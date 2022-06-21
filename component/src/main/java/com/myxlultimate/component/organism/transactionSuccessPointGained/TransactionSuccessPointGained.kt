package com.myxlultimate.component.organism.transactionSuccessPointGained

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_transaction_success_point_gained.view.*

class TransactionSuccessPointGained @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var amount = 0
        set(value) {
            field = value

            priceView.text = resources.getString(R.string.organism_transaction_success_point_gained_amount,value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_success_point_gained, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.transactionSuccessPointGainedAttr)
        typedArray.run {
          amount = getInt(R.styleable.transactionSuccessPointGainedAttr_amount,0)
        }
        typedArray.recycle()
    }
}