package com.myxlultimate.component.organism.loyaltyCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.loyaltyCard.LoyaltyHistoryCard

class LoyaltyHistoryCardGroupAdapter(val onItemPress: (Int) -> Unit) :
    RecyclerView.Adapter<LoyaltyHistoryCardGroupAdapter.ViewHolder>() {

    var items = listOf<LoyaltyHistoryCard.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LoyaltyHistoryCard(
                parent.context
            )
            ,
            onItemPress
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: LoyaltyHistoryCard,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: LoyaltyHistoryCard.Data, index: Int) {
            view.apply {
                dateTime = data.dateTime ?: 0L
                title = data.title ?: ""
                poin = data.poin ?: 0
                expiration = data.expiration ?: 0
                isPendingPoint = true
                onPress = { onItemPress(index) }
            }
        }
    }

}