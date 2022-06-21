package com.myxlultimate.component.organism.activePlanList

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.activePlanList.adapter.ActivePlanListAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_active_plan_group.view.*

class ActivePlanListGroup@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<ActivePlanListItem.Data>()
        set(value) {
            field = value
            activePlanListAdapter.items = value
        }

    var onItemPress: ((Int) -> Unit)? = null

    private val activePlanListAdapter by lazy {
        ActivePlanListAdapter()
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_active_plan_group, this, true)

        itemContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = activePlanListAdapter.also {
                it.items = items
            }
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is ActivePlanListItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                ActivePlanListItem.Data(
                    child.iconImage,
                    child.information,
                    child.title
                )
            )
            activePlanListAdapter.items = items
        }
    }
}