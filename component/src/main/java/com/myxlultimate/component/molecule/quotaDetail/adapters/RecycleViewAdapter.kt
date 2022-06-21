package com.myxlultimate.component.molecule.quotaDetail.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.enums.FupLimitRule
import com.myxlultimate.component.molecule.quotaDetail.QuotaDetailItem

class RecycleViewAdapter : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    var items = listOf<QuotaDetailItem.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: QuotaDetailItem
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: QuotaDetailItem.Data, index: Int) {
            view.apply {
                name = data.name
                quotaType = data.quotaType
                iconImage = data.iconImage
                information = data.information
                remaining = data.remaining
                total = data.total
                isUnlimited = data.isUnlimited
                isFup = data.isFup
                fupLimitRule = data.fupLimitRule
                fupText = data.fupText
                lowThreshold = data.lowThreshold
                disableDetailItem = data.disableDetailItem?:false
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            QuotaDetailItem(
                parent.context
            )
        )
    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

}