package com.myxlultimate.component.organism.roamingItemCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.roamingItemCard.RoamingItemRow

class RoamingItemRowAdapter : RecyclerView.Adapter<RoamingItemRowAdapter.ViewHolder>() {

    var items = listOf<RoamingItemRow.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ViewHolder(
        val view: RoamingItemRow,
        val itemCount : Int
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: RoamingItemRow.Data, index: Int) {
            view.apply {
                priceString = data.priceString?:""
                title = data.title?:""
                imageSource = data.imageSource?:""
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RoamingItemRowAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoamingItemRowAdapter.ViewHolder {
        return ViewHolder(
            RoamingItemRow(
                parent.context
            ),
            items.size
        )
    }
}