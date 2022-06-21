package com.myxlultimate.component.organism.missionListCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.missionListCard.MissionListItemCard
import com.myxlultimate.component.organism.missionListCard.MissionStatus

class MissionListCardsAdapter(val onItemPress: (Int) -> Unit) : RecyclerView.Adapter<MissionListCardsAdapter.ViewHolder>() {

    var items = listOf<MissionListItemCard.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            MissionListItemCard(
                parent.context
            ),
            onItemPress
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
        val view: MissionListItemCard,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: MissionListItemCard.Data, index: Int) {
            view.apply {
                title = data.title
                buttonTitle = data.buttonTitle ?:""
                information = data.information
                imageSource = data.imageSource
                imageIcon = data.iconImage?: R.drawable.ic_postpaid_egg_icon
                missionStatus = data.missionStatus ?: MissionStatus.NONE
                isDisabled = data.isDisabled ?: false
                onPress = {onItemPress(index)}
            }
        }
    }

}