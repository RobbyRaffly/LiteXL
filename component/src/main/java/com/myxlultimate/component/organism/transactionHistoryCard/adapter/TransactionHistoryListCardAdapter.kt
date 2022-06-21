package com.myxlultimate.component.organism.transactionHistoryCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.transactionHistoryCard.TransactionHistoryCard
import com.myxlultimate.component.token.imageView.ImageSourceType

class TransactionHistoryListCardAdapter(
    val onItemPress: (Int) -> Unit
) : RecyclerView.Adapter<TransactionHistoryListCardAdapter.ViewHolder>() {

    var items = listOf<TransactionHistoryCard.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            TransactionHistoryCard(parent.context)
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

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: TransactionHistoryCard
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: TransactionHistoryCard.Data) {
            view.apply {
                name = data.name
                dateTime = data.dateTime
                transactionDate = data.transactionDate
                dateTimeString = data.dateTimeString
                priceString = data.priceString
                originalPriceString = data.originalPriceString
                hasNextButton = data.hasNextButton
                isForHistory = data.isForHistory
                imageSourceType = data.imageSourceType
                imageSource = data.imageSource
                rightBottomDescription = data.rightBottomDescription?:""
                imageBottomSourceType = data.imageBottomSourceType?: ImageSourceType.BASE64
                imageBottomSource = data.imageBottomSource?:""
                onCardPress = data.onCardPress
            }
        }
    }

}