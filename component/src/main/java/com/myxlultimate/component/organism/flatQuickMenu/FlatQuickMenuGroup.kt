package com.myxlultimate.component.organism.flatQuickMenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.flatQuickMenu.adapters.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_flat_quick_menu_group.view.*
import kotlinx.android.synthetic.main.organism_flat_quick_menu_group.view.itemContainerView
import kotlinx.android.synthetic.main.organism_tab_menu_group.view.*

class FlatQuickMenuGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val items = mutableListOf<FlatQuickMenuItem.Data>()

    // ----------------------------------------------------------------------------------

    private var activeIndex = -1

    // ----------------------------------------------------------------------------------

    var onActiveItemChange: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter: RecycleViewAdapter

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_flat_quick_menu_group, this, true)
        recycleAdapter = RecycleViewAdapter(items) {
            setActiveIndex(it)
        }

        itemContainerView.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 8, true))
            adapter = recycleAdapter
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlatQuickMenuGroupAttr)
        typedArray.run {
            setActiveIndex(getInt(R.styleable.FlatQuickMenuGroupAttr_activeIndex, -1))
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is FlatQuickMenuItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                FlatQuickMenuItem.Data(
                    child.label,
                    child.iconImage,
                    items.size == activeIndex,
                    child.textColor,
                    child.labelHexString
                )
            )
        }
    }

    fun setItems(list: List<FlatQuickMenuItem.Data>) {
        items.clear()
        items.addAll(list)
        recycleAdapter.notifyDataSetChanged()
    }

    fun setActiveIndex(value: Int) {
        if (!itemContainerView.isComputingLayout) {
            activeIndex = value
            items.forEachIndexed { index, _ ->
                items[index].isActive = index == value
            }

            onActiveItemChange?.let { it(value) }

            recycleAdapter.notifyDataSetChanged()
        }
    }

    fun isNotEmpty(): Boolean {
        return items.isNotEmpty()
    }
}