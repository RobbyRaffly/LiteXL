package com.myxlultimate.component.organism.packageCard.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.packageCard.OptionPackageCard
import com.myxlultimate.component.organism.packageCard.enums.AvailabilityMode
import com.myxlultimate.component.organism.packageCard.enums.TransactionStatus

class OptionPackageCardRecycleViewAdapter(val onItemPress: (Int) -> Unit): RecyclerView.Adapter<OptionPackageCardRecycleViewAdapter.ViewHolder>(){

    var items = listOf<OptionPackageCard.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: OptionPackageCard,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: OptionPackageCard.Data, index: Int) {
            view.apply {
                name = data.name
                validity = data.validity
                price = data.price
                originalPrice = data.originalPrice
                information = data.information
                hasNextButton = data.hasNextButton
                image = data.image
                onNextPress = {onItemPress(index)}
                onCardPress = {onItemPress(index)}
                availability = data.availability?:AvailabilityMode.NONE
                isDisabled = data.isDisabled
                isLock = data.isLock
                bottomTitle = data.bottomTitle
                optionTextSizeMode = data.optionTextSizeMode
                bottomTitleColor = data.bottomTitleColor
                transactionStatus = data.transactionStatus ?: TransactionStatus.NONE
                isForHistory = data.isForHistory
                upperRightIcon = data.upperRightIcon
                loyaltyRibbonTitle = data.loyaltyRibbonTitle
                loyaltyIcon = data.loyaltySmallIcon
                startHexColor = data.startLoyaltyRibbonHexColor
                endHexColor = data.endLoyaltyRibbonHexColor
                useSmallTitle = data.useSmallTitle
                ribbonTitle = data.ribbonTitle
                hasChinView = data.hasChinView
                chinListItem = data.chinListItems
                cardBackgorundMode = data.cardBackgroundMode
                ribbonMode = data.ribbonMode
                backgroundUrl = data.backgroundUrl
                setRibbonColor = data.setRibbonColor
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
            OptionPackageCard(
                parent.context
            ),
            onItemPress
        )
    }


}