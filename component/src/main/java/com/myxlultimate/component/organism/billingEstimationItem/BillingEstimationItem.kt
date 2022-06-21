package com.myxlultimate.component.organism.billingEstimationItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_billing_estimation_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class BillingEstimationItem @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val period: String? = "",
        val dateTime: Long?=0,
        val dateTimeString: String?="",
        val pricePeriod: Long?=0,
        val pricePeriodString: String?=""
    )

    var period: String = ""
        set(value) {
            field = value
            periodView.text = value
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

    var dateTimeString: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                dateTimeView.text = value
                dateTimeView.visibility = View.VISIBLE
            } else {
                dateTimeView.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------

    var pricePeriod: Long = 0
        set(value) {
            field = value
            pricePeriodView.text =
                context.getString(R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
        }

    // ----------------------------------------------------------------------------------

    var pricePeriodString: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                pricePeriodView.text = value
                pricePeriodView.visibility = View.VISIBLE
            } else {
                pricePeriodView.visibility = View.GONE
            }
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_estimation_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BillingEstimationItem)
        typedArray.run {
            period = getString(R.styleable.BillingEstimationItem_description) ?: ""
            dateTime = getInt(R.styleable.BillingEstimationItem_dateTime, 0).toLong()
            dateTimeString = getString(R.styleable.BillingEstimationItem_dateTimeString) ?: ""
            pricePeriod = getInt(R.styleable.BillingEstimationItem_price, 0).toLong()
            pricePeriodString = getString(R.styleable.BillingEstimationItem_priceString) ?: ""
        }
        typedArray.recycle()
    }
}