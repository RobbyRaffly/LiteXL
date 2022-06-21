package com.myxlultimate.component.organism.activePlanListItemWidgetCard.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem

class BonusAreaBenefitWidgetAdapter(): RecyclerView.Adapter<BonusAreaBenefitWidgetAdapter.ViewHolder>(){

    var items = listOf<PackageBenefitItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: PackageBenefitItem,
        val itemCount: Int
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: PackageBenefitItem.Data, index: Int) {
            view.apply {
                name = data.name
                iconImage = data.iconImage
                information = data.information
                quotaType = data.quotaType
                amount = data.amount
                isUnlimited = data.isUnlimited
                isShimmerOn = data.isShimmerOn
                benefitMode = data.benefitMode
                labelCta = data.labelCta
                bigLabelCta = data.bigTitleCta
                onCtaPress = {
                    data.onCtaPress?.invoke(index)
                }
                hasGapTop = true
                hasGapBottom = true
                hasBorderBottom = index < (itemCount - 1)
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
            PackageBenefitItem(
                parent.context
            ),
            items.size
        )
    }


}