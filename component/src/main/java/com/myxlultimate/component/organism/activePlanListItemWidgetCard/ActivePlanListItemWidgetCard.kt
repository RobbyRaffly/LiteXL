package com.myxlultimate.component.organism.activePlanListItemWidgetCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_active_plan_list_item_widget_card.view.*

class ActivePlanListItemWidgetCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val iconImage: String? = "",
        val title: String? = "",
        val information: String? = "",
        val quotaInformation: String? = ""
    )

    // ----------------------------------------------------------------------------------

    var iconImage = ""
        set(value) {
            field = value
            activePlanListView.iconImage = value
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            activePlanListView.title = value
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            activePlanListView.information = value
        }

    // ----------------------------------------------------------------------------------

    var quotaInformation = ""
        set(value) {
            field = value
            quotaView.text = value
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_active_plan_list_item_widget_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActivePlanListItemWidgetCard)
        typedArray.run {
            iconImage = getString(R.styleable.ActivePlanListItemWidgetCard_iconImage) ?: ""
            title = getString(R.styleable.ActivePlanListItemWidgetCard_title) ?: ""
            information = getString(R.styleable.ActivePlanListItemWidgetCard_information) ?: ""
            quotaInformation = getString(R.styleable.ActivePlanListItemWidgetCard_quotaInformation)?:""
        }
        typedArray.recycle()

    }
}