package com.myxlultimate.component.molecule.optionItemCard

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.token.imageView.ImageView
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_option_item_card.view.*
import java.util.*

class OptionItemCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val subtitle: String,
        val imageSourceType: ImageSourceType = ImageSourceType.DRAWABLE,
        val imageSource: Any?,
        var isActive: Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value

            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var subtitle = ""
        set(value) {
            field = value

            subtitleView.text = value
            IsEmptyUtil.setVisibility(value, subtitleView)
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType: ImageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            updateIconView()
        }

    // ----------------------------------------------------------------------------------

    var imageSource: Any? = null
        set(value) {
            field = value
            updateIconView()
        }

    // ----------------------------------------------------------------------------------

    var deactivatable = false

    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            if (deactivatable && field && !value) return

            field = value

            IsEmptyUtil.setVisibility(value, selectedView)
            onChange?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_option_item_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OptionItemCardAttr)
        typedArray.run {
            title = typedArray.getString(R.styleable.OptionItemCardAttr_title) ?: ""
            subtitle = typedArray.getString(R.styleable.OptionItemCardAttr_subtitle)?: ""
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.OptionItemCardAttr_imageSourceType, 2)]
            imageSource = resolveImageSource(this, imageSourceType, R.styleable.OptionItemCardAttr_imageSource)
            isActive = typedArray.getBoolean(R.styleable.OptionItemCardAttr_isActive,false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(cardView) {
                isActive = !isActive
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun updateIconView() {
        imageSource?.also {image ->
            if (image is Drawable) {
                iconView.imageSourceType = ImageSourceType.DRAWABLE
            } else {
                iconView.imageSourceType = imageSourceType
            }
            iconView.imageSource = image
        }
        IsEmptyUtil.setVisibility(imageSource, iconView)
    }

    private fun resolveImageSource(typedArray: TypedArray, imageSourceType: ImageSourceType, image: Int): Any? {
        return if (imageSourceType == ImageSourceType.DRAWABLE) {
            typedArray.getDrawable(image)
        } else {
            typedArray.getString(image)
        }
    }
}