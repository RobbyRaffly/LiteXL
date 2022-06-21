package com.myxlultimate.component.organism.menuPrioFlexCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.menuPrioFlexCard.adapter.MenuPrioFlexCardAdapter
import kotlinx.android.synthetic.main.organism_menu_prio_flex_card_group.view.*

class MenuPrioFlexCardItemGroup@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<MenuPrioFlexCardItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------
    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    private val recycleAdapter by lazy {
        MenuPrioFlexCardAdapter { index ->
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

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_menu_prio_flex_card_group, this, true)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MenuPrioFlexCardGroupAttr
        )

        typedArray.run {
            labelGroup = getString(R.styleable.MenuPrioFlexCardGroupAttr_labelGroup) ?: ""
        }
        typedArray.recycle()

        rvContainer.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            adapter = recycleAdapter.also { it.items = items }
        }
    }
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is MenuPrioFlexCardItem) {
            super.addView(child, index, params)
        }else {
            items.add(
                MenuPrioFlexCardItem.Data(
                    child.label,
                    child.iconImage,
                    child.imageSourceType,
                    child.onPress

                )
            )
        }
    }

}