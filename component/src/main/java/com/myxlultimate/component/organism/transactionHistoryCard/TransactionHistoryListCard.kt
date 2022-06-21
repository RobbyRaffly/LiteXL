package com.myxlultimate.component.organism.transactionHistoryCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.transactionHistoryCard.adapter.TransactionHistoryListCardAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_list_transaction_history.view.*

class TransactionHistoryListCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<TransactionHistoryCard.Data>()
        set(value) {
            field = value
            transactionHistoryListCardAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            tvDateTransaction.text = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val transactionHistoryListCardAdapter by lazy { TransactionHistoryListCardAdapter { index ->
            onItemPress?.let { it(index) }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_list_transaction_history, this, true)

        listTransactionHistory.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = transactionHistoryListCardAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is TransactionHistoryCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                TransactionHistoryCard.Data(
                    child.name,
                    child.dateTime,
                    child.transactionDate,
                    child.dateTimeString,
                    child.priceString,
                    child.originalPriceString,
                    child.hasNextButton,
                    child.isForHistory,
                    child.imageSourceType,
                    child.imageSource,
                    child.rightBottomDescription,
                    child.imageBottomSourceType,
                    child.imageBottomSource,
                    child.onCardPress
                )
            )
        }
    }

}