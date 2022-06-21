package com.myxlultimate.component.organism.menuPrioFlexCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_menu_prio_flex_card_item.view.*

class MenuPrioFlexCardItem@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs)  {

    data class Data(
        val label: String = "",
        val iconImage: Any? = null,
        val imageSourceType: ImageSourceType = ImageSourceType.ASSET,
        val onPress: (() -> Unit)? = null
    )

    var label = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var iconImage: Any? = null
        set(value) {
            field = value
            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value

            iconView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(contentView, value)

        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {

        LayoutInflater.from(context)
            .inflate(R.layout.organism_menu_prio_flex_card_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuPrioFlexCardItemAttr)

        typedArray.run {
            label = getString(R.styleable.MenuPrioFlexCardItemAttr_label) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.MenuPrioFlexCardItemAttr_imageSourceType, 2)]
            iconImage = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.CustomImageViewAttr_imageSource)
            } else {
                getString(R.styleable.CustomImageViewAttr_imageSource)
            }
        }

        TouchFeedbackUtil.attach(contentView, onPress)
    }

}