package com.myxlultimate.component.organism.bizOnItem.bizOnCashbackHistory

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_cashback_total_header_card.view.*

class CashbackTotalHeaderCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            titleText.text = value
        }

    // ----------------------------------------------------------------------------------

    var amount = ""
        set(value) {
            field = value
            amountText.text = value
        }

    // ----------------------------------------------------------------------------------

    var datePeriod = ""
        set(value) {
            field = value
            datePeriodText.text = value
        }

    // ----------------------------------------------------------------------------------

    var onTncClick: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(tncButtonView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_cashback_total_header_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CashbackTotalHeaderCardAttr)
        typedArray.run {
            title = getString(R.styleable.CashbackTotalHeaderCardAttr_title) ?: ""
            amount = getString(R.styleable.CashbackTotalHeaderCardAttr_amount) ?: ""
            datePeriod = getString(R.styleable.CashbackTotalHeaderCardAttr_datePeriod) ?: ""
        }

    }
}