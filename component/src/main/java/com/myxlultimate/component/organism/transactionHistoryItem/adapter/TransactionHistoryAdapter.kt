package com.myxlultimate.component.organism.transactionHistoryItem.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.transactionHistoryCard.TransactionDate
import com.myxlultimate.component.organism.transactionHistoryItem.TransactionHistoryItem
import com.myxlultimate.component.organism.transactionHistoryItem.TransactionHistoryType
import com.myxlultimate.component.token.imageView.ImageSourceType

class TransactionHistoryAdapter(): RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>(){

    var items = listOf<TransactionHistoryItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: TransactionHistoryItem,
        val itemCount : Int
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: TransactionHistoryItem.Data, index: Int) {
            view.apply {
                transactionHistoryType = data.transactionHistoryType?:TransactionHistoryType.TEXT
                name = data.name
                dateTime = data.dateTime?:0
                transactionDate = TransactionDate.DATETIME
                price = data.price?:0
                priceString = data.priceString?:""
                detail = data.detail?:""
                hasLine = (itemCount-1)!=index
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
            TransactionHistoryItem(
                parent.context
            ),
            items.size
        )
    }


}