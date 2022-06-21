package com.myxlultimate.component.organism.storeWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.SizeMode
import com.myxlultimate.component.organism.packageCard.FamilyPackageCard
import com.myxlultimate.component.organism.packageCard.adapters.CompactPackageFamilyRecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_store_widget_cross_sales.view.*


class PackageFamilyStoreWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<FamilyPackageCard.Data>()
        set(value) {
            field = value

            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        CompactPackageFamilyRecycleViewAdapter { index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_store_widget_package_family, this, true)

        itemContainerView.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 0, true))
            adapter = recycleAdapter.also { it.items = items }

        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is FamilyPackageCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                FamilyPackageCard.Data(
                    child.backgroundImage,
                    SizeMode.COMPACT,
                    child.name,
                    child.description,
                    child.showLabelPromo,
                    child.label,
                    child.backgroundImageCardUrl,
                    child.backgroundMode,
                    child.rightImage,
                    child.showTopLabel
                ))
            recycleAdapter.items = items
        }
    }
}