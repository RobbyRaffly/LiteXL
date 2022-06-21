package com.myxlultimate.component.organism.billingEstimationItem.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.billingEstimationItem.BillingEstimationItem

class BillingEstimationAdapter() : RecyclerView.Adapter<BillingEstimationAdapter.ViewHolder>() {

    var items = listOf<BillingEstimationItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            BillingEstimationItem(
                parent.context
            ),
            items.size
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position],position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: BillingEstimationItem,
        val itemCount : Int
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: BillingEstimationItem.Data, index : Int) {
            view.apply {
                period = data.period ?: ""
                dateTime = data.dateTime ?: 0
                dateTimeString = data.dateTimeString ?: ""
                pricePeriod = data.pricePeriod ?: 0
                pricePeriodString = data.pricePeriodString ?: ""
            }
        }
    }

}