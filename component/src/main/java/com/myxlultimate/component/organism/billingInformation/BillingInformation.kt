package com.myxlultimate.component.organism.billingInformation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_billing_information.view.*
import java.text.SimpleDateFormat
import java.util.*

class BillingInformation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            tvBillLabel.text = value
        }

    // ----------------------------------------------------------------------------------

    var dueDate: Long = 0
        set(value) {
            field = value
            if (value > 0) {
                val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                tvDueDate.apply {
                    text = resources.getString(
                        R.string.organism_billing_information_due_date,
                        format.format(value)
                    )
                    visibility = View.VISIBLE
                }
            } else {
                tvDueDate.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value
            if (value > 0L) {
                ivArrowRight.visibility = View.VISIBLE
            } else {
                ivArrowRight.visibility = View.GONE
            }
            tvBillValue.text =
                context.getString(R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
        }

    // ----------------------------------------------------------------------------------

    var priceString = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                tvBillValue.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var onItemPressed: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(layBillAmount, value)
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_information, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BillingInformation)
        typedArray.run {
            title = getString(R.styleable.BillingInformation_title) ?: ""
            dueDate = getInt(R.styleable.BillingInformation_dueDate, 0).toLong()
            price = getInt(R.styleable.BillingInformation_price, 0).toLong()
            priceString = getString(R.styleable.BillingInformation_priceString) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(layBillAmount, onItemPressed)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}