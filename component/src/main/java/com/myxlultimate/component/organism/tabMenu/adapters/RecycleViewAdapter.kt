package com.myxlultimate.component.organism.tabMenu.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.tabMenu.TabMenuItem

class RecycleViewAdapter(
    val items: MutableList<TabMenuItem.Data>,
    private val onActiveItemChange: (Int) -> Unit
) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private var activeIndex = -1
    private val TAG = "TAB_GROUP"
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(val view: TabMenuItem) : RecyclerView.ViewHolder(view) {

        fun bind(data: TabMenuItem.Data, onIndexChange: (Boolean) -> Unit) {
            view.apply {
                label = data.label
                deactivatable = data.isActive
                isActive = data.isActive
                onChange = onIndexChange
                textColor = data.textColorActive
                textColorInactive = data.textColorInactive
                activeMenuColor = data.activeMenuColor
                paddingHorizontal = data.paddingHorizontal
                paddingVertical = data.paddingVertical
                inactiveAlpha = data.inactiveAlpha
                inactiveMenuColor = data.inactiveMenuColor
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TabMenuItem(
                parent.context
            )
        )
    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prevIndex = activeIndex
        if (items[position].isActive && position != prevIndex) {
            activeIndex = position
            if (prevIndex != -1) {
                onActiveItemChange(activeIndex)
            }
        }
        holder.bind(items[position]) {
            Log.d(TAG, "rv -> adapterPosition: $position, onChange: $it, position: $activeIndex")
            if (it) {
                activeIndex = position
                onActiveItemChange(activeIndex)
            }
        }
    }

}