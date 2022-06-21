package com.myxlultimate.component.organism.transactionCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.shimmering_transaction_payment_method_option_card_group.view.*

class ShimmeringTransactionPaymentMethodOptionCardGroup@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data (
        val isShimmerOn : Boolean? = false
    )

    var isShimmerOn = false
        set(value) {
            field = value
            if(value) {
                shimmerLayout.startShimmer()
                shimmerLayout2.startShimmer()
                shimmerLayout3.startShimmer()
                shimmerLayout4.startShimmer()
                shimmerLayout5.startShimmer()
                shimmerLayout6.startShimmer()
                shimmerLayout7.startShimmer()
                shimmerLayout8.startShimmer()
                shimmerLayout9.startShimmer()

            } else {
                shimmerLayout.stopShimmer()
                shimmerLayout2.stopShimmer()
                shimmerLayout3.stopShimmer()
                shimmerLayout4.stopShimmer()
                shimmerLayout5.stopShimmer()
                shimmerLayout6.stopShimmer()
                shimmerLayout7.stopShimmer()
                shimmerLayout8.stopShimmer()
                shimmerLayout9.stopShimmer()
            }
            this.visibility = if(isShimmerOn) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_transaction_payment_method_option_card_group, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShimmeringTransactionPaymentMethodOptionCardGroup)
        typedArray.run {
            isShimmerOn = getBoolean(R.styleable.ShimmeringTransactionPaymentMethodOptionCardGroup_isShimmerOn,false)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}