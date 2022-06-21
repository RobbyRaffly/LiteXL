package com.myxlultimate.component.organism.itemPaymentCard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PaymentRecyclerViewAdapter(
    private val onItemPress: (Int) -> Unit
) : RecyclerView.Adapter<PaymentRecyclerViewAdapter.ViewHolder>() {

    var items = listOf<ItemPaymentCardItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(
        val view: ItemPaymentCardItem,
        val itemCount: Int,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: ItemPaymentCardItem.Data, index: Int) {
            view.apply {
                titleDate = data.datePayment
                titleViewTypePayment = data.typePayment
                titlePrice = data.pricePayment
                if (titleViewTypePayment == "PRIO Flex"){
                    isShowIcon = true
                    onIconPress = {onItemPress(index)}
                } else {
                    isShowIcon = false
                }

                hasLine = (itemCount - 1) != index
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemPaymentCardItem(
                parent.context
            ),
            items.size,
            onItemPress
        )

    }

    override fun onBindViewHolder(holder: PaymentRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }


}