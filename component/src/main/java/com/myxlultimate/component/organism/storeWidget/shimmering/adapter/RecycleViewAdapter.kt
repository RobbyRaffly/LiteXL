package com.myxlultimate.component.organism.storeWidget.shimmering.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.storeCard.enums.SizeMode
import com.myxlultimate.component.organism.storeWidget.shimmering.ShimmeringStoreCardWidget

class RecycleViewAdapter() : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    var items = listOf<ShimmeringStoreCardWidget.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: ShimmeringStoreCardWidget
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: ShimmeringStoreCardWidget.Data, index: Int) {
            view.apply {
                sizeMode = data.sizeMode ?: SizeMode.LARGE
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
            ShimmeringStoreCardWidget(
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