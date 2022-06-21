package com.myxlultimate.component.organism.billingPaidItem.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.billingPaidItem.BillingPaidItem

class BillingPaidItemAdapter() : RecyclerView.Adapter<BillingPaidItemAdapter.ViewHolder>() {

    var items = listOf<BillingPaidItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            BillingPaidItem(
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
        val view: BillingPaidItem,
        val itemCount : Int
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: BillingPaidItem.Data, index : Int) {
            view.apply {
                title = data.title
                dateTime = data.dateTime ?: 0
                dateTimeString = data.dateTimeString ?: ""
                price = data.price ?: 0
                priceString = data.priceString ?: ""
                hasLine = (itemCount-1)!=index
            }
        }
    }
}