package com.myxlultimate.component.organism.faq

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.faq.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_faq_item_group.view.*

class FaqItemGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data (
        val title : String = "",
        val items : MutableList<FaqItem.Data>
    )
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<FaqItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var title = ""
    set(value) {
        field = value
        titleView.text = value
        IsEmptyUtil.setVisibility(value,titleView)
    }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        RecycleViewAdapter { index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_faq_item_group, this, true)

        faqItemContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 17))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is FaqItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                FaqItem.Data(
                    child.question,
                    child.hasBottomLine,
                    child.isShimmerOn,
                    child.hasRightArrow
                )
            )
            recycleAdapter.items = items
        }
    }

}