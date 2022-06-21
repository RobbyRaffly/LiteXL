package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.template.cardWidget.CardMode
import khronos.Dates
import khronos.days
import khronos.plus
import khronos.toString
import kotlinx.android.synthetic.main.organism_dashboard_widget_contract.view.*

class ContractDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var cardMode = CardMode.SMALL
        set(value) {
            field = value
            containerView.cardMode = value
        }
    // ----------------------------------------------------------------------------------

    val viewCardMain by lazy {
        containerView.viewCardMain
    }
    // ----------------------------------------------------------------------------------

    val viewCardShimmer by lazy {
        parentSkeletonlayout
    }
    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                childSkeletonLayout.startShimmer()
            } else {
                childSkeletonLayout.stopShimmer()
            }
            parentSkeletonlayout.visibility = if (value) View.VISIBLE else View.GONE
            containerView.visibility = if (value) View.GONE else View.VISIBLE
        }

    // ----------------------------------------------------------------------------------

    var current = 0
        set(value) {
            field = value

            contentView.text =
                context.getString(R.string.organism_contract_dashboard_widget_content, value, total)
        }

    // ----------------------------------------------------------------------------------

    var total = 0
        set(value) {
            field = value

            contentView.text = context.getString(
                R.string.organism_contract_dashboard_widget_content,
                current,
                value
            )
        }

    // ----------------------------------------------------------------------------------

    var expiredAt: Long = 0
        set(value) {
            field = value

            val today = Dates.today
            val date = today + value.toInt().days
            val dateString = date.toString("d MMMM yyyy")
            informationView.text = context.getString(
                R.string.organism_contract_dashboard_widget_information,
                dateString
            )
        }

    // ----------------------------------------------------------------------------------

    var currentString = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                contentView.text = value
            }
        }
    // ----------------------------------------------------------------------------------

    var cardTitle = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                informationView.text = value
            }
        }
    // ----------------------------------------------------------------------------------

    var hideInformationView = false
        set(value) {
            field = value
            informationView.visibility = if (value) View.GONE else View.VISIBLE
        }

    // ----------------------------------------------------------------------------------

    var cardWidgetTitle = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                containerView.label = value
            }
        }
    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
            containerView.onActionButtonPress = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_contract, this, true)

        cardMode = CardMode.SMALL
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DashboardWidgetContractAttr)
        typedArray.run {
            current = getInt(R.styleable.DashboardWidgetContractAttr_current, 0)
            total = getInt(R.styleable.DashboardWidgetContractAttr_total, 0)
            expiredAt = getInt(R.styleable.DashboardWidgetContractAttr_expiredAt, 0).toLong()
            cardTitle = getString(R.styleable.DashboardWidgetContractAttr_cardTitle)?:""
            hideInformationView = getBoolean(R.styleable.DashboardWidgetContractAttr_hideInformationView,false)
            cardWidgetTitle = getString(R.styleable.DashboardWidgetContractAttr_cardWidgetTitle)?:""
            currentString = getString(R.styleable.DashboardWidgetContractAttr_currentString)?:""
        }
        typedArray.recycle()
    }
}