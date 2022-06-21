package com.myxlultimate.component.organism.billingItem.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.billingItem.BillingItem
import com.myxlultimate.component.organism.billingItem.BillingItemType

class BillingItemAdapter(private val itemListener: OnItemClickListener) :
    RecyclerView.Adapter<BillingItemAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onDetailClick(data: BillingItem.Data, index: Int)
        fun onDownloadClick(data: BillingItem.Data, index: Int)
    }

    var items = listOf<BillingItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BillingItem(
                parent.context
            ),
            itemListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position, items.size)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: BillingItem,
        private val itemListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: BillingItem.Data, index: Int, itemSize: Int) {
            view.apply {
                dueDate = data.dueDate ?: 0L
                dueDateString = data.dueDateString ?: ""
                monthOfYear = data.monthOfYear ?: ""
                billPrice = data.billPrice ?: 0L
                billPriceString = data.billPriceString ?: ""
                billStatus = data.billStatus ?: BillingItemType.OPEN
                debtPrice = data.debtPrice ?: 0L
                debtPriceString = data.debtPriceString ?: ""
                hasLine = index + 1 != itemSize
//                onItemPressed = { onItemPressed(data, index) }
                onDetailPressed = {
                    itemListener?.onDetailClick(data, index)
                }
                onDownloadPressed = {
                    itemListener?.onDownloadClick(data, index)
                }
                startDate = data.startDate?: ""
                endDate = data.endDate?: ""
                showDownload = data.showDownload?: true
            }
        }
    }

}