package com.myxlultimate.component.organism.tableView.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.tableView.ColumnRow

class TableViewRecyclerView : RecyclerView.Adapter<TableViewRecyclerView.ViewHolder>() {

    var items = listOf<ColumnRow.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            ColumnRow(
                parent.context
            ),
            items.size
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
        val view: ColumnRow,
        val itemCount: Int
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: ColumnRow.Data, index: Int) {
            view.apply {
                firstColumn = data.first
                secondColumn = data.second
                thirdColumn = data.third
                isHeader = index == 0
                backgroundColumnColor =
                    if (index == 0) R.color.mud_palette_primary_blue else R.color.mud_palette_basic_white
            }
        }
    }
}