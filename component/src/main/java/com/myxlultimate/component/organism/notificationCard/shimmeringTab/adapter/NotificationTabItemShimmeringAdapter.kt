package com.myxlultimate.component.organism.notificationCard.shimmeringTab.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.notificationCard.shimmeringTab.NotificationTabItemShimmering

class NotificationTabItemShimmeringAdapter : RecyclerView.Adapter<NotificationTabItemShimmeringAdapter.ViewHolder>() {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = listOf<NotificationTabItemShimmering.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val holder = ViewHolder(
            NotificationTabItemShimmering(
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
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: NotificationTabItemShimmering
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: NotificationTabItemShimmering.Data) {
            view.apply {
                isShimmerOn = data.isShimmerOn ?: false
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}