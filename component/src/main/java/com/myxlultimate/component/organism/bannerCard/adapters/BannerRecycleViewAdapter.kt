package com.myxlultimate.component.organism.bannerCard.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.storeCard.StoreCard
import com.myxlultimate.component.organism.storeCard.enums.SizeMode

class BannerRecycleViewAdapter(
    val onItemPress: (Int) -> Unit
) : RecyclerView.Adapter<BannerRecycleViewAdapter.ViewHolder>() {

    var sizeMode: SizeMode = SizeMode.LARGE

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
                backgroundImage = data.backgroundImage
                backgroundColorMode = data.backgroundColorMode
                sizeMode = data.sizeMode
                category = data.category
                categoryColor = data.categoryColor
                title = data.title
                price = data.price
                originalPrice = data.originalPrice
                onPress = { onItemPress(index) }
                backgroundImageBase64 = data.backgroundImageBase64
                hasRedDot = data.hasRedDot
                iconImage = data.iconImage
                subtitle = data.subtitle ?: ""
                validity = data.validity ?: ""
                ribbonTitle = data.ribbonTitle ?: ""
                isBackground = data.isBackground
                colorBackground = data.colorBackground
                description = data.description
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

        if (sizeMode == SizeMode.SQUARE || sizeMode == SizeMode.RECTANGLE || sizeMode == SizeMode.SMALL_LONG || sizeMode == SizeMode.POSTER
            || sizeMode == SizeMode.SMALL || sizeMode == SizeMode.ALBUM || sizeMode == SizeMode.SQUARE_THUMBNAIL_1 || sizeMode == SizeMode.SQUARE_THUMBNAIL_2 || sizeMode == SizeMode.SQUARE_THUMBNAIL_3 || sizeMode == SizeMode.SQUARE_THUMBNAIL_4) {
            holder.view.layoutParams = ViewGroup.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        } else {
            holder.view.layoutParams = ViewGroup.LayoutParams(
                parent.layoutParams.width,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        }

        return holder
    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

}