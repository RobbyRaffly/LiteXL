package com.myxlultimate.component.organism.billingPaidItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_billing_paid_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class BillingPaidItem @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val dateTime: Long?=0,
        val dateTimeString: String?="",
        val price: Long?=0,
        val priceString: String?=""
    )

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var dateTime: Long = 0
        set(value) {
            field = value
            if (value > 0) {
                val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                dateTimeView.text = format.format(value)
            }
        }

    // ----------------------------------------------------------------------------------

    var dateTimeString = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                dateTimeView.text = value
            }
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

    var priceString = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                priceView.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var hasLine = true
        set(value) {
            field = value
            lineView.visibility = if(value) View.VISIBLE else View.GONE
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_paid_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BillingPaidItem)
        typedArray.run {
            title = getString(R.styleable.BillingPaidItem_title) ?: ""
            dateTime = getInt(R.styleable.BillingPaidItem_dateTime, 0).toLong()
            dateTimeString = getString(R.styleable.BillingPaidItem_dateTimeString) ?: ""
            price = getInt(R.styleable.BillingPaidItem_price, 0).toLong()
            priceString = getString(R.styleable.BillingPaidItem_priceString) ?: ""
        }
        typedArray.recycle()
    }
}