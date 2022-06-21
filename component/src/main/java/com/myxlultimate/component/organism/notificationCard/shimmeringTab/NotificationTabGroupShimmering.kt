package com.myxlultimate.component.organism.notificationCard.shimmeringTab

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.notificationCard.shimmeringTab.adapter.NotificationTabItemShimmeringAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.notification_tab_group_shimmering.view.*

class NotificationTabGroupShimmering @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<NotificationTabItemShimmering.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }
    // ----------------------------------------------------------------------------------

    var numbersOfTab = 3
        set(value) {
            field = value
            for (i in 1..value) {
                items.add(NotificationTabItemShimmering.Data(true))
            }
        }


    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        NotificationTabItemShimmeringAdapter()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.notification_tab_group_shimmering, this, true)

        notificationTabGroupShimmering.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 0,true))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is NotificationTabItemShimmering) {
            super.addView(child, index, params)
        } else {
            items.add(
                NotificationTabItemShimmering.Data(
                    child.isShimmerOn
                )
            )
            recycleAdapter.items = items
        }
    }
}