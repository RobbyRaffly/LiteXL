package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.FontSizeMode
import com.myxlultimate.component.template.cardWidget.CardMode
import com.myxlultimate.component.template.cardWidget.IconMode
import kotlinx.android.synthetic.main.organism_dashboard_summary_card.view.*
import kotlinx.android.synthetic.main.template_card_widget.view.*

class DashboardSummaryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var titleSize = FontSizeMode.LARGE
    set(value) {
        field = value
        titleView.visibility = if (value == FontSizeMode.LARGE) View.VISIBLE else View.GONE
        titleViewMedium.visibility = if (value == FontSizeMode.MEDIUM) View.VISIBLE else View.GONE
    }


    // ----------------------------------------------------------------------------------

    var cardMode = CardMode.NORMAL
        set(value) {
            field = value
            containerView.cardMode = value
        }

    // ----------------------------------------------------------------------------------

    var iconMode = IconMode.SMALL
    set(value) {
        field = value
        containerView.iconMode = value
    }

    // ----------------------------------------------------------------------------------


    var label = ""
        set(value) {
            field = value
            containerView.label = value
        }

    // ----------------------------------------------------------------------------------

    var icon = R.drawable.icon_balance
        set(value) {
            field = value
            containerView.icon = context.getDrawable(value)
        }

    // ----------------------------------------------------------------------------------

    var actionButtonLabel = ""
        set(value) {
            field = value
            containerView.actionButtonLabel = value
        }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onActionButtonPress = value
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            titleView.text = value
            titleViewMedium.text = value
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
            .inflate(R.layout.organism_dashboard_summary_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashboardSummaryCardAttr)
        typedArray.run {
            cardMode = CardMode.values()[getInt(R.styleable.DashboardSummaryCardAttr_cwaCardMode,0)]
            titleSize = FontSizeMode.values()[getInt(R.styleable.DashboardSummaryCardAttr_fontSizeMode,0)]
            iconMode = IconMode.values()[getInt(R.styleable.DashboardSummaryCardAttr_cwaIconMode, 1)]
            icon = getResourceId(R.styleable.DashboardSummaryCardAttr_icon, 0)
            label = getString(R.styleable.DashboardSummaryCardAttr_label) ?: ""
            actionButtonLabel = getString(R.styleable.DashboardSummaryCardAttr_actionButtonLabel) ?: ""
            title = getString(R.styleable.DashboardSummaryCardAttr_title)?:""
            information = getString(R.styleable.DashboardSummaryCardAttr_information)?:""
        }
        typedArray.recycle()
    }

}