package com.myxlultimate.component.molecule.profilePictureSetting

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_profile_picture_setting.view.*
import kotlinx.android.synthetic.main.molecule_profile_picture_setting.view.avatarView
import kotlinx.android.synthetic.main.organism_profile_selector.view.*

class ProfilePictureSetting @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    var imageScaleType = ImageView.ScaleType.CENTER_CROP
    set(value) {
        field = value
        avatarView.scaleType = value
    }
    // ----------------------------------------------------------------------------------

    var imageSource: Any? = null
        set(value) {
            field = value
            avatarView.imageSourceType =
                if (value != null) {
                    if (value.toString().isNotEmpty())
                        imageSourceType
                    else
                        ImageSourceType.DRAWABLE
                } else
                    ImageSourceType.DRAWABLE

            avatarView.imageSource =
                if (value != null) {
                    if (value.toString().isNotEmpty())
                        value
                    else
                        ContextCompat.getDrawable(context, R.drawable.ic_avatar)
                } else
                    ContextCompat.getDrawable(context, R.drawable.ic_avatar)
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType: ImageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            avatarView.imageSourceType = value

        }

    var placeholder: Int? = R.drawable.ic_avatar
        set(value) {
            field = value
            avatarView.placeholder = value
        }

    // ----------------------------------------------------------------------------------

    var onEditPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(editView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_profile_picture_setting, this, true)

        gravity = Gravity.CENTER

        avatarView.scaleType = imageScaleType

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ProfilePictureSettingAttr)
        typedArray.run {
            imageSourceType =
                ImageSourceType.values()[getInt(R.styleable.ProfilePictureSettingAttr_imageSourceType, 2)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.ProfilePictureSettingAttr_imageSource)
            } else {
                getString(R.styleable.ProfilePictureSettingAttr_imageSource)
            }
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(editView, onEditPress)
    }
}