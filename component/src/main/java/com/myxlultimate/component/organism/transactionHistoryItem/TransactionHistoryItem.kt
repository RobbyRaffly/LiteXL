package com.myxlultimate.component.organism.transactionHistoryItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.transactionHistoryCard.TransactionDate
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_transaction_history_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionHistoryItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val transactionHistoryType: TransactionHistoryType? = TransactionHistoryType.TEXT,
        val name: String,
        val dateTime: Long?=0,
        val dateTimeString: String? = "",
        val price: Long?=0,
        val priceString: String? = "",
        val detail: String? = ""
    )

    var transactionHistoryType = TransactionHistoryType.TEXT
        set(value) {
            field = value
            iconView.imageSourceType = ImageSourceType.DRAWABLE
            when (value) {
                TransactionHistoryType.DATA -> {
                    iconView.imageSource  = getDrawable(context, R.drawable.ic_icons_data_trans)
                }
                TransactionHistoryType.VOICE -> {
                    iconView.imageSource  = getDrawable(context, R.drawable.ic_icons_call_trans)
                }
                TransactionHistoryType.BILL -> {
                    iconView.imageSource  = getDrawable(context, R.drawable.ic_icon_bill_trans)
                }
                TransactionHistoryType.BOOSTER -> {
                    iconView.imageSource  = getDrawable(context, R.drawable.ic_icons_booster_trans)
                }
                TransactionHistoryType.TEXT -> {
                    iconView.imageSource  = getDrawable(context, R.drawable.ic_icons_text_trans)
                }
            }
        }

    var name = ""
        set(value) {
            field = value

            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var dateTime: Long = 0
        set(value) {
            field = value

            if(value > 0){
                val formatter = SimpleDateFormat("d MMMM yyyy, h.mm a", Locale.getDefault())
                dateTimeView.text = formatter.format(value)
            }
        }

    // ----------------------------------------------------------------------------------

    var transactionDate = TransactionDate.DATETIME
        set(value) {
            field = value
            if (transactionDate == TransactionDate.DATETIME) {
                val formatter = SimpleDateFormat("d MMMM yyyy, h.mm a", Locale.getDefault())
                dateTimeView.text = formatter.format(dateTime)
            } else {
                val formatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                dateTimeView.text = formatter.format(dateTime * 1000L)
            }
        }

    // ----------------------------------------------------------------------------------

    var dateTimeString: String = ""
        set(value) {
            if (value.isNotEmpty()) {
                field = value
                dateTimeView.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value

            if(value>=1){
                priceView.text =
                    context.getString(R.string.indonesian_rupiah_balance_remaining,
                        ConverterUtil.convertDelimitedNumber(value, true)
                    )
            }
        }

    // ----------------------------------------------------------------------------------

    var detail = ""
        set(value) {
            field = value
            if (detail.isNotEmpty()) {
                priceView.text =
                    ("$priceString - $value")
            }
        }

    // ----------------------------------------------------------------------------------


    var priceString: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                priceView.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: (() -> Unit)? = null
        set(value) {
            field = value
//            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------


    var hasLine = true
        set(value) {
            field = value
            lineView.visibility = if (!value) View.GONE else View.VISIBLE
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_history_item, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TransactionHistoryItemAttr)
        typedArray.run {
            transactionHistoryType = TransactionHistoryType.values()[getInt(R.styleable.TransactionHistoryItemAttr_transactionHistoryType,1)]
            name = getString(R.styleable.TransactionHistoryItemAttr_name) ?: ""
            price = getInt(R.styleable.TransactionHistoryItemAttr_price, 0).toLong()
            dateTime = getInt(R.styleable.TransactionHistoryItemAttr_dateTime, 0).toLong()
            dateTimeString = getString(R.styleable.TransactionHistoryItemAttr_dateTimeString) ?: ""
            priceString = getString(R.styleable.TransactionHistoryItemAttr_priceString) ?: ""
            transactionDate = TransactionDate.values()[getInt(
                R.styleable.TransactionHistoryItemAttr_transactionDate,
                0
            )]
            hasLine = getBoolean(R.styleable.TransactionHistoryItemAttr_hasLine, true)
        }
        typedArray.recycle()
    }
}