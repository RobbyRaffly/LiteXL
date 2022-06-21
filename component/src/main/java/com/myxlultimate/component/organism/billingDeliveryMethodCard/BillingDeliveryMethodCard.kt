package com.myxlultimate.component.organism.billingDeliveryMethodCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_billing_delivery_method_card.view.*

class BillingDeliveryMethodCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            tvLabel.text = value
        }

    // ----------------------------------------------------------------------------------

    var buttonTitle = ""
        set(value) {
            field = value
            tvButtonTitle.text = value
        }

    // ----------------------------------------------------------------------------------

    var type = BillingDeliveryMethodMode.EMAIL
        set(value) {
            field = value
            if (value == BillingDeliveryMethodMode.EMAIL) {
                ivIcon.setImageDrawable(context.getDrawable(R.drawable.ic_email))
                tvButtonTitle.visibility = View.GONE
            } else {
                ivIcon.setImageDrawable(context.getDrawable(R.drawable.ic_post))
                tvButtonTitle.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(tvButtonTitle, value)
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_billing_delivery_method_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BillingDeliveryMethodCard)
        typedArray.run {
            label = getString(R.styleable.BillingDeliveryMethodCard_label) ?: ""
            buttonTitle = getString(R.styleable.BillingDeliveryMethodCard_buttonTitle) ?: ""
            type =
                BillingDeliveryMethodMode.values()[getInt(
                    R.styleable.BillingDeliveryMethodCard_type,
                    0
                )]
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(tvButtonTitle, onPress)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}