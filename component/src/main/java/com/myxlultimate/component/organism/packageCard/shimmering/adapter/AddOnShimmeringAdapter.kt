package com.myxlultimate.component.organism.packageCard.shimmering.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.packageCard.OptionPackageCard

class AddOnShimmeringAdapter () : RecyclerView.Adapter<AddOnShimmeringAdapter.ViewHolder>() {

    var items = listOf<OptionPackageCard.addOnShimer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: OptionPackageCard
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: OptionPackageCard.addOnShimer, index: Int) {
            view.apply {
                isShimmerAddOn = data.isShimmerAddOn ?: false
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            OptionPackageCard(
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