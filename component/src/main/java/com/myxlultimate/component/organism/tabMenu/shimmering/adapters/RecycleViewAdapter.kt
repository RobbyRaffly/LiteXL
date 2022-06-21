package com.myxlultimate.component.organism.tabMenu.shimmering.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.tabMenu.shimmering.TabMenuItemShimmering

class RecycleViewAdapter() : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = listOf<TabMenuItemShimmering.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val holder = ViewHolder(
            TabMenuItemShimmering(
                parent.context
            )
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            parent.layoutParams.height
        )
        return holder
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: TabMenuItemShimmering
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: TabMenuItemShimmering.Data, index: Int) {
            view.apply {
                isShimmerOn = data.isShimmerOn ?: false
            }
        }

    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}