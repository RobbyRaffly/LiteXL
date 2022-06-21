package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_dashboard_widget_no_quota.view.*

class SuspendedDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onActionButtonPress = value
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_no_balance, this, true)
        containerView.apply {
            label = resources.getString(R.string.organism_suspended_dashboard_widget_label)
            actionButtonLabel = resources.getString(R.string.organism_suspended_dashboard_widget_button)
        }
        contentView.text = resources.getString(R.string.organism_suspended_dashboard_widget_content)
    }


}