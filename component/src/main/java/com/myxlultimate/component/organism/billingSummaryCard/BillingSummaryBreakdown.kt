package com.myxlultimate.component.organism.billingSummaryCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.transactionSummary.TransactionSummaryRow
import com.myxlultimate.component.organism.transactionSummary.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_billing_summary_breakdown.view.*

class BillingSummaryBreakdown @JvmOverloads constructor(
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
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_summary_breakdown, this, true)


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
            items.add(TransactionSummaryRow.Data(child.label, child.amount, child.point, child.rowValueType))
            recycleAdapter.items = items
        }
    }

}