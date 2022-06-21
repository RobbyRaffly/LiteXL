package com.myxlultimate.component.organism.statusPaymentCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_status_payment_card.view.*
import kotlinx.android.synthetic.main.organism_status_payment_card.view.iconView

class StatusPaymentCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?= null
) : LinearLayout(context, attributeSet) {
    data class Data(
        val name : String,
        val price : Long,
        val priceString: String,
        val iconImage: String,
        val iconStatus : String,
        val titleStatus : String,
        val hasRefresh :  Boolean
    )

    var name = ""
        set(value) {
            field = value
            titlePayment.text = value
        }

    // ----------------------------------------------------------------------------------
    
    var hasRefresh = true
        set(value) {
            field = value
            if (value) {
                refreshView.visibility = View.VISIBLE
            } else {
                refreshView.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------

    var hasTopView = true
        set(value) {
            field = value
            if (value) {
                titlePayment.visibility = View.VISIBLE
                priceTotalView.visibility = View.VISIBLE
                line.visibility = View.VISIBLE
            } else {
                titlePayment.visibility = View.GONE
                priceTotalView.visibility = View.GONE
                line.visibility = View.GONE
            }
        }
    
    // ----------------------------------------------------------------------------------
    
    var price: Long = 0
        set(value) {
            field = value

            priceTotalView.text =
                context.getString(R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
        }

    // ----------------------------------------------------------------------------------

    var priceString: String = ""
        set(value) {
            field = value
            if(value.isNotEmpty()) {
                priceTotalView.text = value
            }
        }

    // ----------------------------------------------------------------------------------
    
    var imageSourceType = ImageSourceType.ASSET
        set(value) {
            field = value
            iconView.imageSourceType = value
        }

    var iconImage: Any? = null
        set(value) {
            field = value
            iconView.imageSource = value
        }

    var titleStatus = ""
        set(value) {
            field = value
            ctaBottomView.text = value
        }
    // ----------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------
    var onRefreshPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(false, refreshView, value)
        }

    // ----------------------------------------------------------------------------------
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_status_payment_card, this, true)

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.StatusPaymentCardAttr)
        typedArray.run {
            name = getString(R.styleable.StatusPaymentCardAttr_name)?: resources.getString(R.string.organism_status_payment_card_label_total)
            price = getInt(R.styleable.StatusPaymentCardAttr_price, 0).toLong()
            priceString = getString(R.styleable.StatusPaymentCardAttr_priceString) ?: ""
            titleStatus = getString(R.styleable.StatusPaymentCardAttr_titleStatus) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.StatusPaymentCardAttr_imageSourceType, 0)]
            hasRefresh = getBoolean(R.styleable.StatusPaymentCardAttr_hasRefresh, true)
            hasTopView = getBoolean(R.styleable.StatusPaymentCardAttr_hasTopView, true)
            if (imageSourceType == ImageSourceType.DRAWABLE) {
                iconImage = getDrawable(R.styleable.StatusPaymentCardAttr_imageSource)
            } else {
                iconImage = getString(R.styleable.StatusPaymentCardAttr_imageSource)
            }
        }

        TouchFeedbackUtil.attach(false, refreshView, onRefreshPress)
    }
}