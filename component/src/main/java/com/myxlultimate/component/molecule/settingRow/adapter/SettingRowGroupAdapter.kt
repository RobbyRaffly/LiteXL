package com.myxlultimate.component.molecule.settingRow.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.molecule.settingRow.SettingRow

class SettingRowGroupAdapter(val onItemPress: (SettingRow.Data, Int) -> Unit) :
    RecyclerView.Adapter<SettingRowGroupAdapter.ViewHolder>() {

    var items = listOf<SettingRow.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SettingRow(
                parent.context
            ),
            onItemPress
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position, items.size)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: SettingRow,
        val onItemPress: (SettingRow.Data, Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: SettingRow.Data, index: Int, itemSize: Int) {
            view.apply {
                title = data.title
                iconCode = data.iconCode
                ribbonLabel = data.ribbonLabel.toString()
                iconDrawable = data.iconDrawable
                redDotCount = data.redDotCount
                hasBottomLine = index + 1 != itemSize
                onPress = { onItemPress(data, index) }
            }
        }
    }

}