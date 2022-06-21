package com.myxlultimate.component.organism.transactionSuccessSummaryCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_status_payment_card.view.*
import kotlinx.android.synthetic.main.organism_transaction_success_summary_card.view.*

class TransactionSuccessSummaryCard @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isPoint = false
    set(value) {
        field = value
        if(isPoint) {
            totalView.text =
                ("$point " + resources.getString(R.string.organism_redeemable_card_point))
        }
        else {
            totalView.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(total.toLong(), true)
            )
        }
    }


    var buttonTitle = resources.getString(R.string.organism_transaction_success_summary_card_transaction_id)
        set(value) {
            field = value
            detailText.text = value
        }
    // ----------------------------------------------------------------------------------

    var point = 0
        set(value) {
            field = value
            if (point > 0 || isPoint) {
                isPoint = true
                totalView.text =
                    ("$point " + resources.getString(R.string.organism_redeemable_card_point))
            }
        }

    // ----------------------------------------------------------------------------------


    var totalString = ""
        set(value) {
            field = value
            if(value.isNotEmpty()) {
                totalView.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var total = 0
        set(value) {
            field = value
            if(!isPoint)
            { totalView.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value.toLong(), true)
            )}
        }

    // ----------------------------------------------------------------------------------

    var transactionCode = ""
        set(value) {
            field = value
            transactionCodeView.text = value
        }

    // ----------------------------------------------------------------------------------

    var onViewDetailButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(detailText, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_success_summary_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.transactionSuccessSummaryCardAttr)
        typedArray.run {
            point = getInt(R.styleable.transactionSuccessSummaryCardAttr_point, 0)
            total = getInt(R.styleable.transactionSuccessSummaryCardAttr_total, 0)
            totalString = getString(R.styleable.transactionSuccessSummaryCardAttr_totalString) ?: ""
            transactionCode =
                getString(R.styleable.transactionSuccessSummaryCardAttr_transactionCode) ?: ""
            buttonTitle = getString(R.styleable.transactionSuccessSummaryCardAttr_buttonTitle)?: resources.getString(R.string.organism_transaction_success_summary_card_transaction_id)
            isPoint = getBoolean(R.styleable.transactionSuccessSummaryCardAttr_isPoint,false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(detailText, onViewDetailButtonPress)
    }
}