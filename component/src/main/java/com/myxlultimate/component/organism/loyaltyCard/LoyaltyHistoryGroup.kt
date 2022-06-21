package com.myxlultimate.component.organism.loyaltyCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.loyaltyCard.adapter.LoyaltyHistoryGroupAdapter
import com.myxlultimate.component.util.ListUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_history_group.view.*
import kotlinx.android.synthetic.main.organism_loyalty_history_group.view.informationView

class LoyaltyHistoryGroup@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<LoyaltyHistoryCard.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------
    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        LoyaltyHistoryGroupAdapter { index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }

    // ----------------------------------------------------------------------------------

    var labelGroup = ""
        set(value) {
            field = value
            labelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            if (value.isEmpty())
                informationView.visibility = View.GONE
            else
                informationView.visibility = View.VISIBLE
                informationView.text = value
        }

    // ----------------------------------------------------------------------------------
    var titleButton = ""
        set(value) {
            field = value
            if (value.isEmpty())
                titleButtonView.visibility = View.GONE
            else
                titleButtonView.visibility = View.VISIBLE
                titleButtonView.text = value
        }

    var onLabelButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(titleButtonView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_history_group, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.LoyaltyHistoryGroup)
        typedArray.run {
            labelGroup = getString(R.styleable.LoyaltyHistoryGroup_title) ?: ""
            information = getString(R.styleable.LoyaltyHistoryGroup_information) ?:""
            titleButton = getString(R.styleable.LoyaltyHistoryGroup_labelButton) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(false, titleButtonView, onLabelButtonPress)
        itemContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is LoyaltyHistoryCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                LoyaltyHistoryCard.Data(
                    child.dateTime,
                    child.title,
                    child.poin,
                    child.expiration,
                    child.hasPointIcon,
                    child.isPendingPoint
                )
            )
            recycleAdapter.items = items
        }
    }
}