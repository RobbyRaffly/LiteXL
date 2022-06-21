package com.myxlultimate.component.organism.optionItemCardList

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.optionItemCard.LanguageOptionItemCard
import com.myxlultimate.component.molecule.optionItemCard.OptionItemCard
import com.myxlultimate.component.organism.optionItemCardList.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_language_option_card.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OptionItemCardList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<OptionItemCard.Data>()
        set(value) {
            field = value
            updateItems(value)
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        RecycleViewAdapter {
            activeIndex = it
        }
    }

    // ----------------------------------------------------------------------------------

    var activeIndex = -1
        set(value) {
            if (!itemContainerView.isComputingLayout) {
                field = value
                items.forEachIndexed { index, data ->
                    items[index].isActive = index == value
                }
                updateItems(items)
                onActiveItemChange?.let { it(value) }
            }
        }

    // ----------------------------------------------------------------------------------

    var onActiveItemChange: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_option_item_card, this, true)

        itemContainerView.apply {
            val params = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params

            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { updateItems(items) }
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OptionCardAttr)
        typedArray.run {
            activeIndex = getInt(R.styleable.OptionCardAttr_activeIndex, -1)
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is OptionItemCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                OptionItemCard.Data(
                    child.title,
                    child.subtitle,
                    child.imageSourceType,
                    child.imageSource,
                    items.size == activeIndex
                )
            )
            updateItems(items)
        }
    }

    // ----------------------------------------------------------------------------------

    private fun updateItems(items: List<OptionItemCard.Data>) {
        if (!itemContainerView.isComputingLayout)
            recycleAdapter.items = items
    }
}