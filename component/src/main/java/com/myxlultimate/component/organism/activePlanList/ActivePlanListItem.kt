package com.myxlultimate.component.organism.activePlanList

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_active_plan_item.view.*

class ActivePlanListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val iconImage : String?="",
        val title : String?="",
        val information : String?=""
    )

    // ----------------------------------------------------------------------------------

    var iconImage = ""
    set(value) {
        field = value
        iconView.imageSource = value
    }

    // ----------------------------------------------------------------------------------

    var title = ""
    set(value) {
        field = value
        titleView.text = value
    }

    // ----------------------------------------------------------------------------------

    var information = ""
    set(value) {
        field = value
        informationView.text = value
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_active_plan_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActivePlanListItem)
        typedArray.run {
            iconImage = getString(R.styleable.ActivePlanListItem_iconImage)?:""
            title = getString(R.styleable.ActivePlanListItem_title)?:""
            information = getString(R.styleable.ActivePlanListItem_information)?:""
        }
        typedArray.recycle()

    }
}