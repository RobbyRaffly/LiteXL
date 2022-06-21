package com.myxlultimate.component.organism.loyaltyTableCard.adapter

import android.os.Build
import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.loyaltyTableCard.LoyaltyTableCard
import com.myxlultimate.component.organism.loyaltyTableCard.LoyaltyTableItem

class LoyaltyTableCardGroupAdapter(val onCardPress : ((Int,LoyaltyTableCard.Data)-> Unit)?): RecyclerView.Adapter<LoyaltyTableCardGroupAdapter.ViewHolder>() {

    class ViewHolder(
        val view: LoyaltyTableCard,
        val onCardPress : ((Int,LoyaltyTableCard.Data)-> Unit)?
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: LoyaltyTableCard.Data, index: Int){
            view.apply {
                this.items = data.items
                this.hasRightArrowIcon = data.hasRightArrowIcon
                this.name = data.name
                this.onPress = {
                    onCardPress?.let { it(index,data) }
                }
            }
        }
    }

    var items = listOf<LoyaltyTableCard.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder =  ViewHolder(
            LoyaltyTableCard(parent.context),
            onCardPress = onCardPress
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        return holder
    }

}