package com.myxlultimate.component.organism.loyaltyTableCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.loyaltyTableCard.adapter.LoyaltyTableItemAdapter
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_tier_status_detail_item_card.view.*

class LoyaltyTableCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val items : MutableList<LoyaltyTableItem.Data>,
        val name : String,
        val hasRightArrowIcon : Boolean
    )

    // ----------------------------------------------------------------------------------
    private val recycleAdapter by lazy {
        LoyaltyTableItemAdapter()
    }
    var items = mutableListOf<LoyaltyTableItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    var name = ""
    set(value) {
        field = value
        refreshView()
    }

    var hasRightArrowIcon = false
    set(value) {
        field = value
        refreshView()
    }

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            if(hasRightArrowIcon) {
                TouchFeedbackUtil.attach(cardView, value)
            } else {
                TouchFeedbackUtil.detach(cardView)
            }
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_tier_status_detail_item_card, this, true)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LoyaltyTableCard
        )

        typedArray.run {
            name = getString(R.styleable.LoyaltyTableCard_title)?:""
            hasRightArrowIcon = getBoolean(R.styleable.LoyaltyTableCard_hasRightArrow,false)
        }
        typedArray.recycle()

        cardRv.apply {
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    private fun refreshView(){
        nameView.text = name
        IsEmptyUtil.setVisibility(hasRightArrowIcon,rightArrowView)
    }
}