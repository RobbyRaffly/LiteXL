package com.myxlultimate.component.organism.billingItem

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_billing_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class BillingItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val dueDate: Long? = 0L,
        val dueDateString: String? = "",
        val monthOfYear: String? = "",
        val billPrice: Long? = 0L,
        val billPriceString: String? = "",
        val billStatus: BillingItemType? = BillingItemType.OPEN,
        val debtPrice: Long? = 0L,
        val debtPriceString: String? = "",
        val startDate: String? = "",
        val endDate: String? = "",
        val showDownload: Boolean? = true
    )

    // ----------------------------------------------------------------------------------

    var dueDate = 0L
        set(value) {
            field = value
            if (value > 0L) {
                tvDueDate.visibility = View.VISIBLE
                val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                tvDueDate.text =
                    resources.getString(
                        R.string.organism_billing_item_due_date,
                        format.format(value)
                    )
            }
        }

    // ----------------------------------------------------------------------------------

    var dueDateString = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                tvDueDate.visibility = View.VISIBLE
                tvDueDate.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var startDate = ""
        set(value) {
            field = value
        }

    // ----------------------------------------------------------------------------------

    var endDate = ""
        set(value) {
            field = value
        }

    // ----------------------------------------------------------------------------------

    var monthOfYear = ""
        set(value) {
            field = value
            tvMonthOfYear.text = value
        }

    // ----------------------------------------------------------------------------------

    var billPrice = 0L
        set(value) {
            field = value
            tvBillingPrice.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value, true)
            )

            ivDownload.isEnabled = value >0
        }

    // ----------------------------------------------------------------------------------

    var billPriceString = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                tvBillingPrice.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var billStatus = BillingItemType.OPEN
        set(value) {
            field = value
            when (value) {
                BillingItemType.CLOSED -> {
                    with(tvBillStatus){
                        text = resources.getString(R.string.organism_billing_item_paid)
                        setTextColor(ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_green
                        ))
                    }
                    tvBillingPrice.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
                    tvMonthOfYear.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
                    tvDueDate.visibility = View.GONE
                    debtPrice = 0L
                }
                BillingItemType.OPEN -> {
                    with(tvBillStatus){
                        text = resources.getString(R.string.organism_billing_item_unpaid)
                        setTextColor(ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_red
                        ))
                    }
                    tvBillingPrice.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_red))
                    tvMonthOfYear.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
                    tvDueDate.visibility = View.VISIBLE
                }
                BillingItemType.ARREARS -> {
                    with(tvBillStatus){
                        text = resources.getString(R.string.organism_billing_item_debt)
                        setTextColor(ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_red
                        ))
                    }
                    tvBillingPrice.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
                    tvMonthOfYear.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
                    tvDueDate.visibility = View.VISIBLE
                }

                BillingItemType.PARTIALLY_PAID -> {
                    with(tvBillStatus){
                        text = resources.getString(R.string.organism_billing_item_partially_paid)
                        setTextColor(ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_red
                        ))
                    }
                    tvBillingPrice.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
                    tvMonthOfYear.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
                    tvDueDate.visibility = View.VISIBLE
                }
            }

        }

    // ----------------------------------------------------------------------------------

    var debtPrice = 0L
        set(value) {
            field = value
            if (value > 0L && (billStatus == BillingItemType.ARREARS || billStatus == BillingItemType.PARTIALLY_PAID)) {
                tvDebtPrice.visibility = View.VISIBLE
                tvDebtPrice.text =
                    (resources.getString(
                        R.string.organism_billing_item_paid_off,
                        ConverterUtil.convertDelimitedNumber(
                            value,
                            true
                        )
                    ))
            } else {
                tvDebtPrice.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------

    var debtPriceString = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                tvDebtPrice.visibility = View.VISIBLE
                tvDebtPrice.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var hasLine = true
        set(value) {
            field = value
            vLine.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var showDownload = true
        set(value) {
            field = value
            ivDownload.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var onDetailPressed: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(cardItemPendingLoyalty, value)
        }

    // ----------------------------------------------------------------------------------

    var onDownloadPressed: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(ivDownload, value)
        }

    // ----------------------------------------------------------------------------------

    var minimalView = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var guideTitle = ""
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------

    var subTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var downloadUrl = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BillingItem)
        typedArray.run {
            dueDate = getInt(R.styleable.BillingItem_dueDate, 0).toLong()
            dueDateString = getString(R.styleable.BillingItem_dueDateString) ?: ""
            monthOfYear = getString(R.styleable.BillingItem_monthOfYear) ?: ""
            billPrice = getInt(R.styleable.BillingItem_billPrice, 0).toLong()
            billPriceString = getString(R.styleable.BillingItem_billPriceString) ?: ""
            billStatus = BillingItemType.values()[getInt(R.styleable.BillingItem_billStatus, 1)]
            debtPrice = getInt(R.styleable.BillingItem_debtPrice, 0).toLong()
            debtPriceString = getString(R.styleable.BillingItem_debtPriceString) ?: ""
            hasLine = getBoolean(R.styleable.BillingItem_hasLine, true)
            minimalView = getBoolean(R.styleable.BillingItem_minimalView, false)
            subTitle = getString(R.styleable.BillingItem_subtitle) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(cardItemPendingLoyalty, onDetailPressed)
        TouchFeedbackUtil.attach(ivDownload, onDownloadPressed)

    }

    // ----------------------------------------------------------------------------------

    fun refreshView(){
        if (minimalView){
            ivArrowRight.visibility = View.GONE
            tvBillStatus.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
            tvMonthOfYear.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
            tvBillingPrice.visibility = View.INVISIBLE
            tvBillStatus.text = resources.getString(R.string.modem_guidance)
            if (subTitle.isNotEmpty()) tvBillStatus.text = subTitle
            if (guideTitle.isNotEmpty()) tvMonthOfYear.text = guideTitle
        }
    }

    // ----------------------------------------------------------------------------------
}