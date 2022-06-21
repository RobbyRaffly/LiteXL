package com.myxlultimate.component.organism.billingSummaryCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.transactionSummary.RowValueType
import com.myxlultimate.component.organism.transactionSummary.TransactionSummaryRow

class RecycleViewAdapter: RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

    var total = 0
    var items = listOf<TransactionSummaryRow.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: TransactionSummaryRow
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: TransactionSummaryRow.Data, index: Int) {
            view.apply {
                rowValueType = RowValueType.DETAIL
                label = data.name
                amount = data.price
                point = data.point
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TransactionSummaryRow(
                parent.context
            )
        )
    }


}