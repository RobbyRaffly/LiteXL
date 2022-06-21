package com.myxlultimate.component.molecule.unsubscribePackageReason

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.unsubscribePackageReason.adapters.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.molecule_unsubscribe_package_reason_group.view.*


class UnsubscribePackageReasonGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<UnsubscribePackageReasonItem.Data>(
//        StopPackageReasonItem.Data("asd", "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z/C/HgAGgwJ/lK3Q6wAAAABJRU5ErkJggg=="),
//        StopPackageReasonItem.Data("def", "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="),
//        StopPackageReasonItem.Data("ghi", "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mN8+J+hHgAHCgJhqoVbfAAAAABJRU5ErkJggg=="),
//        StopPackageReasonItem.Data("jkl", "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mPkV7pUDwACzwGEV0mXgwAAAABJRU5ErkJggg==")
    )
        set(value) {
            field = value

            activeIndex = -1
        }

    // ----------------------------------------------------------------------------------

    private var delay = System.currentTimeMillis()

    // ----------------------------------------------------------------------------------

    var activeIndex = -1
        set(value) {
            if (delay + 300 > System.currentTimeMillis()) return

            field = value

            items.forEachIndexed { index, data ->
                items[index].isActive = index == value
            }
            recycleAdapter.items = items

            onActiveItemChange?.let { it(value) }
            delay = System.currentTimeMillis()
        }

    // ----------------------------------------------------------------------------------

    var onActiveItemChange: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        RecycleViewAdapter {
            activeIndex = it
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_unsubscribe_package_reason_group, this, true)

        itemContainerView.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 24, true))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is UnsubscribePackageReasonItem) {
            super.addView(child, index, params)
        } else {
            items.add(UnsubscribePackageReasonItem.Data(
                child.label,
                child.iconImage
            ))
        }
    }
}