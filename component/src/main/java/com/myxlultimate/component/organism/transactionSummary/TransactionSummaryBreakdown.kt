package com.myxlultimate.component.organism.transactionSummary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.transactionSummary.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_transaction_summary_breakdown.view.*

class TransactionSummaryBreakdown @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<TransactionSummaryRow.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }
    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy { RecycleViewAdapter() }

    // ----------------------------------------------------------------------------------
    var totalPoint = 0
        set(value) {
            field = value

            if (totalPoint > 0) {
                totalView.point = value
            }
        }

    // ----------------------------------------------------------------------------------

    var total = 0
        set(value) {
            field = value

            if (total >= 0) {
                totalView.amount = value
            }
        }
    // ----------------------------------------------------------------------------------

    var detail = ""
        set(value) {
            field = value

            referenceView.value = value
        }

    var detailLabel = ""
        set(value) {
            field = value

            if(detailLabel.isEmpty())
                referenceView.rowValueType = RowValueType.ID
            else {
                referenceView.rowValueType = RowValueType.DETAIL
                referenceView.label = value
            }

        }

    var isHideDetail = false
        set(value) {
            field = value

            referenceView.visibility = if (value) View.GONE else View.VISIBLE
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_summary_breakdown, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TransactionSummaryBreakdownAttr)
        typedArray.run {
            detail = typedArray.getString(R.styleable.TransactionSummaryBreakdownAttr_detail) ?: ""
            detailLabel =  typedArray.getString(R.styleable.TransactionSummaryBreakdownAttr_label) ?: ""
            isHideDetail = typedArray.getBoolean(R.styleable.TransactionSummaryBreakdownAttr_isHideDetail, false)
        }
        typedArray.recycle()

        itemContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is TransactionSummaryRow) {
            super.addView(child, index, params)
        } else {
            total += child.amount
            totalPoint += child.point
            items.add(TransactionSummaryRow.Data(child.label, child.amount, child.point, child.rowValueType))
            recycleAdapter.items = items
        }
    }

}