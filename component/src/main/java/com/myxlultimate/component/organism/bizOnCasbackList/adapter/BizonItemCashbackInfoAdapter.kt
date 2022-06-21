package com.myxlultimate.component.organism.bizOnCasbackList.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaType
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem
import com.myxlultimate.component.molecule.packageBenefit.enums.BenefitMode

class BizonItemCashbackInfoAdapter : RecyclerView.Adapter<BizonItemCashbackInfoAdapter.ViewHolder>() {

    var items = listOf<PackageBenefitItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
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
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: PackageBenefitItem
    ) : RecyclerView.ViewHolder(view) {

        fun bind( data: PackageBenefitItem.Data, index: Int) {
            view.apply {
                amount = data.amount?: 0
                iconImage = data.iconImage ?: ""
                information = data.information?: ""
                name = data.name?: ""
                labelCta = resources.getString(R.string.xl_bizon_title_cta)
                benefitMode = BenefitMode.WITHCTA
                quotaType = QuotaType.PRICE
                isUnlimited = data.isUnlimited?: false
                onCtaPress = {
                    data.onCtaPress?.invoke(index)
                }
            }
        }
    }

}