package com.myxlultimate.component.organism.storeWidget.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.storeWidget.shimmering.adapter.ShimmeringPackageFamilyAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.shimmering_package_family_group.view.*

class ShimmeringPackageFamilyGroup  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<ShimmeringPackageFamilyItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var numbersOfCards = 3
        set(value) {
            field = value
            for (i in 1..value) {
                items.add(ShimmeringPackageFamilyItem.Data(true))
            }
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        ShimmeringPackageFamilyAdapter()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_package_family_group, this, true)

        shimmeringPackageFamilyGroup.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is ShimmeringPackageFamilyItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                ShimmeringPackageFamilyItem.Data(
                    child.isShimmerOn
                )
            )
            recycleAdapter.items = items
        }
    }
}