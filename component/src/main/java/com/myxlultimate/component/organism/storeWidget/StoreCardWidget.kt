package com.myxlultimate.component.organism.storeWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.bannerCard.adapters.BannerRecycleViewAdapter
import com.myxlultimate.component.organism.storeCard.StoreCard
import com.myxlultimate.component.organism.storeCard.enums.SizeMode
import com.myxlultimate.component.organism.storeWidget.shimmering.ShimmeringStoreCardWidget
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_store_card_widget.view.*


class StoreCardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isShimmerOn = false
        set(value) {
            field = value
            containerView.isShimmerOn = value
            shimmeringGroup.visibility = if (value) View.VISIBLE else View.GONE
            itemContainerView.visibility = if (value) View.GONE else View.VISIBLE

        }
    // ----------------------------------------------------------------------------------

    var numberOfCards = 2
        set(value) {
            field = value
            val list = mutableListOf<ShimmeringStoreCardWidget.Data>()
            for (i in 1..numberOfCards) {
                list.add(ShimmeringStoreCardWidget.Data(items[0].sizeMode, isShimmerOn))
            }
            shimmeringGroup.apply {
                items = list
            }
        }

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            containerView.label = value
        }

    var hasViewAll = true
        set(value) {
            field = value
            containerView.hasViewAllButton = value
        }

    // ----------------------------------------------------------------------------------


    var items = mutableListOf<StoreCard.Data>()
        set(value) {
            field = value

            recycleAdapter.items = value
            if (items.isNotEmpty()) {
                recycleAdapter.sizeMode = items[0].sizeMode
                setUpOrientation()
            } else {
                recycleAdapter.sizeMode = SizeMode.LARGE
            }

        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------


    var isSnap = false
        set(value) {
            field = value
            if (value) {
                try {
                    itemContainerView.onFlingListener
                    val snapHelper = LinearSnapHelper()
                    snapHelper.attachToRecyclerView(itemContainerView)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

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
            .inflate(R.layout.organism_store_card_widget, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StoreCardWidgetAttr)
        typedArray.run {
            label = getString(R.styleable.StoreCardWidgetAttr_label) ?: ""
            hasViewAll = getBoolean(R.styleable.StoreCardWidgetAttr_hasSeeAll, true)
            isSnap = getBoolean(R.styleable.StoreCardWidgetAttr_isSnap, false)
        }

        itemContainerView.apply {
            adapter = recycleAdapter.also { it.items = items }
        }

    }

    private fun setUpOrientation() {
        if (items.isNotEmpty()) {
            val hasRibbon = items[0].sizeMode == SizeMode.WITH_RIBBON
            val layoutManager =
                LinearLayoutManager(context, if (hasRibbon) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL, false)
            itemContainerView.layoutManager = layoutManager
            if (hasRibbon) {
                itemContainerView.apply {addItemDecoration(ListUtil.getListGapDecorator(context, 1, false)) }
            } else {
                itemContainerView.apply {addItemDecoration(ListUtil.getListGapDecorator(context, 0, true)) }
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is StoreCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                StoreCard.Data(
                    child.backgroundImage,
                    child.backgroundColorMode,
                    child.sizeMode,
                    child.category,
                    child.categoryColor,
                    child.title,
                    child.price,
                    child.originalPrice,
                    child.backgroundImageBase64,
                    hasRedDot = child.hasRedDot,
                    iconImage=child.iconImage,
                    subtitle = child.subtitle,
                    validity = child.validity,
                    ribbonTitle = child.ribbonTitle,
                    isBackground = child.isBackground,
                    colorBackground = child.colorBackground
                )
            )
            recycleAdapter.sizeMode = child.sizeMode
            recycleAdapter.items = items
            setUpOrientation()
        }
    }
}