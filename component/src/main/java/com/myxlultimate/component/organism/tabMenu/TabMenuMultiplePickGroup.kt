package com.myxlultimate.component.organism.tabMenu

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.tabMenu.adapters.RecycleViewAdapterMultiple
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_tab_menu_group.view.*

class TabMenuMultiplePickGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val items = mutableListOf<TabMenuItem.Data>()
    val itemsStatus: HashMap<String, Boolean> = HashMap()

    // ----------------------------------------------------------------------------------

    private var activeIndex = -1


    // ----------------------------------------------------------------------------------

    var textColor: Int = ContextCompat.getColor(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value

            recycleAdapter.items.forEach() {
                it.textColorActive = value
            }

            recycleAdapter.notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------

    var textColorInactive: Int = ContextCompat.getColor(context, R.color.mud_palette_basic_black)
        set(value) {
            field = value

            recycleAdapter.items.forEach() {
                it.textColorInactive = value
            }

            recycleAdapter.notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------

    var activeMenuColor: Int = ContextCompat.getColor(context, R.color.mud_palette_basic_black)
        set(value) {
            field = value

            recycleAdapter.items.forEach() {
                it.activeMenuColor = value
            }

            recycleAdapter.notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------

    var inactiveMenuColor: Int = ContextCompat.getColor(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value

            recycleAdapter.items.forEach() {
                it.inactiveMenuColor = value
            }

            recycleAdapter.notifyDataSetChanged()
        }

    var itemPaddingHorizontal: Float = 12f
        set(value) {
            field = value

            recycleAdapter.items.forEach() {
                it.paddingHorizontal = value
            }

            recycleAdapter.notifyDataSetChanged()
        }

    var itemPaddingVertical: Float = 4f
        set(value) {
            field = value

            recycleAdapter.items.forEach() {
                it.paddingVertical = value
            }

            recycleAdapter.notifyDataSetChanged()
        }

    var inactiveAlpha: Float = 0.5f
        set(value) {
            field = value

            recycleAdapter.items.forEach() {
                it.inactiveAlpha = value
            }

            recycleAdapter.notifyDataSetChanged()
        }


    // ----------------------------------------------------------------------------------

    var onActiveItemChange: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter: RecycleViewAdapterMultiple

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_tab_menu_group, this, true)

        recycleAdapter = RecycleViewAdapterMultiple(items) { itemIndex, status ->
            Log.d("GRACE", "Items Data ${items[itemIndex].label}, Status $status")
            itemsStatus[items[itemIndex].label] = status
            Log.d("GRACE", "List Items $itemsStatus")
            Log.d("GRACE", "List keys ${itemsStatus.keys}")
            Log.d("GRACE", "List entries ${itemsStatus.entries}")
        }
//            setActiveIndex(it)
        itemContainerView.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 8, true))
            adapter = recycleAdapter
        }

        val colorPrimaryTypedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(android.R.attr.colorPrimary, colorPrimaryTypedValue, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabMenuGroupAttr)
        typedArray.run {
//            setActiveIndex(getInt(R.styleable.TabMenuGroupAttr_activeIndex, -1))
            textColor =
                getColor(R.styleable.TabMenuGroupAttr_textColorActive, colorPrimaryTypedValue.data)
            textColorInactive = getColor(
                R.styleable.TabMenuGroupAttr_textColorInactive,
                ContextCompat.getColor(context, R.color.mud_palette_basic_white)
            )
            activeMenuColor = getColor(
                R.styleable.TabMenuGroupAttr_activeMenuColor,
                ContextCompat.getColor(context, R.color.mud_palette_basic_white)
            )
            inactiveMenuColor = getColor(
                R.styleable.TabMenuGroupAttr_inactiveMenuColor,
                ContextCompat.getColor(context, R.color.mud_palette_basic_white)
            )
            itemPaddingHorizontal =
                getDimension(R.styleable.TabMenuGroupAttr_itemPaddingHorizontal, 12f)
            itemPaddingVertical = getDimension(R.styleable.TabMenuGroupAttr_itemPaddingVertical, 4f)
            inactiveAlpha = getFloat(R.styleable.TabMenuGroupAttr_itemInactiveAlpha, 0.5f)

        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is TabMenuItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                TabMenuItem.Data(
                    child.label,
                    child.isActive,
                    activeMenuColor = activeMenuColor,
                    textColorActive = textColor,
                    textColorInactive = textColorInactive,
                    paddingHorizontal = itemPaddingHorizontal,
                    paddingVertical = itemPaddingVertical,
                    inactiveAlpha = inactiveAlpha,
                    inactiveMenuColor = inactiveMenuColor

                )
            )
        }
    }

    fun setItems(list: List<TabMenuItem.Data>) {
        items.clear()
        items.addAll(list)
        items.forEach() {
            it.activeMenuColor = activeMenuColor
            it.textColorInactive = textColorInactive
            it.textColorActive = textColor
            it.paddingVertical = itemPaddingVertical
            it.paddingHorizontal = itemPaddingHorizontal
            it.inactiveAlpha = inactiveAlpha
            it.inactiveMenuColor = inactiveMenuColor
        }
        recycleAdapter.notifyDataSetChanged()
    }

//    fun setActiveIndex(value: Int) {
//        if (!itemContainerView.isComputingLayout) {
//            activeIndex = value
//            items.forEachIndexed { index, _ ->
//                items[index].isActive = index == value
//            }
//
//            onActiveItemChange?.let {
//                it(value)
//            }
//            recycleAdapter.notifyDataSetChanged()
//        }
//    }

    fun getActiveIndex(): Int = activeIndex

    fun isNotEmpty(): Boolean {
        return items.isNotEmpty()
    }
}