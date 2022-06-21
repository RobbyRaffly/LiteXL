package com.myxlultimate.component.organism.billingEstimationItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.billingEstimationItem.adapter.BillingEstimationAdapter
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_billing_estimation_item_group.view.*

class BillingEstimationItemGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title: String = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value
            priceView.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value, true)
            )
        }

    // ----------------------------------------------------------------------------------

    var priceString: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                priceView.text = value
                priceTotalView.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var buttonLabel: String = ""
        set(value) {
            field = value

            if (value.isNullOrEmpty())
                buttonView.visibility = View.GONE
            else {
                buttonView.text = value
                buttonView.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    var hasButtonEnable: Boolean = false
        set(value) {
            field = value

            buttonView.isEnabled = value
        }

    var hasTotal: Boolean = false
        set(value) {
            field = value
            if(value) {
                priceView.visibility = View.GONE
                priceTotalView.visibility = View.VISIBLE
            } else {
                priceView.visibility = View.VISIBLE
                priceTotalView.visibility = View.GONE
            }
        }

    var buttonPressed: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(buttonView, value)
        }
    // ----------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<BillingEstimationItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }


    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        BillingEstimationAdapter()
    }

    var hasLine = true
        set(value) {
            field = value
            lineView.visibility = if(value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_estimation_item_group, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BillingEstimationItemGroup)
        typedArray.run {
            title = getString(R.styleable.BillingEstimationItemGroup_title) ?: ""
            buttonLabel = getString(R.styleable.BillingEstimationItemGroup_buttonLabel) ?: ""
            price = getInt(R.styleable.BillingEstimationItemGroup_price, 0).toLong()
            priceString = getString(R.styleable.BillingEstimationItemGroup_priceString) ?: ""
        }
        typedArray.recycle()

        billingItemEstimationContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            adapter = recycleAdapter.also { it.items = items }
        }

        TouchFeedbackUtil.attach(buttonView, buttonPressed)
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is BillingEstimationItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                BillingEstimationItem.Data(
                    child.period,
                    child.dateTime,
                    child.dateTimeString,
                    child.pricePeriod,
                    child.pricePeriodString
                )
            )
            recycleAdapter.items = items
        }
    }

}

