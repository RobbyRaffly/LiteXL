package com.myxlultimate.component.organism.faq.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.faq.FaqFloatingCardItem

class FaqFloatingCardAdapter(val onItemPress: (Int) -> Unit) : RecyclerView.Adapter<FaqFloatingCardAdapter.ViewHolder>() {

    var items = listOf<FaqFloatingCardItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FaqFloatingCardItem(
                parent.context
            ),
            onItemPress
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: FaqFloatingCardItem,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: FaqFloatingCardItem.Data, index: Int) {
            view.apply {
                title = data.title
                onPress = {onItemPress(index)}
            }
        }
    }

}