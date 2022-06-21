package com.myxlultimate.component.organism.transactionTotalPriceFooter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_transaction_total_price_footer.view.*


class TransactionTotalPriceFooter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var price: Long = 0
        set(value) {
            field = value

            priceView.text = context.getString(
                R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value, true)
            )
        }

    // ----------------------------------------------------------------------------------

    var point = 0
        set(value) {
            field = value

            if (point > 0) {
                priceView.text = (ConverterUtil.convertDelimitedNumber(
                    value.toLong(),
                    true
                ) + " " + resources.getString(
                    R.string.organism_redeemable_card_point
                ))
                titleFooterView.text =
                    (resources.getString(R.string.organism_top_up_selected_nominal_footer_title_point))
            }
        }

    // ----------------------------------------------------------------------------------

    var textTotalAmount = ""
        set(value) {
            field = value
            titleFooterView.text = value
        }
    // ----------------------------------------------------------------------------------

    var isArrowShow = true
        set(value) {
            field = value
            if (value) {
                detailButtonView.visibility = View.VISIBLE
            } else{
                detailButtonView.visibility = View.GONE
            }
        }
    // ----------------------------------------------------------------------------------

    var buttonText = ""
        set(value) {
            field = value

            buyButtonView.text = value
        }

    // ----------------------------------------------------------------------------------

    var isFooterEnabled = true
        set(value) {
            field = value
            buyButtonView.isEnabled = value
            if (!isFooterEnabled) {
                priceView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
                titleFooterView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
            } else {
                val attrs = intArrayOf(R.attr.colorBackgroundPrimary)
                val ta = context.obtainStyledAttributes(attrs)
                val color = ta.getResourceId(0, R.attr.colorBackgroundPrimary)
                ta.recycle()
                priceView.setTextColor(getColor(context, color))
            }
        }

    // ----------------------------------------------------------------------------------

    var footerMode = FooterMode.NORMAL
        set(value) {
            field = value
            when (footerMode) {
                FooterMode.NORMAL -> {
                    detailButtonView.visibility = View.VISIBLE
                    topDetailButtonView.visibility = View.GONE
                }
                FooterMode.TOP_CHEVRON -> {
                    detailButtonView.visibility = View.GONE
                    topDetailButtonView.visibility = View.VISIBLE
                }
            }
        }

    // ----------------------------------------------------------------------------------


    var hasDetailButton: Boolean = false
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, detailButtonView)
        }

    var isShimmerOn: Boolean = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.apply {
                    visibility = View.VISIBLE
                    startShimmer()
                }
                originalView.visibility = View.GONE
            } else {
                shimmerLayout.apply {
                    visibility = View.GONE
                    stopShimmer()
                }
                originalView.visibility = View.VISIBLE

            }
        }

    // ----------------------------------------------------------------------------------

    var hasRedDot = false
        set(value) {
            field = value
            redDotCountLayout.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var redDotCount: Int = 0
        set(value) {
            field = value
            if (value > 0) {
                redDotCountLayout.visibility = View.VISIBLE
                tvCount.text = value.toString()
            }
        }

    // ----------------------------------------------------------------------------------

    var onBuyButtonPress: (() -> Unit)? = null

    var onDetailButtonPress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_total_price_footer, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TransactionTotalPriceFooterAttr)
        typedArray.run {
            price = getInt(R.styleable.TransactionTotalPriceFooterAttr_price, 0).toLong()
            textTotalAmount =
                getString(R.styleable.TransactionTotalPriceFooterAttr_topTitle) ?: ""
            hasDetailButton =
                getBoolean(R.styleable.TransactionTotalPriceFooterAttr_hasDetailButton, false)
            buttonText = getString(R.styleable.TransactionTotalPriceFooterAttr_buttonText) ?: ""
            isFooterEnabled =
                getBoolean(R.styleable.TransactionTotalPriceFooterAttr_isFooterEnabled, true)
            point = getInt(R.styleable.TransactionTotalPriceFooterAttr_point, 0)
            isShimmerOn = getBoolean(R.styleable.TransactionTotalPriceFooterAttr_isShimmerOn, false)
            hasRedDot = getBoolean(R.styleable.TransactionTotalPriceFooterAttr_hasRedDot, false)
            isArrowShow = getBoolean(R.styleable.TransactionTotalPriceFooterAttr_hasRedDot, false)
            redDotCount = getInt(R.styleable.TransactionTotalPriceFooterAttr_redDotCount, 0)
            footerMode = FooterMode.values()[getInt(
                R.styleable.TransactionTotalPriceFooterAttr_footerMode,
                0
            )]
        }
        typedArray.recycle()

        buyButtonView.setOnClickListener {
            onBuyButtonPress?.let { it() }
        }

        detailButtonView.setOnClickListener {
            onDetailButtonPress?.let { it() }
        }

        topDetailButtonView.setOnClickListener {
            onDetailButtonPress?.let { it() }
        }
    }
}