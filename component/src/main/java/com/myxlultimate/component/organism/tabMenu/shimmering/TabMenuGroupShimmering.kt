package com.myxlultimate.component.organism.tabMenu.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.tabMenu.shimmering.adapters.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.shimmering_tab_menu_group.view.*

class TabMenuGroupShimmering @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<TabMenuItemShimmering.Data>()
        set(value) {
            field = value
        }
    // ----------------------------------------------------------------------------------

    var numbersOfTab = 3
        set(value) {
            field = value
            for (i in 1..value) {
                items.add(TabMenuItemShimmering.Data(true))
            }
        }


    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        RecycleViewAdapter()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_tab_menu_group, this, true)

        shimmeringTabMenuGroup.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8,true))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is TabMenuItemShimmering) {
            super.addView(child, index, params)
        } else {
            items.add(
                TabMenuItemShimmering.Data(
                    child.isShimmerOn
                )
            )
            recycleAdapter.items = items
        }
    }
}