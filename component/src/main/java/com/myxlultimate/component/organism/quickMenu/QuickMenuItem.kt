package com.myxlultimate.component.organism.quickMenu

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_quick_menu_item.view.*

class QuickMenuItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var label = ""
        set(value) {
            field = value

            labelView.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Please set cache flags before set the iconImage to take effect
     * */
    var skipMemoryCache = true
    var skipDiskCache = true

    // ----------------------------------------------------------------------------------

    var iconImage: Any? = null
        set(value) {
            field = value

            iconView.skipMemoryCache = skipMemoryCache
            iconView.skipDiskCache = skipDiskCache
            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var iconImageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value

            iconView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var isDisabled = false
        set(value) {
            field = value

            if(isLocked) {
                labelView.alpha = if (!value) 1F else .5F
            } else {
                containerView.alpha = if (!value) 1F else .5F
            }
            this.onPress = onPress
        }

    // ----------------------------------------------------------------------------------

    var isRedDot = false
        set(value) {
            field = value
            refreshView()
        }


    var isAdd = false
        set(value) {
            field = value
            refreshView()
        }

    var isRemove = false
        set(value) {
            field = value
            refreshView()
        }

    var isEmpty = false
        set(value) {
            field = value
            refreshView()
        }

    var bubbleLabel = ""
        set(value) {
            field = value
            refreshView()
        }

    var isLocked = false
        set(value) {
            field = value
            if(isLocked){
                iconView.imageSourceType = ImageSourceType.BASE64
                iconView.imageSource = resources.getString(R.string.xl_lock_base64_icon)
                isDisabled = true
            } else {
                iconView.imageSourceType  = iconImageSourceType
                iconView.imageSource = iconImage
                isDisabled = isDisabled
            }
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            if (!isDisabled) {
                TouchFeedbackUtil.attach(containerView, value)
            } else {
                TouchFeedbackUtil.detach(containerView)
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        if (isRedDot) {
            circleRedDot.background =
                ContextCompat.getDrawable(context, R.drawable.circle_background)
            circleRedDot.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.mud_palette_basic_red)
        }
        if (isAdd) {
            circleRedDot.backgroundTintList = null
            circleRedDot.background =
                ContextCompat.getDrawable(context, R.drawable.ic_quick_menu_add)
        }
        if (isRemove) {
            circleRedDot.backgroundTintList = null
            circleRedDot.background =
                ContextCompat.getDrawable(context, R.drawable.ic_quick_menu_remove)
        }
        circleRedDot.visibility = if (isRemove || isAdd || isRedDot) View.VISIBLE else View.GONE
        iconContainerView.apply {
            if (isEmpty)
                this.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_quick_menu_empty
                )
            else {
                this.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.circle_background
                )
                this.backgroundTintList = ContextCompat.getColorStateList(
                    context,
                    if (isLocked) R.color.mud_palette_basic_medium_grey else R.color.mud_palette_basic_white
                )
            }
            iconView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        }
        bubbleLabelView.visibility = if (bubbleLabel.isNotEmpty()) View.VISIBLE else View.GONE
        textBubbleLabel.text = bubbleLabel
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_quick_menu_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuickMenuItemAttr)
        typedArray.run {
            label = getString(R.styleable.QuickMenuItemAttr_label) ?: ""
            iconImage = getString(R.styleable.QuickMenuItemAttr_iconImage) ?: ""
            isDisabled = getBoolean(R.styleable.QuickMenuItemAttr_isDisabled, false)
            isRedDot = getBoolean(R.styleable.QuickMenuItemAttr_isRedDot, false)
            isRemove = getBoolean(R.styleable.QuickMenuItemAttr_isRemove, false)
            isAdd = getBoolean(R.styleable.QuickMenuItemAttr_isAdd, false)
            isEmpty = getBoolean(R.styleable.QuickMenuItemAttr_isEmpty, false)
            bubbleLabel = getString(R.styleable.QuickMenuItemAttr_bubbleLabel) ?: ""
            isLocked = getBoolean(R.styleable.QuickMenuItemAttr_isLock,false)
        }
    }
}