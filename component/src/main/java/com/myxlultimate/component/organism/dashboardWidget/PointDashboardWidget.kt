package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_dashboard_widget_point.view.*

class PointDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isShimmerOn = false
    set(value) {
        field = value
        if(value) {
            shimmerLayout.startShimmer()

        } else {
            shimmerLayout.stopShimmer()
        }
        cardView.visibility = if(value) View.GONE else View.VISIBLE
        parentSkeletonlayout.visibility = if(value) View.VISIBLE else View.GONE
    }
    var total: Long = 0
        set(value) {
            field = value

            cardView.value = ConverterUtil.convertDelimitedNumber(value,true) + " " + context.getString(R.string.point_dashboard_widget)
        }

    // ----------------------------------------------------------------------------------

    var expiring: Long = 0

    // ----------------------------------------------------------------------------------

    var expiredAt: Long = 0
        set(value) {
            field = value

//            cardView.information = if (expiring > 0) context.getString(R.string.organism_point_dashboard_widget_information, expiring, value.toString()) else ""
            cardView.information = context.getString(R.string.organism_point_dashboard_widget_information)
        }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            cardView.onActionButtonPress = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_point, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashboardWidgetPointAttr)
        typedArray.run {
            total = getInt(R.styleable.DashboardWidgetPointAttr_total, 0).toLong()
            expiring = getInt(R.styleable.DashboardWidgetPointAttr_expiring, 0).toLong()
            expiredAt = getInt(R.styleable.DashboardWidgetPointAttr_expiredAt, 0).toLong()
        }
        typedArray.recycle()
    }

}