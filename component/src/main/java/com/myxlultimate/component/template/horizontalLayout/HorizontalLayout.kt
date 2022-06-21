package com.myxlultimate.component.template.horizontalLayout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_quick_menu_group.view.*

class HorizontalLayout @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_quick_menu_group, this, true)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (mainView == null)
        super.addView(child, params) else mainView.addView(child, index, params)
    }

    override fun addView(child: View?, index: Int) {
        if (mainView == null)
        super.addView(child, index) else mainView.addView(child, index)
    }

}