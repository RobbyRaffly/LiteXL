package com.myxlultimate.component.organism.notificationCard.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.notificationCard.shimmering.adapter.ShimmeringNotificationCardGroupAdapter
import com.myxlultimate.component.organism.notificationCard.shimmering.model.ShimmeringModelData
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.shimmering_notification_card_group.view.*

class ShimmeringNotificationCardGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<ShimmeringModelData>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        ShimmeringNotificationCardGroupAdapter()
    }

    // ----------------------------------------------------------------------------------

    var numbersOfCards: Pair<Int, Int> = Pair(2, 3)
        set(value) {
            field = value
            for (date in 1..value.first) {
                items.add(ShimmeringModelData(isDate = true, isShimmerOn = true))
                for (item in 1..value.second) {
                    items.add(ShimmeringModelData(isDate = false, isShimmerOn = true))
                }
            }
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_notification_card_group, this, true)

        shimmeringNotificationCardGroup.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 0))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}