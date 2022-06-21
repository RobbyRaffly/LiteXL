package com.myxlultimate.component.organism.quickMenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.quotaSummary.QuotaSummaryItem
import kotlinx.android.synthetic.main.organism_quick_menu_group.view.*

class QuickMenuGroup @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var itemCounter = 0

    // ----------------------------------------------------------------------------------

    var children = mutableListOf<QuickMenuItem>()

    // ----------------------------------------------------------------------------------

    var isDisabled = false
        set(value) {
            field = value

            children.forEach {
                it.isDisabled = value
            }
        }

    // ----------------------------------------------------------------------------------

    var enabledSingleDisabled = false

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_quick_menu_group, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuickMenuGroupAttr)
        typedArray.run {
            isDisabled = getBoolean(R.styleable.QuickMenuGroupAttr_isDisabled, false)
            enabledSingleDisabled = getBoolean(R.styleable.QuickMenuGroupAttr_enabledSingleDisabled,false)

        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if(mainView == null)
            super.addView(child, index, params)
        else if (child != null && child is QuickMenuItem) {
            if (itemCounter > 0) {
                val view = View(context)
                val layoutParams = LayoutParams(
                    0,
                    LayoutParams.MATCH_PARENT,
                    1F
                )

                mainView.addView(view, layoutParams)
            }

            child.isDisabled = if(enabledSingleDisabled) child.isDisabled else isDisabled

            children.add(child)
            mainView.addView(child, index, params)
            itemCounter++
        }
    }
}