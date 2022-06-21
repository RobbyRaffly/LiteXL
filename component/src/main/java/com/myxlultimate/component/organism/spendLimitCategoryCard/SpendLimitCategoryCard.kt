package com.myxlultimate.component.organism.spendLimitCategoryCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_spend_limit_category_card.view.*

class SpendLimitCategoryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var iconImage = resources.getDrawable(R.drawable.ic_balance)
        set(value) {
            field = value
            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            informationView.text = value
        }

    // ----------------------------------------------------------------------------------

    var isDisabled = false
        set(value) {
            field = value
            if (value) {
                titleView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
                informationView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
                circleView.visibility = View.GONE
                iconView.alpha=0.5f
                TouchFeedbackUtil.detach(cardView)
            } else {
                titleView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                informationView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                circleView.visibility = View.VISIBLE
                iconView.alpha=1f
                onCardPress?.let { TouchFeedbackUtil.attach(cardView, it)}
            }
        }
    // ----------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value
            if (!isDisabled) {
                TouchFeedbackUtil.attach(cardView, value)
            } else {
                TouchFeedbackUtil.detach(cardView)
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_spend_limit_category_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpendLimitCategoryCard)
        typedArray.run {
            getDrawable(R.styleable.SpendLimitCategoryCard_iconImage)?.let {
                iconImage = it
            }
            title = getString(R.styleable.SpendLimitCategoryCard_title) ?: ""
            information = getString(R.styleable.SpendLimitCategoryCard_information) ?: ""
            isDisabled = getBoolean(R.styleable.SpendLimitCategoryCard_isDisabled, false)
        }
        typedArray.recycle()

    }
}