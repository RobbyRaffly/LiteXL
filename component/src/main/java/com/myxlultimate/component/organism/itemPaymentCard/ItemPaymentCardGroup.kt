package com.myxlultimate.component.organism.itemPaymentCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ListUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_item_payment_card_group.view.*

class ItemPaymentCardGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<ItemPaymentCardItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    var onItemPress: ((Int) -> Unit)? = null

    private val recycleAdapter by lazy {
        PaymentRecyclerViewAdapter { index ->
            onItemPress?.let { it(index) }
        }
    }

    var onButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(rvTypePayment, value)
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_item_payment_card_group, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TransactionHistoryWidget)
        typedArray.run {

        }
        typedArray.recycle()

        rvTypePayment.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }
    }


}