package com.myxlultimate.component.organism.upSell.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.upSell.OptionItem

class OptionItemListAdapter(
    private val onActiveItemChange: (Int) -> Unit
) : ListAdapter<OptionItem.Data, OptionItemListAdapter.ViewHolder>(diffUtilCallback) {

    class ViewHolder(
        val view: OptionItem,
        val onActiveIndexChange: (Int) -> Unit,
        val itemSize: Int
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: OptionItem.Data, index: Int) {
            view.apply {
                title = data.title
                description = data.description
                icon = data.imageSource
                deactivatable = data.isActive
                isActive = data.isActive
                isLineVisible = index != itemSize - 1
                onChange = {
                    if (it) {
                        onActiveIndexChange(index)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            OptionItem(
                parent.context
            ), onActiveItemChange,
            itemSize = itemCount
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }

    companion object {
        val diffUtilCallback = object : DiffUtil.ItemCallback<OptionItem.Data>() {
            override fun areItemsTheSame(
                oldItem: OptionItem.Data,
                newItem: OptionItem.Data
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: OptionItem.Data,
                newItem: OptionItem.Data
            ): Boolean {
                return oldItem.title == newItem.title && oldItem.isActive == newItem.isActive
            }

        }
    }
}