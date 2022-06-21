package com.myxlultimate.component.organism.loyaltyTableCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.loyaltyTableCard.adapter.LoyaltyTableCardGroupAdapter
import com.myxlultimate.component.organism.missionListCard.MissionListItemCard
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_loyalty_table_card_group.view.*

class LoyaltyTableCardGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val items : MutableList<LoyaltyTableCard.Data>,
        val title : String
    )

    // ----------------------------------------------------------------------------------
    private var recycleAdapter: LoyaltyTableCardGroupAdapter? = null

    var onCardPress : ((Int, LoyaltyTableCard.Data)-> Unit)? = null

    var items = mutableListOf<LoyaltyTableCard.Data>()
        set(value) {
            field = value
            recycleAdapter?.items = value
        }

    var title = ""
        set(value) {
            field = value
            refreshView()
        }


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_table_card_group, this, true)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LoyaltyTableCardGroup
        )

        typedArray.run {
            title = getString(R.styleable.LoyaltyTableCardGroup_title)?:""
        }
        typedArray.recycle()
        recycleAdapter = LoyaltyTableCardGroupAdapter(
            onCardPress = { i, data ->
                onCardPress?.let { it(i,data) }
            }
        )
        loyaltyTableCardRv.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 8,false))
            adapter = recycleAdapter.also { it?.items = items }
        }


    }

    // ----------------------------------------------------------------------------------

    private fun refreshView(){
        labelView.text = title
        IsEmptyUtil.setVisibility(title,labelView)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is LoyaltyTableCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                LoyaltyTableCard.Data(
                    child.items,
                    child.name,
                    child.hasRightArrowIcon
                )
            )
            recycleAdapter?.items = items
        }
    }
}