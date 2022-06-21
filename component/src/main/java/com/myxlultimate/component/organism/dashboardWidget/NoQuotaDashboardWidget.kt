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

class NoQuotaDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onActionButtonPress = value
        }

    var noQuotaIcon = R.drawable.ic_belum_punya_paket_prepaid_icon
        set(value) {
            field = value
            refreshView()
        }

    var isFailedFromApi = false
        set(value) {
            field = value
            refreshView()
        }

    var label = resources.getString(R.string.organism_quota_summary_dashboard_widget_label)
        set(value) {
            field = value
            refreshView()
        }

    var noActivePackageContainerLabel = label
        set(value) {
            field = value
            refreshView()
        }

    var noActivePackageLabel = resources.getString(R.string.organism_no_quota_dashboard_widget_content)
        set(value) {
            field = value
            refreshView()
        }

    var noActivePackageCTALabel = resources.getString(R.string.organism_no_quota_dashboard_widget_action_button_label)
        set(value) {
            field = value
            refreshView()
        }

    val viewCardMain by lazy {
        containerView.viewCardMain
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_no_quota, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DashboardWidgetNoQuotaAttr)
        typedArray.run {
            isFailedFromApi =
                (getBoolean(R.styleable.DashboardWidgetNoQuotaAttr_isFailedFromApi, false))
            noQuotaIcon = getResourceId(
                R.styleable.DashboardWidgetNoQuotaAttr_noQuotaIcon,
                R.drawable.ic_belum_punya_paket_prepaid_icon
            )
            label = getString(R.styleable.DashboardWidgetNoQuotaAttr_label)?:resources.getString(R.string.organism_quota_summary_dashboard_widget_label)
        }
        val typedValue = TypedValue()
        context.theme?.resolveAttribute(R.attr.emptyPackagePngIcon, typedValue, true)
        noQuotaIcon = typedValue.resourceId
        typedArray.recycle()
    }

    private fun refreshView() {
        containerView.label = label
        if (isFailedFromApi) {
            contentView.text =
                resources.getString(R.string.organism_fail_quota_dashboard_widget_content)
            containerView.actionButtonLabel =
                resources.getString(R.string.organism_fail_quota_dashboard_widget_action_button_label)
            containerView.icon = ContextCompat.getDrawable(context, R.drawable.icon_refresh)
            containerView.hasRightArrow = false
        } else {
            containerView.label = noActivePackageContainerLabel
            contentView.text = noActivePackageLabel
            containerView.actionButtonLabel = noActivePackageCTALabel
            containerView.icon = ContextCompat.getDrawable(context, noQuotaIcon)
            containerView.hasRightArrow = true
        }
    }


}