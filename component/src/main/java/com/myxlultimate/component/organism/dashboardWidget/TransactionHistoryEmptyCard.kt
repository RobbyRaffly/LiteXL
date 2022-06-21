package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_transaction_history_empty_card.view.*
class TransactionHistoryEmptyCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            if(value) {
                shimmerLayout.startShimmer()

            } else {
                shimmerLayout.stopShimmer()
            }
            cardView.visibility = if(value) View.GONE else View.VISIBLE
            parentSkeletonlayout.visibility = if(value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var title = context.getString(R.string.organism_transaction_history_widget)
        set(value) {
            field = value
            tvTitle.text = value
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            tvContent.text = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_history_empty_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TransactionHistoryEmptyCard)
        typedArray.run {
            title = getString(R.styleable.TransactionHistoryEmptyCard_title) ?: context.getString(R.string.organism_transaction_history_widget)
            description = getString(R.styleable.TransactionHistoryEmptyCard_description) ?: ""
        }
        typedArray.recycle()
    }

}