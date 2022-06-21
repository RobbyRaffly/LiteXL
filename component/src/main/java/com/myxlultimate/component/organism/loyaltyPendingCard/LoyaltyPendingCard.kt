package com.myxlultimate.component.organism.loyaltyPendingCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_pending_card.view.*

class LoyaltyPendingCard  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
    set(value) {
        field = value
        titleView.text = value
    }

    var information = ""
    set(value) {
        field = value
        informationView.text = value
    }

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_pending_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyPendingCard)
        typedArray.run {
            title = getString(R.styleable.LoyaltyPendingCard_title)?:""
            information = getString(R.styleable.LoyaltyPendingCard_information)?:""
        }
        typedArray.recycle()
    }
}