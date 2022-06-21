package com.myxlultimate.component.organism.quotaDetailWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.quotaSummary.QuotaSummaryGroup
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_quota_detail_widget_roaming_quota_summary.view.*

class RoamingQuotaSummaryQuotaDetailWidget@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isActive = false
        set(value) {
            field = value

            if (isActive) {
                statusFlagView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.mud_palette_basic_green)
                itemContainerView.isDisabled = false
                statusLabelView.text = context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_active)

                actionButtonView.apply {
                    text = context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_deactivate_button_label)
                    setOnClickListener { onDeactivateButtonPress?.let { it() } }
                }
            } else {
                statusFlagView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.mud_palette_basic_red)
                itemContainerView.isDisabled = true
                statusLabelView.text = resources.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_inactive)

                actionButtonView.apply {
                    text = resources.getString(R.string.organism_roaming_quota_summary_dashboard_widget_activate_button_label)
                    setOnClickListener { onActivateButtonPress?.let { it() } }
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var onActivateButtonPress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onDeactivateButtonPress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_quota_detail_widget_roaming_quota_summary, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuotaDetailWidgetRoamingQuotaSummaryAttr)
        typedArray.run {
            isActive = getBoolean(R.styleable.QuotaDetailWidgetRoamingQuotaSummaryAttr_isActive,false)
        }
        typedArray.recycle()
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