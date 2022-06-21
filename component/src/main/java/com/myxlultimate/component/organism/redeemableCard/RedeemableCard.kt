package com.myxlultimate.component.organism.redeemableCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_redeemable_card.view.*

class RedeemableCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var userPoint = 0
        set(value) {
            field = value
            cardDetailView.isEnough = userPoint - price >=0
        }

    // ----------------------------------------------------------------------------------

    var isPointEnough = false
        set(value) {
            field = value
            cardDetailView.isEnough = isPointEnough
        }

    // ----------------------------------------------------------------------------------


    var name = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var validity = ""
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

    var originalPrice: Long = 0
        set(value) {
            if (value > 0 && field > 0) price = field
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

    var hasNextButton = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
            cardDetailView.imageSourceType = value
        }

    var image: Any? = null
        set(value) {
            field = value
            cardDetailView.image = value
        }

    // ----------------------------------------------------------------------------------

    var onNextPress: (() -> Unit)? = null
        set(value) {
            field = value

            cardDetailView.onNextPress = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        cardDetailView.name = name
        cardDetailView.information = information
        cardDetailView.validity = validity
        cardDetailView.price =  (ConverterUtil.convertDelimitedNumber(price, true) + " " + resources.getString(R.string.organism_redeemable_card_point))
        cardDetailView.originalPrice = if(originalPrice <= 0)  "" else  (ConverterUtil.convertDelimitedNumber(originalPrice, true) + " " + resources.getString(R.string.organism_redeemable_card_point))
        cardDetailView.hasNextButton = hasNextButton
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_redeemable_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RedeemableCardAttr)
        typedArray.run {
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.RedeemableCardAttr_imageSourceType, 3)]
            image = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.RedeemableCardAttr_iconImage) ?: ""
            } else {
                getString(R.styleable.RedeemableCardAttr_iconImage) ?: ""
            }
            name = getString(R.styleable.RedeemableCardAttr_name) ?: ""
            information = getString(R.styleable.RedeemableCardAttr_information)?:""
            validity = getString(R.styleable.RedeemableCardAttr_validity) ?: ""
            price = getInt(R.styleable.RedeemableCardAttr_point, 0).toLong()
            originalPrice = getInt(R.styleable.RedeemableCardAttr_originalPoint, 0).toLong()
            hasNextButton = getBoolean(R.styleable.RedeemableCardAttr_hasNextButton, true)
            userPoint = getInt(R.styleable.RedeemableCardAttr_userPoint,0)
        }

    }
}