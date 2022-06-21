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
import com.myxlultimate.component.organism.packageCard.OptionPackageCard
import com.myxlultimate.component.organism.packageCard.adapters.OptionPackageCardRecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_loyalty_card_widget.view.*


class LoyaltyCardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            containerView.label = value
        }
    // ----------------------------------------------------------------------------------

    var hasViewAll = true
        set(value) {
            field = value
            containerView.hasViewAllButton = value
        }

    // ----------------------------------------------------------------------------------


    var items = mutableListOf<OptionPackageCard.Data>()
        set(value) {
            field = value

            recycleAdapter.items = value

        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var hasLines = true
        set(value) {
            field = value
            if(hasLines) {
                itemContainerView.apply {
                    addItemDecoration(
                        ListUtil.getListGapDecorator(
                            context,
                            0,
                            false
                        )
                    )
                }
                containerView.removeShadow = false
            } else {
                containerView.removeShadow = true
            }
        }

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
        OptionPackageCardRecycleViewAdapter { index ->
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
            .inflate(R.layout.organism_loyalty_card_widget, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StoreCardWidgetAttr)
        typedArray.run {
            label = getString(R.styleable.StoreCardWidgetAttr_label) ?: ""
            hasViewAll = getBoolean(R.styleable.StoreCardWidgetAttr_hasSeeAll, true)
            isSnap = getBoolean(R.styleable.StoreCardWidgetAttr_isSnap, false)
            hasLines = getBoolean(R.styleable.StoreCardWidgetAttr_hasLines,true)
        }

        itemContainerView.apply {
            adapter = recycleAdapter.also { it.items = items }
        }

    }

    private fun setUpOrientation() {
        if (items.isNotEmpty()) {
            val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemContainerView.layoutManager = layoutManager
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is OptionPackageCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                OptionPackageCard.Data(
                    child.name,
                    child.validity,
                    child.price,
                    child.originalPrice,
                    child.information,
                    child.hasNextButton,
                    child.hasPromo,
                    child.image,
                    child.availability,
                    child.ribbonTitle,
                    child.isShimmerOn,
                    child.isDisabled,
                    child.isLock,
                    child.bottomTitle,
                    child.optionTextSizeMode,
                    child.bottomTitleColor
                )
            )
            recycleAdapter.items = items
            setUpOrientation()

        }
    }
}