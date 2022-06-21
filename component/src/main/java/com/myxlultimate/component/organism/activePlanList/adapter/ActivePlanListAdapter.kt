package com.myxlultimate.component.organism.activePlanList.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.activePlanList.ActivePlanListItem

class ActivePlanListAdapter() : RecyclerView.Adapter<ActivePlanListAdapter.ViewHolder>() {

    var items = listOf<ActivePlanListItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            ActivePlanListItem(
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
        val view: ActivePlanListItem
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: ActivePlanListItem.Data, index: Int) {
            view.apply {
                iconImage = data.iconImage ?: ""
                title = data.title ?: ""
                information = data.information ?: ""
            }
        }
    }

}