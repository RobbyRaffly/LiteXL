package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaSummaryStatusType
import com.myxlultimate.component.molecule.quotaSummary.QuotaSummaryGroup
import kotlinx.android.synthetic.main.organism_dashboard_widget_quota_summary.view.*

class QuotaSummaryDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var actionButtonLabel: String =
        resources.getString(R.string.organism_quota_summary_dashboard_widget_action_button_label)
        set(value) {
            field = value
            containerView.actionButtonLabel = value
        }

    var title: String =
        resources.getString(R.string.organism_quota_summary_dashboard_widget_action_button_label)
        set(value) {
            field = value
            containerView.label = value
        }

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onActionButtonPress = value
        }

    var quotaSummaryStatusType = QuotaSummaryStatusType.NONE
        set(value) {
            field = value

            containerView.quotaSummaryStatusType = value

            itemContainerView.alpha =
                if (value == QuotaSummaryStatusType.SUSPENDED)
                    0.5f
                else
                    1f
        }

    var statusLabel: String? = ""
        set(value) {
            field = value
            value?.let { containerView.statusLabel = it }
        }

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

    var separatorCount = 2
        set(value) {
            field = value
        }

    var customFlagIcon = resources.getString(R.string.xl_internet)
        set(value) {
            field = value
            if (quotaSummaryStatusType == QuotaSummaryStatusType.CUSTOM) {
                containerView.flagIcon = value
            }
        }

    var customFlagText = ""
        set(value) {
            field = value
            if (quotaSummaryStatusType == QuotaSummaryStatusType.CUSTOM) {
                containerView.customFlagText = value
            }
        }

    var customBackgroundFlagText = R.color.mud_palette_normal_yellow
    set(value) {
        field = value
        if (quotaSummaryStatusType == QuotaSummaryStatusType.CUSTOM) {
            containerView.customBackgroundFlag = value
        }
    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    val viewCardMain by lazy {
        containerView.viewCardMain
    }

    val viewShimmerMain by lazy {
        parentSkeletonlayout
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_quota_summary, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.QuotaSummaryDashboardWidgetAttr)
        typedArray.run {
            actionButtonLabel =
                getString(R.styleable.QuotaSummaryDashboardWidgetAttr_actionButtonLabel)
                    ?: context.getString(R.string.organism_quota_summary_dashboard_widget_action_button_label)
            quotaSummaryStatusType = QuotaSummaryStatusType.values()[getInt(
                R.styleable.QuotaSummaryDashboardWidgetAttr_quotaSummaryStatusType,
                0
            )]
            title =
                getString(R.styleable.QuotaSummaryDashboardWidgetAttr_title) ?: resources.getString(
                    R.string.organism_quota_summary_dashboard_widget_action_button_label
                )
            separatorCount = getInt(R.styleable.QuotaSummaryDashboardWidgetAttr_separatorCount, 2)
            statusLabel = getString(R.styleable.QuotaSummaryDashboardWidgetAttr_statusLabel) ?: ""
            customFlagIcon = getString(R.styleable.QuotaSummaryDashboardWidgetAttr_customFlagIcon)?:resources.getString(R.string.xl_internet)
            customBackgroundFlagText = getResourceId(R.styleable.QuotaSummaryDashboardWidgetAttr_customFlagBackgroundColor,R.color.mud_palette_normal_yellow)
            customFlagText = getString(R.styleable.QuotaSummaryDashboardWidgetAttr_customFlagText)?:""
        }
        typedArray.recycle()
        isShimmerOn = false

        itemContainerView.lineQuantity = separatorCount
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (itemContainerView == null) {
            super.addView(child, index, params)
        } else if (itemContainerView is QuotaSummaryGroup) {
            itemContainerView.addView(child, index, params)
        }
    }

}