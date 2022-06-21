package com.myxlultimate.component.organism.loyaltyCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.loyaltyCard.LoyaltyHistoryCard

class LoyaltyHistoryGroupAdapter (val onItemPress: (Int) -> Unit): RecyclerView.Adapter<LoyaltyHistoryGroupAdapter.ViewHolder>() {

    var items = listOf<LoyaltyHistoryCard.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: LoyaltyHistoryCard,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: LoyaltyHistoryCard.Data, index: Int) {
            view.apply {
                dateTime = data.dateTime!!
                title = data.title.toString()
                poin = data.poin!!
                expiration = data.expiration!!
                hasPointIcon = data.hasPointIcon!!
                isPendingPoint = data.isPendingPoint!!
                onPress = {onItemPress(index)}
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
            LoyaltyHistoryCard(
                parent.context
            ),
            onItemPress
        )
    }
}