package com.myxlultimate.component.organism.bizOnItem.bizOnCashbackHistory

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.bizOnItem.bizOnCashbackHistory.adapter.CashbackHistoryAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_list_cashback_history.view.*

class CashbackListHistoryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<CashbackHistoryItemCard.Data>()
        set(value) {
            field = value
            cashbackHistoryAdapter.items = value
        }
    var onItemPress: ((Int) -> Unit)? = null
    // ----------------------------------------------------------------------------------

    private val cashbackHistoryAdapter by lazy { CashbackHistoryAdapter { index ->
            onItemPress?.let {
                it(index)
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_list_cashback_history, this, true)

        listCashbackHistory.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = cashbackHistoryAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is CashbackHistoryItemCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                CashbackHistoryItemCard.Data(
                    child.label,
                    child.amount,
                    child.imageSource,
                    child.imageSourceType,
                    child.information,
                    child.showButtonTitle,
                    child.buttonTitle,
                    child.onItemClick
                )
            )
        }
    }

}