package com.myxlultimate.component.molecule.optionItemCard

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_language_option_item_card.view.*
import kotlinx.android.synthetic.main.molecule_language_option_item_card.view.iconView

class LanguageOptionItemCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val imageSource: Drawable?,
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

    var imageSource: Drawable? = null
        set(value) {
            field = value

            iconView.imageSource = value
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
            .inflate(R.layout.molecule_language_option_item_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LanguageOptionItemCardAttr)
        typedArray.run {
            title = typedArray.getString(R.styleable.LanguageOptionItemCardAttr_title) ?: ""
            imageSource = typedArray.getDrawable(R.styleable.LanguageOptionItemCardAttr_imageSource)
            isActive = typedArray.getBoolean(R.styleable.LanguageOptionItemCardAttr_isActive,false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(cardView) { isActive = !isActive }
    }
}