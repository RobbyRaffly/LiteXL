package com.myxlultimate.component.organism.transactionHistoryCard

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_transaction_history_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionHistoryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val name: String,
        val dateTime: Long,
        val transactionDate: TransactionDate,
        val dateTimeString: String,
        val priceString: String,
        val originalPriceString: String,
        val hasNextButton: Boolean,
        val isForHistory: Boolean,
        val imageSourceType: ImageSourceType,
        val imageSource: Any?,
        val rightBottomDescription: String? = "",
        val imageBottomSourceType: ImageSourceType? = ImageSourceType.BASE64,
        val imageBottomSource: Any? = "",
        var onCardPress: (() -> Unit)? = null
    )

    var name = ""
        set(value) {
            field = value

            nameView.text = value
            historyName.text = value
        }

    // ----------------------------------------------------------------------------------

    var dateTime: Long = 0
        set(value) {
            field = value

            val format = SimpleDateFormat("d MMMM yyyy kk:mm", Locale.getDefault())
            dateTimeView.text = format.format(value * 1000L)
        }

    // ----------------------------------------------------------------------------------

    var transactionDate = TransactionDate.DATETIME
        set(value) {
            field = value
            if (transactionDate == TransactionDate.DATETIME) {
                val format = SimpleDateFormat("d MMMM yyyy kk:mm", Locale.getDefault())
                dateTimeView.text = format.format(dateTime * 1000L)
            } else {
                val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                dateTimeView.text = format.format(dateTime * 1000L)
            }
        }

    // ----------------------------------------------------------------------------------

    var dateTimeString: String = ""
        set(value) {
            if(value.isNotEmpty()) {
                field = value
                dateTimeView.text = value
                historyDate.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value

            priceView.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value, true)
            )
            historyPrice.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value, true)
            )

        }

    // ----------------------------------------------------------------------------------

    var priceString: String = ""
        set(value) {
            field = value
            if(value.isNotEmpty()) {
                priceView.text = value
                historyPrice.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var originalPrice: Long = 0
        set(value) {
            if (value > 0 && field > 0) price = field
            field = value

            originalPriceView.apply {
                text = if(value < 1) "" else context.getString(R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
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

    var originalPriceString: String = ""
        set(value) {
            field = value
            if(value.isNotEmpty()) {
                originalPriceView.apply {
                    text = value
                    if (text.isNotEmpty()) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                        } else {
                            paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        }
                    }
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var hasNextButton = false
        set(value) {
            field = value

            nextButtonView.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

    var isForHistory = false
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            iconView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource : Any? = ""
        set(value) {
            field = value
            if (value == "") {
                val typedValue = TypedValue()
                context.theme?.resolveAttribute(R.attr.emptyPackagePngIcon, typedValue, true)
                iconView.imageSource = typedValue.resourceId
            } else {
                iconView.imageSource = value
            }
        }

    // ----------------------------------------------------------------------------------

    var rightBottomDescription = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, rightBottomText)
            rightBottomText.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageBottomSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
            iconRightBottom.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var imageBottomSource : Any? = ""
        set(value) {
            field = value
            iconRightBottom.apply {
                imageSource = value
                visibility = if (value == "") View.GONE else View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_history_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TransactionHistoryCardAttr)
        typedArray.run {

            name = getString(R.styleable.TransactionHistoryCardAttr_name) ?: ""
            price = getInt(R.styleable.TransactionHistoryCardAttr_price, 0).toLong()
            originalPrice = getInt(R.styleable.TransactionHistoryCardAttr_originalPrice, 0).toLong()
            hasNextButton = getBoolean(R.styleable.TransactionHistoryCardAttr_hasNextButton, false)
            dateTime = getInt(R.styleable.TransactionHistoryCardAttr_dateTime, 0).toLong()
            dateTimeString = getString(R.styleable.TransactionHistoryCardAttr_dateTimeString) ?: ""
            originalPriceString = getString(R.styleable.TransactionHistoryCardAttr_originalPriceString) ?: ""
            priceString = getString(R.styleable.TransactionHistoryCardAttr_priceString) ?: ""
            transactionDate = TransactionDate.values()[getInt(
                R.styleable.TransactionHistoryCardAttr_transactionDate,
                0
            )]
            isForHistory = getBoolean(R.styleable.TransactionHistoryCardAttr_isForHistory, false)
            imageSourceType = ImageSourceType.values()[typedArray.getInt(R.styleable.TransactionHistoryCardAttr_imageSourceType, 2)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.TransactionHistoryCardAttr_imageSource)
            } else {
                typedArray.getString(R.styleable.TransactionHistoryCardAttr_imageSource)
            }
        }

        if (hasNextButton) TouchFeedbackUtil.attach(containerView, onCardPress)
    }



    private fun refreshView() {
        if (isForHistory){
            iconContainer.visibility = View.VISIBLE
            smallText.visibility = View.VISIBLE
            historyPrice.visibility = View.VISIBLE

            hugeText.visibility = View.GONE
            priceView.visibility = View.GONE
        }
    }
}