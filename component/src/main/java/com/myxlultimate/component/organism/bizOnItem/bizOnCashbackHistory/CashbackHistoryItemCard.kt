package com.myxlultimate.component.organism.bizOnItem.bizOnCashbackHistory

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_cashback_history_item.view.*
import kotlinx.android.synthetic.main.organism_cashback_total_header_card.view.*

class CashbackHistoryItemCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val label: String,
        val amount: String,
        val imageSource: Any?,
        val imageSourceType: ImageSourceType,
        val information: String,
        val showButtonTitle: Boolean,
        val buttonTitle: String,
        val onItemClick: (() -> Unit)? = null
    )

    val imageDefault = "iVBORw0KGgoAAAANSUhEUgAAAEsAAABICAMAAACJBPh2AAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAABRUExURUdwTEzH5HfZ7FfL5XLU6y++3zjB3xi222jR6CW73WvS6FzM5nra8GzR6EXF4gCu1v///wex2D7C4eD2+7zq9V3M5vL8/nrW66jk8s/x+JPd7sp4AV0AAAAPdFJOUwDQMLdS8ev9gvp2rBhu26Vk6qsAAAIFSURBVFjD5VjdeoMgDK1tEekfRFDB93/QtVvrVkyQ8LGb7VxaeyThJJyw2/17XE7nplVSAkip2uZ8uhQSXRuhVxDNlU10bKQmIJsjh6kTOgnR5TKdlN6EOmVF1+ostNuRnkFnAs5ppn2rGWj3qfikZkHScXagmYCuGhVJVkJFkB2LqO5k65ztpS6EXO2m0MUQEdVh/bl+mGbvXLA2BOf8NPSKSMMhmSw5WYNgnOV2yqIIB0Nh7Lei7N5/8sYwyX4I473JTCaFgLYgYlm9SSO9sDY7wgcGtGW8ZPq+KePzPxOhAPzxHtPWEiKvEA6YIBY9uJ4AKtkvWVyAsYvPz6xzBhdEXLPJgZPoTkal6LK4jJVYwm7vz0Iel3ER1w2pRZvJFetMrAson8sjZRQFPuZy2ahNPbgiteRSmTESBZ/LK+VpLlaM9v5lsGSMrNzb71csknueJoalYi2iCZ5Wp6ViA6JVXg0FgIAK/1BQ2+Nrc2aktgt6zivaVc+heqFhnSEi2aO3oLAeTZwdW3kD7OyIzrS5pE20+Fkrx4L21REeYOAvS5HeZGK3+472TP1Wc/VAO8PYy8Hg6KyNfkja37XHhP5pMl2428xPn+n8fHeaPaQ9Ju59Ic+aiN/05DVnhaozTNXZqurMV3UWrTojV53d694pVL3rqHsHU/duqO6dVeW7tL+CD+Dv4Y9OIAeyAAAAAElFTkSuQmCC"

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            tvDateCashback.text = value
        }

    // ----------------------------------------------------------------------------------

    var amount = ""
        set(value) {
            field = value
            tvAmount.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            ivIconPayment.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource : Any? = imageDefault
        set(value) {
            field = value
            ivIconPayment.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            tvActivationNumber.text = value
        }

    // ----------------------------------------------------------------------------------

    var showButtonTitle = false
        set(value) {
            field = value

            if (value) {
                ivIconPayment.visibility = View.GONE
                tvButtonTitle.visibility = View.VISIBLE
            } else {
                ivIconPayment.visibility = View.VISIBLE
                tvButtonTitle.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------

    var buttonTitle = ""
        set(value) {
            field = value
            tvButtonTitle.text = value
        }

    // ----------------------------------------------------------------------------------

    var onItemClick: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(tvButtonTitle, value)
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_cashback_history_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CashbackHistoryItemCard)
        typedArray.run {
            label = getString(R.styleable.CashbackHistoryItemCard_label) ?: ""
            amount = getString(R.styleable.CashbackHistoryItemCard_amount) ?: ""
            imageSourceType = ImageSourceType.values()[typedArray.getInt(R.styleable.CashbackHistoryItemCard_imageSourceType, 3)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.CashbackHistoryItemCard_imageSource)
            } else {
                typedArray.getString(R.styleable.CashbackHistoryItemCard_imageSource)
            }
            information = getString(R.styleable.CashbackHistoryItemCard_information) ?: ""
            showButtonTitle = getBoolean(R.styleable.CashbackHistoryItemCard_showButtonTitle, false)
            buttonTitle = getString(R.styleable.CashbackHistoryItemCard_buttonTitle) ?: ""
        }
        typedArray.recycle()

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}