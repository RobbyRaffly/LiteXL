package com.myxlultimate.component.organism.featureItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import kotlinx.android.synthetic.main.organism_feature_item.view.*

class FeatureItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
            imageView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource : Any? = ""
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

    var textColor = R.color.mud_palette_basic_black
        set(value) {
            field = value
            titleView.setTextColor(getColor(context, value))
            informationView.setTextColor(getColor(context, value))
        }
    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            informationView.text = value
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


// ----------------------------------------------------------------------------------
    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_feature_item, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FeatureItem)
        typedArray.run {
            title = getString(R.styleable.FeatureItem_title) ?: ""
            textColor = getResourceId(R.styleable.FeatureItem_textColor, R.color.mud_palette_basic_black)
            information= getString(R.styleable.FeatureItem_information)?:""
            imageSourceType = ImageSourceType.values()[typedArray.getInt(R.styleable.FeatureItem_imageSourceType, 3)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.FeatureItem_imageSource)
            } else {
                typedArray.getString(R.styleable.FeatureItem_imageSource)
            }

        }
        typedArray.recycle()

    }
}