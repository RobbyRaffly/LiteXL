package com.myxlultimate.component.organism.notificationCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.notificationCard.NotificationCard
import com.myxlultimate.component.token.imageView.ImageSourceType

class RecycleViewAdapter(val onItemPress: (Int) -> Unit): RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

    var items = listOf<NotificationCard.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: NotificationCard,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: NotificationCard.Data, index: Int) {
            view.apply {
                title = data.title
                imageSourceType = data.imageSourceType
                image = data.image
                isRead = data.isRead
                onPress = {onItemPress(index)}
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NotificationCard(
                parent.context
            ),
            onItemPress
        )
    }


}