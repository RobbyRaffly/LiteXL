package com.myxlultimate.component.organism.bizOnItem.bizOnActivation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.bizOnItem.bizOnActivation.CashbackActivationItemCard

class CashbackActivationAdapter(val onItemPress: (Int) -> Unit) : RecyclerView.Adapter<CashbackActivationAdapter.ViewHolder>() {

    var items = listOf<CashbackActivationItemCard.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            CashbackActivationItemCard(
                parent.context
            ),
            items.size,
            onItemPress
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: CashbackActivationItemCard,
        val itemCount : Int,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: CashbackActivationItemCard.Data, index: Int) {
            view.apply {
                title = data.title
                iconItem = data.iconItem
                label = data.label
                amount = data.amount
                isShowArrow = data.isShowArrow
                isShowButtonLine = (itemCount-1)!=index
                onItemPress = { onItemPress(index) }
            }
        }
    }

}