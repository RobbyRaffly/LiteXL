package com.myxlultimate.component.organism.bizOnItem.bizOnActivation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.bizOnItem.bizOnActivation.adapter.CashbackActivationAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_list_cashback_history.view.*

class CashbackListActivationCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<CashbackActivationItemCard.Data>()
        set(value) {
            field = value
            cashbackActivationAdapter.items = value
        }
    var onItemPress: ((Int) -> Unit)? = null
    // ----------------------------------------------------------------------------------

    private val cashbackActivationAdapter by lazy { CashbackActivationAdapter { index ->
            onItemPress?.let {
                it(index)
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_list_cashback_activation, this, true)

        listCashbackHistory.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = cashbackActivationAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is CashbackActivationItemCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                CashbackActivationItemCard.Data(
                    child.title,
                    child.iconItem,
                    child.label,
                    child.amount,
                    child.isShowArrow,
                    child.isShowButtonLine
                )
            )
        }
    }

}