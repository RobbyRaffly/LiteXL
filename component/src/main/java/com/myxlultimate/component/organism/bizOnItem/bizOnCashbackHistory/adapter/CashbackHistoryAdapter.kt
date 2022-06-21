package com.myxlultimate.component.organism.bizOnItem.bizOnCashbackHistory.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.bizOnItem.bizOnCashbackHistory.CashbackHistoryItemCard

class CashbackHistoryAdapter(val onItemPress: (Int) -> Unit) : RecyclerView.Adapter<CashbackHistoryAdapter.ViewHolder>() {

    var items = listOf<CashbackHistoryItemCard.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            CashbackHistoryItemCard(parent.context)
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: CashbackHistoryItemCard
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: CashbackHistoryItemCard.Data) {
            view.apply {
                label = data.label
                amount = data.amount
                imageSource = data.imageSource
                imageSourceType = data.imageSourceType
                information = data.information
                showButtonTitle = data.showButtonTitle
                buttonTitle = data.buttonTitle
                onItemClick = data.onItemClick
            }
        }
    }

}