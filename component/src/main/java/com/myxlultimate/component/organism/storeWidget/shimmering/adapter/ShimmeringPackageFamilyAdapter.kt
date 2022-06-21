package com.myxlultimate.component.organism.storeWidget.shimmering.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.storeWidget.shimmering.ShimmeringPackageFamilyItem

class ShimmeringPackageFamilyAdapter () : RecyclerView.Adapter<ShimmeringPackageFamilyAdapter.ViewHolder>() {

    var items = listOf<ShimmeringPackageFamilyItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: ShimmeringPackageFamilyItem
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: ShimmeringPackageFamilyItem.Data, index: Int) {
            view.apply {
                isShimmerOn = data.isShimmerOn ?: false
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            ShimmeringPackageFamilyItem(
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

}