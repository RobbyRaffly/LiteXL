package com.myxlultimate.component.molecule.accountPicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.profileSelector.ProfileSelector
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_account_picker_item.view.*

class AccountPickerItem @JvmOverloads constructor(
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
            profileSelector.avatar = value
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

    // ----------------------------------------------------------------------------------

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
    var isActive = false
        set(value) {
            field = value

            borderContainerView.setBackgroundResource(if (isActive) R.drawable.top_up_nominal_option_background else 0)
            checkView.visibility = if (isActive) View.VISIBLE else View.GONE

            onChange?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null

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
            .inflate(R.layout.molecule_account_picker_item, this, true)

        profileSelector.imageScaleType = imageScaleType

        profileSelector.isChevronVisible = false

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AccountPickerItemAttr)
        typedArray.run {
            name = typedArray.getString(R.styleable.AccountPickerItemAttr_name) ?: ""
            id = typedArray.getString(R.styleable.AccountPickerItemAttr_id) ?: ""
            isActive = getBoolean(R.styleable.AccountPickerItemAttr_isActive, false)
            imageSourceType = ImageSourceType.values()[getInt(
                R.styleable.AccountPickerItemAttr_imageSourceType,
                2
            )]
            avatar = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.AccountPickerItemAttr_avatar)
            } else {
                getString(R.styleable.AccountPickerItemAttr_avatar)
            }
            avatarBorderStartColor = getInt(R.styleable.AccountPickerItemAttr_avatarBorderStartColor, 0)
            avatarBorderEndColor = getInt(R.styleable.AccountPickerItemAttr_avatarBorderEndColor, 0)

            subscriptionTypeText = getString(R.styleable.AccountPickerItemAttr_subscriptionTypeText) ?: ""
            subscriptionTypeBgColor = getInt(R.styleable.AccountPickerItemAttr_subscriptionTypeBgColor, 0)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(this) {
            if (!isActive) {
                isActive = true
            }
        }
    }
}