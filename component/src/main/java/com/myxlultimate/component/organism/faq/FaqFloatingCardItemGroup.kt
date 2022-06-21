package com.myxlultimate.component.organism.faq

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.faq.adapter.FaqFloatingCardAdapter
import kotlinx.android.synthetic.main.organism_faq_floating_card_group.view.*

class FaqFloatingCardItemGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<FaqFloatingCardItem.Data>()
        set(value) {
            field = value
            faqFloatingCardAdapter.items = value
        }

    var onItemPress: ((Int) -> Unit)? = null

    private val faqFloatingCardAdapter by lazy {
        FaqFloatingCardAdapter { index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_faq_floating_card_group, this, true)

        rvFaqFloatingContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            adapter = faqFloatingCardAdapter.also {
                it.items = items
            }
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is FaqFloatingCardItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                FaqFloatingCardItem.Data(
                    child.title
                )
            )
            faqFloatingCardAdapter.items = items
        }
    }
}