package com.myxlultimate.component.organism.laguageOptionCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.optionItemCard.LanguageOptionItemCard
import com.myxlultimate.component.organism.laguageOptionCard.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_language_option_card.view.*

class LanguageOptionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<LanguageOptionItemCard.Data>()
        set(value) {
            if (delay + 300 > System.currentTimeMillis()) return
            field = value

            recycleAdapter.items = value
            delay = System.currentTimeMillis()
        }
    // ----------------------------------------------------------------------------------

    private var delay: Long = 0

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy { RecycleViewAdapter{
        activeIndex = it
    }
    }

    // ----------------------------------------------------------------------------------

    var activeIndex = -1
        set(value) {
            if (delay + 200 > System.currentTimeMillis()) return
            field = value

            items.forEachIndexed { index, data ->
                items[index].isActive = index == value
            }
            recycleAdapter.items = items

            delay = System.currentTimeMillis()
            onActiveItemChange?.let { it(value) }

        }

    // ----------------------------------------------------------------------------------

    var onActiveItemChange: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_language_option_card, this, true)

        itemContainerView.apply {
            val params = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params

            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LanguageOptionCardAttr)
        typedArray.run {
            activeIndex = getInt(R.styleable.LanguageOptionCardAttr_activeIndex, -1)
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is LanguageOptionItemCard) {
            super.addView(child, index, params)
        } else {
            items.add(LanguageOptionItemCard.Data(child.title, child.imageSource, items.size == activeIndex))
            recycleAdapter.items = items
        }
    }

    // ----------------------------------------------------------------------------------

}