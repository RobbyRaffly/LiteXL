package com.myxlultimate.component.organism.troubleshootIDetailItemInformationCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.troubleshootIDetailItemInformationCard.adapter.TroubleshootDetailItemListAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_troubleshoot_detail_item_information_card.view.*

class TroubleshootIDetailItemInformationCard@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var topTitle = ""
    set(value) {
        field = value
        topTitleView.text = value

    }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var items = mutableListOf<TroubleshootIDetailItem.Data>()
    set(value) {
        field = value
        recycleAdapter.items = value
    }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        TroubleshootDetailItemListAdapter{ index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


// ----------------------------------------------------------------------------------
    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_troubleshoot_detail_item_information_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TroubleshootIDetailItemInformationCard)
        typedArray.run {
            topTitle = getString(R.styleable.TroubleshootIDetailItemInformationCard_topTitle)?:""
        }
        typedArray.recycle()

        troubleshootDetailItemRecyclerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = recycleAdapter
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is TroubleshootIDetailItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                TroubleshootIDetailItem.Data(
                    child.leftTitle,
                    child.rightTitle
                )
            )
            updateItems(items)
        }
    }

    // ----------------------------------------------------------------------------------

    private fun updateItems(items: List<TroubleshootIDetailItem.Data>) {
        if (!troubleshootDetailItemRecyclerView.isComputingLayout)
            recycleAdapter.items = items
    }
}