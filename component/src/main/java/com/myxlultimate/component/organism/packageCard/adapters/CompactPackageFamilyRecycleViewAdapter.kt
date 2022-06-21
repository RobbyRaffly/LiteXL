package com.myxlultimate.component.organism.packageCard.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.enums.SizeMode
import com.myxlultimate.component.organism.bannerCard.adapters.BannerRecycleViewAdapter
import com.myxlultimate.component.organism.packageCard.FamilyPackageCard
import com.myxlultimate.component.organism.storeCard.StoreCard

class CompactPackageFamilyRecycleViewAdapter(
    val onItemPress: (Int) -> Unit
) : RecyclerView.Adapter<CompactPackageFamilyRecycleViewAdapter.ViewHolder>() {

    var items = listOf<FamilyPackageCard.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: FamilyPackageCard,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: FamilyPackageCard.Data, index: Int) {
            view.apply {
                backgroundImage = data.backgroundImage
                sizeMode = data.sizeMode
                name = data.name
                description = data.description
                sizeMode = SizeMode.COMPACT
                showLabelPromo = data.showLabelPromo
                label = data.label
                onPress = { onItemPress(index) }
                backgroundImageCardUrl = data.backgroundImageCardUrl
                backgroundMode = data.backgroundMode
                rightImage = data.rightImage
                showTopLabel = data.showTopLabel
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val holder = CompactPackageFamilyRecycleViewAdapter.ViewHolder(
            FamilyPackageCard(parent.context),
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