package com.myxlultimate.component.organism.packageCard.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.packageCard.OptionPackageCard
import com.myxlultimate.component.organism.packageCard.shimmering.adapter.AddOnShimmeringAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.shimmering_add_on_group.view.*

class AddOnShimmeringGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<OptionPackageCard.addOnShimer>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var numbersOfCards = 3
        set(value) {
            field = value
            for (i in 1..value) {
                items.add(OptionPackageCard.addOnShimer(true))
            }
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        AddOnShimmeringAdapter()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_add_on_group, this, true)

        shimmeringAddOnGroup.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = recycleAdapter.also { it.items = items }
        }

        numbersOfCards = numbersOfCards
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is OptionPackageCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                OptionPackageCard.addOnShimer(
                    child.isShimmerOn
                )
            )
            recycleAdapter.items = items
        }
    }
}