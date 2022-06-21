package com.myxlultimate.component.organism.transactionHistoryItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.transactionHistoryItem.adapter.TransactionHistoryAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_transaction_history_widget.view.*

class TransactionHistoryWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            if(value) {
                childSkeletonLayout.startShimmer()
            } else {
                childSkeletonLayout.stopShimmer()
            }
            parentSkeletonlayout.visibility = if (value) View.VISIBLE else View.GONE
            containerView.visibility = if(value) View.GONE else View.VISIBLE
        }

    // ----------------------------------------------------------------------------------

    var items = mutableListOf<TransactionHistoryItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        TransactionHistoryAdapter()
    }

    // ----------------------------------------------------------------------------------


    var label = context.getString(R.string.organism_transaction_history_widget)
        set(value) {
            field = value
            tvTitle.text = value
        }
    // ----------------------------------------------------------------------------------

    var onButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
        }
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_history_widget, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TransactionHistoryWidget)
        typedArray.run {
            label = getString(R.styleable.TransactionHistoryWidget_label)
                ?: context.getString(R.string.organism_transaction_history_widget)
        }
        typedArray.recycle()

        transactionHistoryItemRecyclerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

}