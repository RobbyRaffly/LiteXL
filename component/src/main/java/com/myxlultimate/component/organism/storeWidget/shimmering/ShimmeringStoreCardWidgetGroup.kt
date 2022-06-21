package com.myxlultimate.component.organism.storeWidget.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.storeWidget.shimmering.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.shimmering_store_card_widget_group.view.*

class ShimmeringStoreCardWidgetGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<ShimmeringStoreCardWidget.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        RecycleViewAdapter()
    }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_store_card_widget_group, this, true)

        shimmeringStoreCardWidgetGroup.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 0))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is ShimmeringStoreCardWidget) {
            super.addView(child, index, params)
        } else {
            items.add(
                ShimmeringStoreCardWidget.Data(
                    child.sizeMode,
                    child.isShimmerOn
                )
            )
            recycleAdapter.items = items
        }
    }

}