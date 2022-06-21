package com.myxlultimate.component.organism.loyaltyCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.loyaltyCard.adapter.LoyaltyHistoryCardGroupAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_loyalty_history_card_group.view.*

class LoyaltyHistoryCardGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<LoyaltyHistoryCard.Data>()
        set(value) {
            field = value
            loyaltyHistoryCardGroupAdapter.items = value
        }

    var onItemPress: ((Int) -> Unit)? = null

    private val loyaltyHistoryCardGroupAdapter by lazy {
        LoyaltyHistoryCardGroupAdapter { index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_history_card_group, this, true)

        rvLoyaltyHistoryCardGroup.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 4))
            adapter = loyaltyHistoryCardGroupAdapter.also {
                it.items = items
            }
        }
    }

}