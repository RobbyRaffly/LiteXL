package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_dashboard_widget_no_quota.view.*

class NoBalanceDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onActionButtonPress = value
        }

    val viewCardMain by lazy {
        containerView.viewCardMain
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_no_balance, this, true)
    }


}