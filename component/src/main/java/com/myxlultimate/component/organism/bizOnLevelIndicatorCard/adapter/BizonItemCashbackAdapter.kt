package com.myxlultimate.component.organism.bizOnLevelIndicatorCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.bizOnLevelIndicatorCard.BizonCashbackListItem

class BizonItemCashbackAdapter : RecyclerView.Adapter<BizonItemCashbackAdapter.ViewHolder>() {

    var items = listOf<BizonCashbackListItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            BizonCashbackListItem(
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
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: BizonCashbackListItem
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: BizonCashbackListItem.Data, index: Int) {
            view.apply {
                vcasbackName = data.casbackName ?: ""
                vcasbackInformation = data.casbackInformation ?: ""
                vcasbackPrice = data.casbackPrice ?: 0

            }
        }
    }

}