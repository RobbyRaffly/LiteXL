package com.myxlultimate.component.organism.faq.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.faq.FaqItem

class RecycleViewAdapter(val onItemPress: (Int) -> Unit) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    var items = listOf<FaqItem.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: FaqItem,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: FaqItem.Data, index: Int) {
            view.apply {
                hasBottomLine = data.hasBottomLine
                question = data.question
                onPress = { onItemPress(index) }
                isShimmerOn = data.isShimmerOn ?: false
                hasRightArrow = data.hasRightArrow ?: true
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FaqItem(
                parent.context
            ),
            onItemPress
        )
    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

}