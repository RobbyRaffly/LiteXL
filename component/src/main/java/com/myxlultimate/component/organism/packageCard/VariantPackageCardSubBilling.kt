package com.myxlultimate.component.organism.packageCard

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.billingItem.BillingItemType
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_package_card_arrears_variant_sub_billing.view.*

class VariantPackageCardSubBilling @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultStartColor = "#c40d42"
    private val defaultEndColor = "#18448A"

    // ----------------------------------------------------------------------------------

    var showLoyaltyBottomView = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, loyaltyBottomLayoutView)
        }
    // ----------------------------------------------------------------------------------

    var startHexColor = defaultStartColor
        set(value) {
            field = value
            setUpColor()
        }

    // ----------------------------------------------------------------------------------

    var endHexColor = defaultEndColor
        set(value) {
            field = value
            setUpColor()
        }

    // ----------------------------------------------------------------------------------

    var textBtnReadColor = "#000000"
        set(value) {
            field = value
            setUpColor()
        }
    // ----------------------------------------------------------------------------------


    var firstTitle = ""
        set(value) {
            field = value
            titleFirst.text = value
        }

    var secondTitle: String = ""
        set(value) {
            field = value
            titleSecond.text = value
        }

    var textPeriod = ""
        set(value) {
            field = value
            textItemFirst.text = value
        }

    var textDue = ""
        set(value) {
            field = value
            textItemSecond.text = value
        }

    var textStatus = ""
        set(value) {
            field = value
            titleThird.text = value
        }
    var titleStatus = BillingItemType.CLOSED
        set(value) {
            field = value
            refreshView()
        }

    var onBtnHowToReadPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(btnHowToRead, value)
        }

    var onBtnDownloadPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(btnDownloadBill, value)
        }

    var sumPartially = ""
        set(value) {
            field = value
            refreshView()
        }

    var btnHowToReadTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    var btnDownloadTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_package_card_arrears_variant_sub_billing, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VariantPackageCardAttr)
        typedArray.run {
            showLoyaltyBottomView =
                typedArray.getBoolean(R.styleable.VariantPackageCardAttr_showLoyaltyBottom, false)

            firstTitle =
                typedArray.getString(R.styleable.VariantPackageCardAttr_loyaltyBottomTitle) ?: ""
            secondTitle =
                typedArray.getString(R.styleable.VariantPackageCardAttr_loyaltyBottomTitle) ?: ""
            startHexColor =
                typedArray.getString(R.styleable.VariantPackageCardAttr_startColor) ?: startHexColor
            endHexColor =
                typedArray.getString(R.styleable.VariantPackageCardAttr_endColor) ?: endHexColor
        }
        typedArray.recycle()
    }


    private fun setUpColor() {
        DrawableUtil.CreateGradientBackground(context, startHexColor, endHexColor, 16f)?.let {
            loyaltyBottomLayoutView.background = it
        }


        btnHowToRead.setTextColor(Color.parseColor(textBtnReadColor))
    }

    private fun refreshView() {
        btnDownloadBill.setText(btnDownloadTitle)
        btnHowToRead.setText(btnHowToReadTitle)
        when (titleStatus) {
            BillingItemType.CLOSED -> {
                titleStatusTxt.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.mud_palette_basic_green
                    )
                )
                titleStatusTxt.text =
                    resources.getString(R.string.organism_billing_summary_card_status_paid)

            }

            BillingItemType.OPEN -> {
                titleStatusTxt.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.mud_palette_basic_red
                    )
                )
                titleStatusTxt.text =
                    resources.getString(R.string.organism_billing_summary_card_status_unpaid)
            }

            BillingItemType.ARREARS -> {
                titleStatusTxt.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.mud_palette_basic_red
                    )
                )
                titleStatusTxt.text =
                    resources.getString(R.string.organism_billing_summary_card_status_arrears)

            }
            BillingItemType.PARTIALLY_PAID -> {
                titleStatusTxt.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.mud_palette_basic_red
                    )
                )
                titleStatusTxt.text =
                    resources.getString(
                        R.string.organism_billing_item_partially_paid_detail,
                        sumPartially
                    )
            }
        }
    }
}