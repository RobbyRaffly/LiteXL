package com.myxlultimate.component.organism.bannerCard.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.bannerCard.DeviceBannerCard
import com.myxlultimate.component.organism.storeCard.StoreCard
import com.myxlultimate.component.organism.storeCard.enums.SizeMode

class DeviceRecycleViewAdapter (
    val onItemPress: (Int) -> Unit
) : RecyclerView.Adapter<DeviceRecycleViewAdapter.ViewHolder>() {

    var items = listOf<StoreCard.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: StoreCard,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: StoreCard.Data, index: Int) {
            view.apply {
                sizeMode = SizeMode.SMALL
                backgroundImage = data.backgroundImage
                backgroundColorMode = data.backgroundColorMode
                category = data.category
                categoryColor = data.categoryColor
                title = data.title
                onPress = { onItemPress(index) }
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            StoreCard(parent.context),
            onItemPress
        )

        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        return holder
    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

}