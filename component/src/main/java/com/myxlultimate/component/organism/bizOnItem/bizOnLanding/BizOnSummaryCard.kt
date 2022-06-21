package com.myxlultimate.component.organism.bizOnItem.bizOnLanding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.bizOnItem.bizOnLanding.enum.CardType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_biz_on_summary_card.view.*

class BizOnSummaryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var icon = ContextCompat.getDrawable(context, R.drawable.ic_biz_on_icon_cashback)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var value = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var cardType = CardType.HEADER
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_on_summary_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BizOnSummaryCard)
        typedArray.run {
            icon = getDrawable(R.styleable.BizOnSummaryCard_imageDrawable)
            information = getString(R.styleable.BizOnSummaryCard_information) ?: ""
            value = getString(R.styleable.BizOnSummaryCard_value) ?: ""
            cardType = CardType.values()[getInt(R.styleable.BizOnSummaryCard_cardType, 0)]
        }
        typedArray.recycle()

    }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        ivHeaderIcon.setImageDrawable(icon)
        tvInformation.text = information
        tvValue.text = value
        if (cardType == CardType.HEADER)
            tvValue.textSize = 18f
        else
            tvValue.textSize = 12f
        TouchFeedbackUtil.attach(clBaseAction, onPress)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}
