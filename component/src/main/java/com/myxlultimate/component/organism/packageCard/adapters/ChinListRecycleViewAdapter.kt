package com.myxlultimate.component.organism.packageCard.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem
import com.myxlultimate.component.organism.packageCard.OptionPackageCard
import com.myxlultimate.component.token.imageView.ImageSourceType

class ChinListRecycleViewAdapter : RecyclerView.Adapter<ChinListRecycleViewAdapter.ViewHolder>() {

    var items = listOf<PackageBenefitItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(
        val view : PackageBenefitItem
    ) : RecyclerView.ViewHolder(view){
        fun bind(data : PackageBenefitItem.Data ){
            view.apply {
                name = data.name
                iconImageSourceType = data.iconImageSourceType ?: ImageSourceType.BASE64
                iconImage = data.iconImage
                use16DpIcon = data.use16DpIcon
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            PackageBenefitItem(
                parent.context
            )
        )

        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}