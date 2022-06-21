package com.myxlultimate.component.molecule.accountRemovalCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.profileSelector.ProfileSelector
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_account_removal_card.view.*
import kotlinx.android.synthetic.main.molecule_profile_picture_setting.view.*

class AccountRemovalCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val profileSelector: ProfileSelector by lazy { findViewById<ProfileSelector>(R.id.profileSelectorView) }

    // ----------------------------------------------------------------------------------

    var imageScaleType = ImageView.ScaleType.CENTER_CROP
        set(value) {
            field = value
            profileSelector.imageScaleType = value
        }
    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value

            profileSelector.name = value
        }

    // ----------------------------------------------------------------------------------

    var id = ""
        set(value) {
            field = value

            profileSelector.id = value
        }

    // ----------------------------------------------------------------------------------

    var avatar: Any? = null
        set(value) {
            field = value

            profileSelector.imageSourceType =
                if (value.toString().isNotEmpty()) imageSourceType else ImageSourceType.BITMAP
            profileSelector.avatar =
                if (value.toString().isNotEmpty()) value else R.drawable.ic_avatar
        }

    // ----------------------------------------------------------------------------------

    var avatarBorderStartColor = 0
        set(value) {
            field = value
            profileSelector.avatarBorderStartColor = value
        }

    // ----------------------------------------------------------------------------------

    var avatarBorderEndColor = 0
        set(value) {
            field = value
            profileSelector.avatarBorderEndColor = value
        }

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            profileSelector.imageSourceType = value
        }

    var placeholder: Int? = R.drawable.ic_avatar
        set(value) {
            field = value
            profileSelector.placeholder = value
        }

    // ----------------------------------------------------------------------------------

    var onProfileAccountPress: (() -> Unit)? = null
        set(value) {
            field = value
            profileSelector.onPress = value
        }

    // ----------------------------------------------------------------------------------

    var onRemoveAccountPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(eraseView, value)
        }

    // ----------------------------------------------------------------------------------

    var subscriptionTypeText = ""
        set(value) {
            field = value
            profileSelector.subscriptionTypeText = value
        }

    // ----------------------------------------------------------------------------------

    var subscriptionTypeBgColor = 0
        set(value) {
            field = value
            profileSelector.subscriptionTypeBgColor = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_account_removal_card, this, true)

        profileSelector.imageScaleType = imageScaleType

        profileSelector.isChevronVisible = false

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AccountRemovalCardAttr)
        typedArray.run {
            name = typedArray.getString(R.styleable.AccountRemovalCardAttr_name) ?: ""
            id = typedArray.getString(R.styleable.AccountRemovalCardAttr_id) ?: ""
//            avatar = typedArray.getString(R.styleable.AccountRemovalCardAttr_avatar) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(
                R.styleable.AccountRemovalCardAttr_imageSourceType,
                0
            )]
            if (imageSourceType == ImageSourceType.DRAWABLE) {
                avatar = getDrawable(R.styleable.AccountRemovalCardAttr_avatar)
            } else {
                avatar = typedArray.getString(R.styleable.AccountRemovalCardAttr_avatar)
            }
            avatarBorderStartColor = getInt(R.styleable.AccountRemovalCardAttr_avatarBorderStartColor, 0)
            avatarBorderEndColor = getInt(R.styleable.AccountRemovalCardAttr_avatarBorderEndColor, 0)

            subscriptionTypeText = getString(R.styleable.AccountRemovalCardAttr_subscriptionTypeText) ?: ""
            subscriptionTypeBgColor = getInt(R.styleable.AccountRemovalCardAttr_subscriptionTypeBgColor, 0)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(eraseView, onRemoveAccountPress)
    }
}