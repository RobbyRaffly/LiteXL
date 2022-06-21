package com.myxlultimate.component.organism.transactionCard

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_transaction_selected_item_card.view.*
import kotlinx.android.synthetic.main.organism_transaction_selected_item_card.view.iconView
import kotlinx.android.synthetic.main.organism_transaction_selected_item_card.view.nameView
import kotlinx.android.synthetic.main.organism_transaction_selected_item_card.view.originalPriceView
import kotlinx.android.synthetic.main.organism_transaction_selected_item_card.view.priceView
import kotlinx.android.synthetic.main.organism_transaction_selected_item_card.view.validityView

class TransactionSelectedItemCard @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isShimmerOn = false
    set(value) {
        field = value
        refreshView()
    }

    var name = ""
        set(value) {
            field = value

            nameView.text = value
            IsEmptyUtil.setVisibility(value, nameView)
        }

    // ----------------------------------------------------------------------------------

    var image =
        "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"
        set(value) {
            field = value
            iconView.imageSource = value
            if (value == "") {
                iconView.visibility = View.GONE
            } else {
                iconView.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    var hasRemovedIcon = false
        set(value) {
            field = value
            removedIconView.visibility = if (hasRemovedIcon) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var onRemovedIconPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(mainView, value)
        }

    // ----------------------------------------------------------------------------------

    var isGoneWhenZeroPrice = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var price = 0
        set(value) {
            field = value
            // TODO No need to hide when price is Rp 0
//            IsEmptyUtil.setVisibility(value,priceView)
            priceView.text =
                context.getString(R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value.toLong(), true)
                )
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var originalPrice = 0
        set(value) {
            field = value
            originalPriceView.apply {
                IsEmptyUtil.setVisibility(value, this)
                text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value.toLong(), true)
                )
                if (text.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                    } else {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            }
        }
    // ----------------------------------------------------------------------------------

    var validity = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var customValidity = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_selected_item_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.transactionSelectedItemCardAttr)
        typedArray.run {
            name = getString(R.styleable.transactionSelectedItemCardAttr_name) ?: ""
            image = getString(R.styleable.transactionSelectedItemCardAttr_iconImage) ?: ""
            price = getInt(R.styleable.transactionSelectedItemCardAttr_price, 0)
            originalPrice = getInt(R.styleable.transactionSelectedItemCardAttr_originalPrice, 0)
            validity = getString(R.styleable.transactionSelectedItemCardAttr_validity) ?: ""
            hasRemovedIcon =
                getBoolean(R.styleable.transactionSelectedItemCardAttr_hasRemovedIcon, false)
            customValidity =
                getString(R.styleable.transactionSelectedItemCardAttr_customValidity) ?: ""
            isGoneWhenZeroPrice =
                getBoolean(R.styleable.transactionSelectedItemCardAttr_isGoneWhenZeroPrice, false)
            isShimmerOn = getBoolean(R.styleable.transactionSelectedItemCardAttr_isShimmerOn,false)
        }
        typedArray.recycle()
    }

    private fun refreshView() {
        validityView.visibility =
            if (customValidity.isNotEmpty() || validity.isNotEmpty()) View.VISIBLE else View.GONE
        if (customValidity.isNotEmpty()) {
            validityView.text = customValidity
        } else {
            validityView.text = resources.getString(
                R.string.organism_transaction_selected_item_card_validity,
                validity
            )
        }

        priceView.visibility = if (price == 0 && isGoneWhenZeroPrice) View.GONE else View.VISIBLE

        if (isShimmerOn) {
            shimmerLayout.apply {
                visibility = View.VISIBLE
                startShimmer()
            }
        } else {
            shimmerLayout.apply {
                visibility = View.GONE
                stopShimmer()
            }
        }
        mainView.visibility = if(isShimmerOn) View.GONE else View.VISIBLE
    }
}