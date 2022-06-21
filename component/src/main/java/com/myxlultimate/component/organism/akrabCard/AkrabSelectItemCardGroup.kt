package com.myxlultimate.component.organism.akrabCard

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.databinding.OrganismAkrabSelectItemCardGroupBinding
import com.myxlultimate.component.organism.akrabCard.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil

class AkrabSelectItemCardGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private var binding: OrganismAkrabSelectItemCardGroupBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<AkrabSelectItemCard.Data>()
        set(value) {
            field = value
            updateItems(value)
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        RecycleViewAdapter(
            onActiveItemChange = {
                activeIndex = it
            }
        )
    }

    // ----------------------------------------------------------------------------------

    var activeIndex = -1
        set(value) {
            if (binding?.itemContainerView?.isComputingLayout != true) {
                field = value
                items.forEachIndexed { index, data ->
                    items[index].isRadioActive = index == value
                }
                updateItems(items)
                onActiveItemChange?.let { it(value) }
            }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Action listeners
     * =================
     * */
    var onActiveItemChange: ((Int) -> Unit)? = null

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    // ------------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        binding =
            OrganismAkrabSelectItemCardGroupBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
            )

        binding?.itemContainerView?.apply {
            val params = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params

            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { updateItems(items) }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun updateItems(items: List<AkrabSelectItemCard.Data>) {
        if (binding?.itemContainerView?.isComputingLayout != true)
        recycleAdapter.items = items
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is AkrabSelectItemCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                AkrabSelectItemCard.Data(
                    child.akrabSelectItemMode,
                    child.title,
                    child.subtitle,
                    items.size == activeIndex
                )
            )
            updateItems(items)
        }
    }
}