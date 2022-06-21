package com.myxlultimate.component.organism.transactionCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.transactionCard.TransactionPaymentMethodOptionCard

class RecycleViewAdapter(
    val onItemPress: (Int) -> Unit,
    val onTopUpClick: () -> Unit,
    val onDetailPromoButtonPress: (Int) -> Unit,
    val onButtonClicked: (Int) -> Unit
) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private var activeIndex = -1

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: TransactionPaymentMethodOptionCard
    ) : RecyclerView.ViewHolder(view) {
        fun bind(
            data: TransactionPaymentMethodOptionCard.Data,
            onIndexChange: (Boolean) -> Unit,
            onTopUpClick: () -> Unit,
            onDetailPromoButtonPress: () -> Unit,
            onButtonClicked: () -> Unit
        ) {
            view.apply {
                this.code = data.code
                this.name = data.name
                this.information = data.information
                this.balance = data.balance
                this.isBalanceVisible = data.isBalanceVisible
                this.cashbackPercentage = data.cashbackPercentage
                this.isBalanceEnough = data.isBalanceEnough
                this.isCardSelected = data.isCardSelected
                this.iconImage = data.iconImage
                this.imageSourceType = data.imageSourceType
                this.hasTopUpButton = data.hasTopUpButton
                this.onChange = onIndexChange
                this.onTopUpButtonPress = onTopUpClick
                this.balanceString = data.balanceString ?: ""
                this.ribbonTitle = data.ribbonTitle ?: ""
                this.promoButtonTitle = data.promoButtonTitle ?: ""
                this.promoStringTitle = data.promoStringTitle ?: ""
                this.bottomInformation = data.bottomInformation ?: ""
                this.bottomInformationUnderCardView = data.bottomInformationUnderCardView ?: ""
                this.onDetailPromoButtonPress = onDetailPromoButtonPress
                this.onButtonPress = onButtonClicked
                this.buttonLabel = data.buttonLabel
                this.description = data.description
                this.inactiveRadio = data.inactiveRadio
            }
        }
    }

    var items = listOf<TransactionPaymentMethodOptionCard.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TransactionPaymentMethodOptionCard(parent.context)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prevIndex = activeIndex
        if (items[position].isCardSelected && position != prevIndex) {
            activeIndex = position
            if (prevIndex != -1) onItemPress(activeIndex)
        }
        holder.bind(
            items[position],
            onIndexChange = {
                if (it && position != activeIndex) {
                    activeIndex = position
                    onItemPress(activeIndex)
                }
            },
            onTopUpClick = { onTopUpClick() },
            onDetailPromoButtonPress = { onDetailPromoButtonPress(position) },
            onButtonClicked = { onButtonClicked(position) }
        )
    }
}