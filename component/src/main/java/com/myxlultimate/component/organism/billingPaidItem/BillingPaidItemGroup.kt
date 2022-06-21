package com.myxlultimate.component.organism.billingPaidItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.billingPaidItem.adapter.BillingPaidItemAdapter
import kotlinx.android.synthetic.main.organism_billing_paid_item_group.view.*

class BillingPaidItemGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<BillingPaidItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }


    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        BillingPaidItemAdapter()
    }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_paid_item_group, this, true)

        billingItemContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is BillingPaidItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                BillingPaidItem.Data(
                    child.title,
                    child.dateTime,
                    child.dateTimeString,
                    child.price,
                    child.priceString
                )
            )
            recycleAdapter.items = items
        }
    }

}