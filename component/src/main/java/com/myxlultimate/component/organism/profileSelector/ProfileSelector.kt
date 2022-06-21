package com.myxlultimate.component.organism.profileSelector

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_profile_selector.view.*

class ProfileSelector @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    val colorGrey = getColor(context, R.color.mud_palette_basic_dark_grey)

    var isDisabled = false
        set(value) {
            field = value
            setTextColor()
        }

    var imageScaleType = ImageView.ScaleType.CENTER_CROP
        set(value) {
            field = value
            avatarView.scaleType = value
        }
    // ----------------------------------------------------------------------------------

    var profileMode = ProfileMode.AVATAR
        set(value) {
            field = value
            setProfileImage()
        }

    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                parentSkeletonView.startShimmer()
            } else {
                parentSkeletonView.stopShimmer()
            }
            parentSkeletonView.visibility = if (value) View.VISIBLE else View.GONE
            containerView.visibility = if (value) View.GONE else View.VISIBLE
        }
    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value

            nameView.text = value
            IsEmptyUtil.setVisibility(value, nameView)
            setProfileImage()
        }

    // ----------------------------------------------------------------------------------

    var id = ""
        set(value) {
            field = value

            idView.text = value
            IsEmptyUtil.setVisibility(value, idView)
        }

    // ----------------------------------------------------------------------------------
    var customValidity = ""
        set(value) {
            field = value

            customValidityView.text = value
            IsEmptyUtil.setVisibility(value, customValidityView)
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            avatarView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var avatar: Any? = null
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
                        getDrawable(context, R.drawable.ic_avatar)
                } else
                    getDrawable(context, R.drawable.ic_avatar)

            setProfileImage()
        }

    // ----------------------------------------------------------------------------------

    var avatarBorderStartColor = 0
        set(value) {
            field = value
            setAvatarBgGradient()
        }

    // ----------------------------------------------------------------------------------

    var avatarBorderEndColor = 0
        set(value) {
            field = value
            setAvatarBgGradient()
        }

    // ----------------------------------------------------------------------------------

    var placeholder: Int? = R.drawable.ic_avatar
        set(value) {
            field = value
            avatarView.placeholder = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

    var backgroundColorProfile =
        ContextCompat.getColorStateList(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value
            setProfileImage()
        }

    // ----------------------------------------------------------------------------------

    var textColor = R.color.mud_palette_basic_white
        set(value) {
            field = value
            setTextColor()
        }

    // ----------------------------------------------------------------------------------

    var hasOnPress = true
        set(value) {
            field = value
            if (hasOnPress) {
                onPress = {}
            }
        }

    // ----------------------------------------------------------------------------------

    var setNameEllipsizeMode: TextUtils.TruncateAt? = null
        set(value) {
            field = value
            value?.let {
                nameView.ellipsize = it
            }
        }

    // ----------------------------------------------------------------------------------

    var setIdEllipsizeMode: TextUtils.TruncateAt? = null
        set(value) {
            field = value
            value?.let {
                idView.ellipsize = it
            }
        }

    // ----------------------------------------------------------------------------------

    var isChevronVisible = true
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, tvChevron)
        }

    // ----------------------------------------------------------------------------------

    var subscriptionTypeText = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, subscriptionType)
            subscriptionType.text = value
        }

    // ----------------------------------------------------------------------------------

    var subscriptionTypeBgColor = 0
        set(value) {
            field = value
            (subscriptionType.background as GradientDrawable).setColor(value)
        }

    // ----------------------------------------------------------------------------------

    var hasBetaBadge = false
        set(value) {
            field = value
            setHybridBetaBadge()
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_profile_selector, this, true)

        avatarView.scaleType = imageScaleType
        isShimmerOn = false

        val typedValue = TypedValue()
        context.theme?.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        val color = ContextCompat.getColorStateList(context, typedValue.resourceId)
        backgroundColorProfile = color

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ProfileSelectorAttr)
        typedArray.run {
            name = getString(R.styleable.ProfileSelectorAttr_name) ?: ""
            id = getString(R.styleable.ProfileSelectorAttr_id) ?: ""
            textColor = getResourceId(
                R.styleable.ProfileSelectorAttr_textColor,
                R.color.mud_palette_basic_white
            )
            hasOnPress = getBoolean(R.styleable.ProfileSelectorAttr_hasOnPress, true)
            imageSourceType =
                ImageSourceType.values()[getInt(
                    R.styleable.ProfileSelectorAttr_imageSourceType,
                    2
                )]
            avatar = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.ProfileSelectorAttr_avatar)
            } else {
                getString(R.styleable.ProfileSelectorAttr_avatar)
            }
            avatarBorderStartColor = getInt(R.styleable.ProfileSelectorAttr_avatarBorderStartColor, 0)
            avatarBorderEndColor = getInt(R.styleable.ProfileSelectorAttr_avatarBorderEndColor, 0)

            customValidity = getString(R.styleable.ProfileSelectorAttr_customValidity) ?: ""
            profileMode =
                ProfileMode.values()[getInt(R.styleable.ProfileSelectorAttr_profileMode, 0)]

            subscriptionTypeText = getString(R.styleable.ProfileSelectorAttr_subscriptionTypeText) ?: ""
            subscriptionTypeBgColor = getInt(R.styleable.ProfileSelectorAttr_subscriptionTypeBgColor, 0)
        }
        typedArray.recycle()
    }

    fun setTextColor() {
        if (isDisabled) {
            idView.setTextColor(colorGrey)
            nameView.setTextColor(colorGrey)
        } else {
            nameView.setTextColor(ContextCompat.getColor(context, textColor))
            idView.setTextColor(ContextCompat.getColor(context, textColor))
        }
    }

    private fun setProfileImage() {
        initialView.text = ConverterUtil.getInitialName(name)
        if (profileMode == ProfileMode.INITIAL) {
            avatarView.visibility = View.GONE
            initialView.visibility = View.VISIBLE
            avatarContainerView.backgroundTintList = backgroundColorProfile
        } else {
            avatarContainerView.backgroundTintList =
                (ContextCompat.getColorStateList(context, R.color.transparent))
            avatarView.visibility = View.VISIBLE
            initialView.visibility = View.GONE
        }
    }

    private fun setAvatarBgGradient() {
        if (avatarBorderStartColor != 0 && avatarBorderEndColor != 0) {
            val gradient = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(avatarBorderStartColor, avatarBorderEndColor)
            )
            avatarBorder.background = gradient
            avatarBorder.setPadding(resources.getDimensionPixelSize(R.dimen.mud_dimens_2dp))
            avatarContainerView.strokeColor = getColor(context, R.color.basicWhite)
            avatarContainerView.strokeWidth = resources.getDimensionPixelSize(R.dimen.mud_dimens_1dp)
        }
    }

    private fun setHybridBetaBadge() {
        val defaultNameViewParam = nameView.layoutParams as ConstraintLayout.LayoutParams
        if (hasBetaBadge) {
            upfrontLabel.visibility = View.VISIBLE
            betaLabel.visibility = View.VISIBLE
            idView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = nameView.id
                marginStart = 0
            }
            customValidityView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = nameView.id
                marginStart = 0
            }


            val params = nameView.layoutParams as ConstraintLayout.LayoutParams
            params.marginStart = 16
            nameView.layoutParams = params
        } else {
            upfrontLabel.visibility = View.GONE
            betaLabel.visibility = View.GONE
            nameView.layoutParams = defaultNameViewParam
            idView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToEnd = avatarBorderContainer.id
                marginStart = 0
            }
            customValidityView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToEnd = avatarBorderContainer.id
                marginStart = 0
            }
        }
    }
}