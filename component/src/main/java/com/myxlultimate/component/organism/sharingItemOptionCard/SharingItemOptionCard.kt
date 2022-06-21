package com.myxlultimate.component.organism.sharingItemOptionCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_sharing_item_option_card.view.*
import kotlinx.android.synthetic.main.outline_textfield_layout.view.*

class SharingItemOptionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value

            imageView.imageSourceType = value
        }
    // ----------------------------------------------------------------------------------

    var imageSource: Any? = null
        set(value) {
            field = value

            imageView.imageSource = value
        }
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }
    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(cardView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_sharing_item_option_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SharingItemOptionCard)
        typedArray.run {
            imageSourceType = ImageSourceType.values()[getInt(
                R.styleable.SharingItemOptionCard_imageSourceType,
                2
            )]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.SharingItemOptionCard_imageSource)
            } else {
                getString(R.styleable.SharingItemOptionCard_imageSource)
            }
            title = getString(R.styleable.SharingItemOptionCard_title)?:""
        }
        typedArray.recycle()
    }
}