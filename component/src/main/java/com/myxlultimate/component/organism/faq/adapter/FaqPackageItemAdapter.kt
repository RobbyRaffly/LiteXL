package com.myxlultimate.component.organism.faq.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.faq.FaqPackageItem

class FaqPackageItemAdapter(val onItemPress: (Int) -> Unit) : RecyclerView.Adapter<FaqPackageItemAdapter.ViewHolder>() {

    var items = listOf<FaqPackageItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FaqPackageItem(
                parent.context
            ),
            onItemPress
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: FaqPackageItem,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: FaqPackageItem.Data, index: Int) {
            view.apply {
                iconDrawable = data.iconDrawable
                title = data.title
                description = data.description
                onPress = {onItemPress(index)}
            }
        }
    }

}