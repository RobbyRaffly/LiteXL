package com.myxlultimate.component.organism.storeWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearSnapHelper
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.bannerCard.BannerCard
import com.myxlultimate.component.organism.bannerCard.adapters.BannerRecycleViewAdapter
import com.myxlultimate.component.organism.storeCard.StoreCard
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_store_widget_hot_banner.view.*

class HotBannerStoreWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<StoreCard.Data>()
        set(value) {
            field = value

            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onViewAllButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onViewAllButtonPress = value
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        BannerRecycleViewAdapter { index ->
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
            .inflate(R.layout.organism_store_widget_hot_banner, this, true)

        itemContainerView.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 0, true))
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is StoreCard) {
            super.addView(child, index, params)
        } else {
            items.add(StoreCard.Data(
                child.backgroundImage,
                child.backgroundColorMode,
                child.sizeMode,
                child.category,
                child.categoryColor,
                child.title,
                child.price,
                child.originalPrice
            ))
            recycleAdapter.items = items
        }
    }
}