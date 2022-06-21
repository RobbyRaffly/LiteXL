package com.myxlultimate.component.molecule.packageVarianBenefit.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.molecule.packageVarianBenefit.PackageVariantBenefitItem

class RecycleViewAdapter: RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

    var items = listOf<PackageVariantBenefitItem.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: PackageVariantBenefitItem
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: PackageVariantBenefitItem.Data, index: Int) {
            view.apply {
                name = data.name
                iconImage = data.iconImage
                isShimmerOn = data.isShimmerOn
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
        val holder =  ViewHolder(
            PackageVariantBenefitItem(
                parent.context
            )
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        return holder
    }


}