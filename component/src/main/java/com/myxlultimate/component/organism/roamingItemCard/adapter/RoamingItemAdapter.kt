package com.myxlultimate.component.organism.roamingItemCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.roamingItemCard.RoamingItem

class RoamingItemAdapter : RecyclerView.Adapter<RoamingItemAdapter.ViewHolder>() {

    var items = listOf<RoamingItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ViewHolder(
        val view: RoamingItem,
        val itemCount : Int
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: RoamingItem.Data, index: Int) {
            view.apply {
                priceString = data.priceString?:""
                title = data.title?:""
                detail = data.detail?:""
                imageSource = data.imageSource?:""
                items = data.items?: listOf()
                showLine = index!=itemCount-1
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RoamingItemAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoamingItemAdapter.ViewHolder {
        return ViewHolder(
            RoamingItem(
                parent.context
            ),
            items.size
        )
    }
}