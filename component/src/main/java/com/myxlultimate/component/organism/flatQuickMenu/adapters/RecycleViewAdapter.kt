package com.myxlultimate.component.organism.flatQuickMenu.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.flatQuickMenu.FlatQuickMenuItem

class RecycleViewAdapter(
    private val items: MutableList<FlatQuickMenuItem.Data>,
    private val onActiveItemChange: (Int) -> Unit
) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private var activeIndex = -1

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(val view: FlatQuickMenuItem) : RecyclerView.ViewHolder(view) {

        fun bind(data: FlatQuickMenuItem.Data, onIndexChange: (Boolean) -> Unit) {
            view.apply {
                label = data.label
                iconImage = data.iconImage
                deactivatable = data.isActive
                isActive = data.isActive
                onChange = onIndexChange
                textColor = data.labelColor
                labelHexString = data.labelHexString
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FlatQuickMenuItem(
                parent.context
            )
        )
    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prevIndex = activeIndex
        if (items[position].isActive && position != prevIndex) {
            activeIndex = position
            if (prevIndex != -1) onActiveItemChange(activeIndex)
        }
        holder.bind(items[position]) {
            if (it) {
                activeIndex = position
                onActiveItemChange(activeIndex)
            }
        }
    }

}