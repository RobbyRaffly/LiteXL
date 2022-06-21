package com.myxlultimate.component.organism.billingItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.billingItem.adapter.BillingItemAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_billing_item_group.view.*

class BillingItemGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), BillingItemAdapter.OnItemClickListener {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<BillingItem.Data>()
        set(value) {
            field = value
            billingItemAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onDetailClick: ((BillingItem.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onDownloadClick: ((BillingItem.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------


    private val billingItemAdapter by lazy {
        BillingItemAdapter(this)
    }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_item_group, this, true)

        rvBillingItemGroup.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = billingItemAdapter.also {
                it.items = items
            }
        }
    }

    override fun onDetailClick(data: BillingItem.Data, index: Int) {
        onDetailClick?.let { it(data, index) }
    }

    // ----------------------------------------------------------------------------------

    override fun onDownloadClick(data: BillingItem.Data, index: Int) {
        onDownloadClick?.let { it(data, index) }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}