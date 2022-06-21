package com.myxlultimate.component.organism.itemPaymentCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_item_payment_card_list_item.view.*

class ItemPaymentCardItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val datePayment: String,
        val typePayment: String,
        val pricePayment: String,
        val isIconShow: Boolean = false,
        var onIconPress: (() -> Unit)? = null
    )

    var titleDate = ""
        set(value) {
            field = value
            textViewDate.text = value
        }

    var titleViewTypePayment = ""
        set(value) {
            field = value
            textViewTypePayment.text = value
        }

    var titlePrice = ""
        set(value) {
            field = value
            textViewPrice.text = value
        }


    var onIconPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(payment_container, value)
        }

    var isShowIcon: Boolean = false
        set(value) {
            field = value
            if (field) {
                imgInfo.visibility = View.VISIBLE
            } else {
                imgInfo.visibility = View.GONE
            }
        }

    var hasLine = true
        set(value) {
            field = value
            lineView.visibility = if (!value) View.GONE else View.VISIBLE
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_item_payment_card_list_item, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TransactionHistoryItemAttr)
        typedArray.run {
            titleDate = getString(R.styleable.TransactionHistoryItemAttr_name) ?: ""
            titleViewTypePayment = getString(R.styleable.TransactionHistoryItemAttr_name) ?: ""
            titlePrice = getString(R.styleable.TransactionHistoryItemAttr_name) ?: ""
            hasLine = getBoolean(R.styleable.TransactionHistoryItemAttr_hasLine, true)
            isShowIcon = getBoolean(R.styleable.TransactionHistoryItemAttr_hasLine, true)
        }

        typedArray.recycle()

    }
}