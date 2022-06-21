package com.myxlultimate.component.organism.billingSummaryCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.billingItem.BillingItemType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_billing_summary_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class BillingSummaryCard @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var periodInformation = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var priceString = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var status = BillingItemType.CLOSED
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var date: Long = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var dateString = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var subscriptionType = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasBottomInformation = false
        set(value) {
            field = value
            refreshView()
        }

    var showLoyaltyBottomView = false
        set(value) {
            field = value
//            IsEmptyUtil.setVisibility(value)
        }

    var loyaltyBottomTitle = ""
    set(value) {
        field = value

    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {

        periodInformationView.text =
            resources.getString(R.string.organism_billing_detail_period, periodInformation)

        priceView.text =
            context.getString(
                R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(price, true)
            )
        if (priceString.isNotEmpty()) {
            priceView.text = priceString
        }

        when (status) {
            BillingItemType.CLOSED -> {
                statusView.setTextColor(getColor(context, R.color.mud_palette_basic_green))
                statusView.text =
                    resources.getString(R.string.organism_billing_summary_card_status_paid)
                dividerViewHorizontal.visibility = View.GONE
                layoutDueDate.visibility = View.GONE
            }
            BillingItemType.OPEN -> {
                statusView.setTextColor(getColor(context, R.color.mud_palette_basic_red))
                statusView.text =
                    resources.getString(R.string.organism_billing_summary_card_status_unpaid)
                dividerViewHorizontal.visibility = View.VISIBLE
                layoutDueDate.visibility = View.VISIBLE
            }
            BillingItemType.ARREARS -> {
                statusView.setTextColor(getColor(context, R.color.mud_palette_basic_red))
                statusView.text =
                    resources.getString(R.string.organism_billing_summary_card_status_arrears)
                dividerViewHorizontal.visibility = View.VISIBLE
                layoutDueDate.visibility = View.VISIBLE
            }
            BillingItemType.PARTIALLY_PAID -> {
                statusView.setTextColor(getColor(context, R.color.mud_palette_basic_red))
                statusView.text = resources.getString(R.string.organism_billing_item_partially_paid)
                dividerViewHorizontal.visibility = View.VISIBLE
                layoutDueDate.visibility = View.VISIBLE
            }
        }

        val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        dateView.text = format.format(date * 1000L)

        if (dateString.isNotEmpty()) {
            dateView.text = dateString
        }

//        subscriptionTypeView.text = subscriptionType

//        informationView.text =
            resources.getString(R.string.organism_billing_detail_used_payment, information)

//        bottomView.visibility = if (hasBottomInformation) View.VISIBLE else View.GONE
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_summary_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BillingSummaryCard)
        typedArray.run {
            periodInformation = getString(R.styleable.BillingSummaryCard_periodInformation) ?: ""
            price = (getInt(R.styleable.BillingSummaryCard_price, 0)).toLong()
            priceString = getString(R.styleable.BillingSummaryCard_priceString) ?: ""
            status =
                BillingItemType.values()[getInt(R.styleable.BillingSummaryCard_status, 0)]
            date = (getInt(R.styleable.BillingSummaryCard_date, 0)).toLong()
            dateString = getString(R.styleable.BillingSummaryCard_dateTimeString) ?: ""
            subscriptionType = getString(R.styleable.BillingSummaryCard_subscriptionType) ?: ""
            information = getString(R.styleable.BillingSummaryCard_information) ?: ""
            hasBottomInformation =
                getBoolean(R.styleable.BillingSummaryCard_hasBottomInformation, false)
        }
        typedArray.recycle()
    }
}