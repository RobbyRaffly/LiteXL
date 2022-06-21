package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_dashboard_widget_inactive_account.view.*

class InactiveAccountDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onActionButtonPress = value
        }

    var title = resources.getString(R.string.organism_inactive_account_dashboard_widget_content)
    set(value) {
        field = value
        tvTitle.text = value
    }

    var label = ""
        set(value) {
            field = value
            containerView.label = value
        }

    var buttonLabel = ""
        set(value) {
            field = value
            containerView.actionButtonLabel = value
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_inactive_account, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InactiveAccountDashboardWidget)
        typedArray.run {
            title = getString(R.styleable.InactiveAccountDashboardWidget_title)?:resources.getString(R.string.organism_inactive_account_dashboard_widget_content)
            label = getString(R.styleable.InactiveAccountDashboardWidget_label)?:""
            buttonLabel = getString(R.styleable.InactiveAccountDashboardWidget_buttonTitle)?:resources.getString(R.string.organism_inactive_account_dashboard_widget_action_button_label)
        }
        typedArray.recycle()
    }

}