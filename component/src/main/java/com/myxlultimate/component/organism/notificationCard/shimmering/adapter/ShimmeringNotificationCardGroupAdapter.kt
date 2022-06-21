package com.myxlultimate.component.organism.notificationCard.shimmering.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.notificationCard.shimmering.ShimmeringNotificationCardItem
import com.myxlultimate.component.organism.notificationCard.shimmering.ShimmeringNotificationDateItem
import com.myxlultimate.component.organism.notificationCard.shimmering.model.ShimmeringModelData

class ShimmeringNotificationCardGroupAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    companion object {
        private const val VIEW_DATE = 0
        private const val VIEW_ITEM = 1
    }

    // ----------------------------------------------------------------------------------

    var items = listOf<ShimmeringModelData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_DATE -> {
                val holder = NotificationDateViewHolder(
                    ShimmeringNotificationDateItem(
                        parent.context
                    )
                )
                holder.view.layoutParams = ViewGroup.LayoutParams(
                    parent.layoutParams.width,
                    parent.layoutParams.height
                )
                return holder
            }
            else -> {
                val holder = NotificationCardViewHolder(
                    ShimmeringNotificationCardItem(
                        parent.context
                    )
                )
                holder.view.layoutParams = ViewGroup.LayoutParams(
                    parent.layoutParams.width,
                    parent.layoutParams.height
                )
                return holder
            }
        }

    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NotificationCardViewHolder) {
            holder.bind(items[position])
        } else {
            (holder as NotificationDateViewHolder).bind(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isDate) VIEW_DATE else VIEW_ITEM
    }

    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    class NotificationDateViewHolder(
        val view: ShimmeringNotificationDateItem
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: ShimmeringModelData) {
            view.apply {
                isShimmerOn = data.isShimmerOn
            }
        }
    }

    // ----------------------------------------------------------------------------------

    class NotificationCardViewHolder(
        val view: ShimmeringNotificationCardItem
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: ShimmeringModelData) {
            view.apply {
                isShimmerOn = data.isShimmerOn
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}