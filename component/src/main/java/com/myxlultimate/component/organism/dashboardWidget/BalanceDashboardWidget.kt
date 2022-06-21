package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.DashboardBalanceWidgetMode
import com.myxlultimate.component.enums.InformationMode
import com.myxlultimate.component.template.cardWidget.IconMode
import com.myxlultimate.component.util.ConverterUtil
import khronos.Dates
import khronos.days
import khronos.plus
import khronos.toString
import kotlinx.android.synthetic.main.organism_dashboard_widget_balance.view.*
import java.text.SimpleDateFormat
import java.util.*

class BalanceDashboardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultIcon = R.attr.balanceIcon

    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            cardView.isShimmerOn = value
        }

    // ----------------------------------------------------------------------------------

    var isSmallBalanceCard = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var actionButtonLabel =
        resources.getString(R.string.organism_balance_dashboard_widget_action_button_label)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var label = resources.getString(R.string.organism_balance_dashboard_widget_label)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            refreshView()
        }

    var informationMode = InformationMode.DEFAULT
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var remaining: Long = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var mode: DashboardBalanceWidgetMode = DashboardBalanceWidgetMode.DEFAULT
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------

    var canceledAt: Long = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var creditLimit: Long = 0
        set(value) {
            field = value
            refreshView()

        }

    // ----------------------------------------------------------------------------------

    var lowBalanceThreshold: Float = 20f
        set(value) {
            field = value
            refreshView()

        }

    // ----------------------------------------------------------------------------------

    var cycleStart: Long = 0
        set(value) {
            field = value
            refreshView()

        }

    // ----------------------------------------------------------------------------------

    var cycleEnd: Long = 0
        set(value) {
            field = value
            refreshView()

        }

    // ----------------------------------------------------------------------------------

    var expiredAt: Long = 0
        set(value) {
            field = value
            refreshView()

        }
    // ----------------------------------------------------------------------------------

    var icon: Int? = null
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var iconMode: IconMode? = null
        set(value) {
            field = value
            value?.let {
                cardView.iconMode = it
            }
        }

    // ----------------------------------------------------------------------------------

    var isHideActionButton = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isActionButtonEnabled = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
            cardView.onActionButtonPress = value
        }

    // ----------------------------------------------------------------------------------

    var showExclamationMark = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onExclamationMarkClick: (() -> Unit)? = null
        set(value) {
            field = value
            cardView.onExclamationMarkClick = value
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
    // ----------------------------------------------------------------------------------

    val viewCardMain by lazy {
        cardView.viewCardMain
    }

    val smallCardViewChild by lazy {
        cardView.smallCardViewChild
    }

    val viewShimmerMain by lazy {
        cardView.viewShimmerMain
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dashboard_widget_balance, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DashboardWidgetBalanceAttr)
        typedArray.run {
            val typedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(defaultIcon, typedValue, true)
            cardView.icon = getResourceId(
                R.styleable.DashboardWidgetBalanceAttr_widgetIcon,
                typedValue.resourceId
            )
            remaining = (getInt(R.styleable.DashboardWidgetBalanceAttr_remaining, 0)).toLong()
            canceledAt = (getInt(R.styleable.DashboardWidgetBalanceAttr_canceledAt, 0)).toLong()
            expiredAt = (getInt(R.styleable.DashboardWidgetBalanceAttr_expiredAt, 0)).toLong()
            label = getString(R.styleable.DashboardWidgetBalanceAttr_label)
                ?: resources.getString(R.string.organism_balance_dashboard_widget_label)
            actionButtonLabel = getString(R.styleable.DashboardWidgetBalanceAttr_actionButtonLabel)
                ?: resources.getString(R.string.organism_balance_dashboard_widget_action_button_label)
            creditLimit = (getInt(R.styleable.DashboardWidgetBalanceAttr_creditLimit, 0)).toLong()
            cycleStart =
                (getInt(R.styleable.DashboardWidgetBalanceAttr_cycleStart, 0)).toLong() * 1000
            cycleEnd = (getInt(R.styleable.DashboardWidgetBalanceAttr_cycleEnd, 0)).toLong() * 1000
            mode = DashboardBalanceWidgetMode.values()[getInt(
                R.styleable.DashboardWidgetBalanceAttr_balanceWidgetMode,
                0
            )]
            isHideActionButton =
                getBoolean(R.styleable.DashboardWidgetBalanceAttr_isHideActionButton, false)
            isActionButtonEnabled =
                getBoolean(R.styleable.DashboardWidgetBalanceAttr_isActionButtonEnabled, true)
            showExclamationMark =
                getBoolean(R.styleable.DashboardWidgetBalanceAttr_showExclamationMark, false)
        }
        typedArray.recycle()
    }

    private fun refreshView() {
        setRemaining()
        setIcon()
        setLabel()
        setExclamationMark()
        setInformation()
        setActionButton()
        setDashboardWidgetMode()
    }

    private fun setRemaining() {
        cardView.value = context.getString(
            R.string.indonesian_rupiah_balance_remaining,
            ConverterUtil.convertDelimitedNumber(remaining, true)
        )
    }

    private fun setIcon() {
        // set icon
        icon?.let {
            val iconTypedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(it, iconTypedValue, true)
            cardView.icon = iconTypedValue.resourceId
        }
    }

    private fun setLabel() {
        // set label 
        cardView.label = label
    }

    private fun setExclamationMark() {
        // set exclamation mark 
        cardView.showExclamationMark = showExclamationMark
    }

    private fun setInformation() {
        // set information
        when {
            canceledAt in 1..7 -> {
                cardView.information = context.getString(
                    R.string.organism_balance_dashboard_widget_information_grace_over,
                    canceledAt
                )
            }
            canceledAt < 1 -> {
                cardView.information = context.getString(
                    R.string.organism_balance_dashboard_widget_information_grace_hour,
                    context.getString(R.string.organism_balance_dashboard_widget_information_grace_today)
                )
            }
            canceledAt > 7 -> {
                val today = Dates.today
                val canceled = today + canceledAt.toInt().days
                val canceledDate = canceled.toString("d MMMM yyyy")
                cardView.information = context.getString(
                    R.string.organism_balance_dashboard_widget_information_grace_until,
                    canceledDate
                )
            }
        }
//        cardView.information = information
        cardView.informationMode = informationMode
    }

    private fun setActionButton() {
        cardView.buttonLabel = actionButtonLabel
        cardView.isHideActionButton = isHideActionButton
        cardView.isActionButtonEnabled = isActionButtonEnabled
    }

    private fun setDashboardWidgetMode() {

        // new type button widget
        cardView.isSmallBalanceCard = isSmallBalanceCard

        // override data by mode 
        when (mode) {
            DashboardBalanceWidgetMode.DEFAULT -> {

                val today = Dates.today
                val grace = today + expiredAt.toInt().days
                val graceDate = grace.toString("d MMMM yyyy")

                when {
                    expiredAt >= 8 -> {
                        cardView.information = context.getString(
                            R.string.organism_balance_dashboard_widget_information_default,
                            graceDate
                        )
                        cardView.informationMode = InformationMode.DEFAULT
                    }
                    expiredAt > 0 -> {
                        cardView.information = context.getString(
                            R.string.organism_balance_dashboard_widget_information_grace_incoming,
                            expiredAt
                        )
                        cardView.informationMode = InformationMode.DANGER
                    }
                    else -> {
                        cardView.information = context.getString(
                            R.string.organism_balance_dashboard_widget_information_grace_until,
                            graceDate
                        )
                        cardView.informationMode = InformationMode.DANGER
                    }
                }
            }
            DashboardBalanceWidgetMode.POSTPAID -> {
                if (creditLimit == 0L) {
                    cardView.informationMode = InformationMode.NONE
                } else if (100f * remaining / creditLimit >= lowBalanceThreshold) {
                    cardView.information = context.getString(
                        R.string.organism_balance_dashboard_widget_information_credit_limit,
                        context.getString(
                            R.string.indonesian_rupiah_balance_remaining,
                            ConverterUtil.convertDelimitedNumber(creditLimit, true)
                        )
                    )
                    cardView.informationMode = InformationMode.DEFAULT
                } else if (remaining == 0L) {
                    cardView.information =
                        context.getString(R.string.organism_balance_dashboard_widget_information_empty_credit)
                    cardView.informationMode = InformationMode.DANGER
                } else {
                    cardView.information =
                        context.getString(R.string.organism_balance_dashboard_widget_information_low_credit)
                    cardView.informationMode = InformationMode.DANGER
                }

                val start = Calendar.getInstance()
                start.timeInMillis = cycleStart

                val end = Calendar.getInstance()
                end.timeInMillis = cycleEnd

                val dateFormat = SimpleDateFormat("d MMMM")

                var periode = dateFormat.format(end.time)
                if (start.get(Calendar.MONTH) == end.get(Calendar.MONTH)) {
                    periode = "" + start.get(Calendar.DATE) + " - " + periode
                } else {
                    periode = dateFormat.format(start.time) + " - " + periode
                }

                cardView.label = context.getString(
                    R.string.organism_balance_dashboard_widget_information_postpaid_credit,
                    periode
                )
                cardView.buttonLabel =
                    context.getString(R.string.organism_balance_dashboard_widget_information_adjust_credit_limit)
            }
            DashboardBalanceWidgetMode.PRIOFLEX -> {
                cardView.informationMode = InformationMode.NONE

                cardView.label =
                    context.getString(R.string.organism_balance_dashboard_widget_information_prio_flex)
                cardView.buttonLabel =
                    context.getString(R.string.organism_balance_dashboard_widget_information_prio_flex_top_up)

                val typedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(R.attr.prioFlexBalanceIcon, typedValue, true)
                cardView.icon = typedValue.resourceId
            }
            DashboardBalanceWidgetMode.DEPOSIT -> {
                cardView.informationMode = InformationMode.NONE

                cardView.label =
                    context.getString(R.string.organism_balance_dashboard_widget_deposit)
                cardView.buttonLabel =
                    context.getString(R.string.organism_balance_dashboard_widget_deposit_top_up)
            }
            DashboardBalanceWidgetMode.CUSTOM -> {
                // no restriction implementation
            }
        }
    }
}
