package com.myxlultimate.component.organism.topUpPrioFlexCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_topup_prio_flex_card.view.*

class TopUpPrioFlexCard@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value
            priceView.text =
                if (value < 1) ""
                else context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
        }

    var priceWithZero: Long = 0
        set(value) {
            field = value
            priceView.text =
                if (value < 1) context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(0, true)
                )
                else context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
        }

    // ----------------------------------------------------------------------------------

    var buttonText = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty())
                buttonTextView.visibility = View.GONE

            buttonTextView.text = value

        }

    // ----------------------------------------------------------------------------------

    var titleButton = ""
        set(value) {
            field = value
            titleButtonView.text = value
        }

    // ----------------------------------------------------------------------------------

    var onPressButtonTitle: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(buttonTextView, value)

        }

    // ----------------------------------------------------------------------------------

    var onPressButton: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(buttonTopUpView, value)

        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_topup_prio_flex_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopUpPrioFlexCardAttr)

        typedArray.run {
            title = getString(R.styleable.TopUpPrioFlexCardAttr_title) ?: ""
            price = getInt(R.styleable.TopUpPrioFlexCardAttr_price, 0).toLong()
            buttonText = getString(R.styleable.TopUpPrioFlexCardAttr_buttonText) ?: ""
            titleButton = getString(R.styleable.TopUpPrioFlexCardAttr_titleButton) ?: ""

            TouchFeedbackUtil.attach(buttonTextView, onPressButtonTitle)
            TouchFeedbackUtil.attach(buttonTopUpView, onPressButton)
        }
    }
}