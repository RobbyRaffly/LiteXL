package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaSummaryStatusType
import com.myxlultimate.component.template.cardWidget.CardMode
import com.myxlultimate.component.template.cardWidget.IconMode
import kotlinx.android.synthetic.main.organism_dashboard_widget_plan.view.*

class PlanDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            cardView.title = value
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            cardView.information = value
        }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            cardView.onActionButtonPress = value
        }

    // ----------------------------------------------------------------------------------

    var buttonLabel =
        resources.getString(R.string.organism_plan_dashboard_widget_action_button_label)
        set(value) {
            field = value

            cardView.buttonLabel = value
        }

    var label = resources.getString(R.string.organism_plan_dashboard_widget_label)
        set(value) {
            field = value
            cardView.label = value
        }

    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            cardView.visibility = if (value) View.GONE else View.VISIBLE
            shimmeringParent.visibility = if (value) View.VISIBLE else View.GONE
            if (value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
        }
    // ----------------------------------------------------------------------------------


    var iconImage = ContextCompat.getDrawable(context, R.drawable.ic_home)
        set(value) {
            field = value
            cardView.iconImage = value
        }

    var bottomLabel = ""
        set(value) {
            field = value
            cardView.bottomLabel = value
        }

    var bottomCtaLabel = ""
        set(value) {
            field = value
            cardView.bottomCtaLabel = value
        }

    var bottomTitle = ""
        set(value) {
            field = value
            cardView.bottomTitle = value
        }

    var bottomInformation = ""
        set(value) {
            field = value
            cardView.bottomInformation = value
        }

    var bottomInformationError = false
        set(value) {
            field = value
            cardView.bottomInformationError = value
        }

    var hasWarning = false
        set(value) {
            field = value
            cardView.hasWarning = value
        }

    var onBottomCtaClick: (() -> Unit)? = null
        set(value) {
            field = value
            cardView.onBottomCtaClick = {
                if (value != null) {
                    value()
                }
            }
        }

    var isBottomCtaDisable = false
        set(value) {
            field = value
            cardView.isButtonDisable = value
        }

    var isBottomTitleDisable = false
        set(value) {
            field = value
            cardView.isBottomTitleDisable = value
        }


    val viewCardMain by lazy {
        cardView.viewCardMain
    }

    // ----------------------------------------------------------------------------------

    val viewCardShimmer by lazy {
        shimmeringParent
    }

    // ----------------------------------------------------------------------------------

    var iconMode: IconMode? = null
        set(value) {
            field = value
            value?.let {
                cardView.iconMode = it
            }
        }

    var isShowIcon = true
        set(value) {
            field = value

            cardView.isShowIcon = value
        }
    // ----------------------------------------------------------------------------------

    var buttonLabelCta = ""
        set(value) {
            field = value

            cardView.buttonLabelCta = value
        }
    // ----------------------------------------------------------------------------------

    var isVisibleButtonCta = false
        set(value) {
            field = value

            cardView.isVisibleButtonCta = value
        }
    // ----------------------------------------------------------------------------------

    var onPressButtonCta: (() -> Unit)? = null
        set(value) {
            field = value

            cardView.onPressButtonCta = value
        }
    // ----------------------------------------------------------------------------------


    var cardMode = CardMode.NORMAL
        set(value) {
            field = value
            cardView.cardMode = value
        }
    // ----------------------------------------------------------------------------------

    var quotaSummaryStatusType = QuotaSummaryStatusType.NONE
        set(value) {
            field = value
            cardView.quotaSummaryStatusType = value
        }

    // ----------------------------------------------------------------------------------

    var flagIcon : String = resources.getString(R.string.xl_plane)
        set(value) {
            field = value

            cardView.flagIcon = value
        }

    // ----------------------------------------------------------------------------------

    var customFlagText = ""
        set(value) {
            field = value

            cardView.customFlagText = value
        }

    // ----------------------------------------------------------------------------------

    var customBackgroundFlag:Int? = null
        set(value) {
            field = value

            value?.let {
                cardView.customBackgroundFlag = it
            }
        }

    // ----------------------------------------------------------------------------------

    var showExclamationMarkEnd = false
        set(value) {
            field = value

            cardView.showExclamationMarkEnd = value
        }

    // ----------------------------------------------------------------------------------

    var onExclamationMarkClickEnd : (() -> Unit)? = null
        set(value) {
            field = value

            cardView.onExclamationMarkClickEnd = value
        }

    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_plan, this, true)
        cardView.onBottomCtaClick = {
            onBottomCtaClick
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashboardPlanAttr)
        typedArray.run {
            title = getString(R.styleable.DashboardPlanAttr_title) ?: ""
            information = getString(R.styleable.DashboardPlanAttr_information) ?: ""
            isShimmerOn = getBoolean(R.styleable.DashboardPlanAttr_isShimmerOn, false)
            iconImage =
                getDrawable(R.styleable.DashboardPlanAttr_iconImage) ?: ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_home
                )
            buttonLabel = getString(R.styleable.DashboardPlanAttr_buttonLabel)
                ?: resources.getString(R.string.organism_plan_dashboard_widget_action_button_label)
            label = getString(R.styleable.DashboardPlanAttr_label)
                ?: resources.getString(R.string.organism_plan_dashboard_widget_label)
            bottomLabel = getString(R.styleable.DashboardPlanAttr_bottomLabel)
                ?: ""
            bottomCtaLabel = getString(R.styleable.DashboardPlanAttr_bottomCtaLabel) ?: ""
            bottomInformation = getString(R.styleable.DashboardPlanAttr_bottomInformation) ?: ""
            bottomTitle = getString(R.styleable.DashboardPlanAttr_bottomTitle) ?: ""
            hasWarning = getBoolean(R.styleable.DashboardPlanAttr_hasWarning, false)
            isBottomCtaDisable = getBoolean(R.styleable.DashboardPlanAttr_isBottomCtaDisable, false)
            isBottomTitleDisable =
                getBoolean(R.styleable.DashboardPlanAttr_isBottomTitleDisable, false)
        }
        typedArray.recycle()
    }

}