package com.myxlultimate.component.organism.bizOnTable.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.bizOnTable.BizonTableListItem

class BizonItemTableAdapter : RecyclerView.Adapter<BizonItemTableAdapter.ViewHolder>() {

    var items = listOf<BizonTableListItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            BizonTableListItem(
                parent.context
            )
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this, items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: BizonTableListItem
    ) : RecyclerView.ViewHolder(view) {

        fun bind(adapter: BizonItemTableAdapter, data: BizonTableListItem.Data, index: Int) {
            view.apply {
                columnOne = data.columnOne ?: ""
                columnTwo = data.columnTwo ?: 0
                columnThree = data.columnThree ?: 0
                if(index == adapter.itemCount -1){
                    hideBottomDivider()
                }
            }
        }
    }

}