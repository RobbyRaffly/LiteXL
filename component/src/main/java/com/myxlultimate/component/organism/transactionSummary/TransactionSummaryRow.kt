package com.myxlultimate.component.organism.transactionSummary

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_transaction_summary_row.view.*
import kotlin.math.absoluteValue

class TransactionSummaryRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val name: String,
        val price: Int,
        val point :Int,
        val rowValueType: RowValueType? = RowValueType.TOTAL
    )

    var rowValueType = RowValueType.TOTAL
        set(value) {
            field = value
            if(rowValueType == RowValueType.TOTAL || rowValueType == RowValueType.TOTAL_TRX) {
                val typedValue = TypedValue()
                context.theme.resolveAttribute(R.attr.colorBackgroundPrimary, typedValue, true)
                val color = typedValue.resourceId
                rowValueType.valueColor = color
            } else {
                rowValueType.valueColor = value.valueColor
            }
            refreshView()

            this.label = label
        }

    // ----------------------------------------------------------------------------------

    var valueColor = rowValueType.valueColor
    set(value) {
        field = value

        valueView.setTextColor(ContextCompat.getColor(context, value))
    }

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var point = 0
        set(value) {
            field = value
            refreshView()
        }

    var amount = 0
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
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        labelView.let {
            TextViewCompat.setTextAppearance(it, rowValueType.style)
            it.setTextColor(ContextCompat.getColor(context, rowValueType.labelColor))
        }

        valueView.let {
            TextViewCompat.setTextAppearance(it, rowValueType.style)
            it.setTextColor(ContextCompat.getColor(context, rowValueType.valueColor))
        }

        labelView.text = when (rowValueType) {
            RowValueType.TOTAL -> resources.getString(R.string.organism_transaction_summary_row_label_total)
            RowValueType.ID -> if (label.isNotEmpty()) label else resources.getString(R.string.organism_transaction_summary_row_label_references)
            RowValueType.DETAIL -> label
            RowValueType.TOTAL_TRX -> label
        }

        if (rowValueType == RowValueType.ID && value.isNotEmpty()) {
            valueView.text = value
        }

        if (rowValueType == RowValueType.DETAIL || rowValueType == RowValueType.TOTAL || rowValueType == RowValueType.TOTAL_TRX) {
            if (amount >= 0) {
                valueView.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(amount.toLong(), true)
                )
            } else {
                valueView.text =
                    ("- "+context.getString(R.string.indonesian_rupiah_balance_remaining,
                        ConverterUtil.convertDelimitedNumber(amount.absoluteValue.toLong(), true)
                    ))
            }
        }

        if (rowValueType == RowValueType.DETAIL || rowValueType == RowValueType.TOTAL) {
            if (point > 0) {
                valueView.text =
                    ("$point " + resources.getString(R.string.organism_redeemable_card_point))
            }
        }
    }


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_summary_row, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TransactionSummaryRowAttr)
        typedArray.run {
            rowValueType =
                RowValueType.values()[getInt(R.styleable.TransactionSummaryRowAttr_rowValueType, 0)]
            label = getString(R.styleable.TransactionSummaryRowAttr_label) ?: ""
            value = getString(R.styleable.TransactionSummaryRowAttr_value) ?: ""
            amount = getInt(R.styleable.TransactionSummaryRowAttr_amount, 0)
            point = getInt(R.styleable.TransactionSummaryRowAttr_point,0)
            valueColor = getResourceId(R.styleable.TransactionSummaryRowAttr_textColor,rowValueType.valueColor)
        }
        typedArray.recycle()
    }
}