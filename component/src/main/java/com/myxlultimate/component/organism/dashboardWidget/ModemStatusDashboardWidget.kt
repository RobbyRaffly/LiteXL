package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_dashboard_widget_modem_status.view.*
import kotlin.math.floor
import kotlin.math.roundToLong

class ModemStatusDashboardWidget @JvmOverloads constructor(
    context : Context,
    attrs : AttributeSet? = null
) : LinearLayout(context, attrs) {


    // ----------------------------------------------------------------------------------

    var isModemActive : Boolean? = null
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var label = ""
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

    // ----------------------------------------------------------------------------------

    var deviceInformation = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasBottomView = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onRefreshButtonClick : (() -> Unit)? = null
        set(value) {
            field = value
            refreshButton.setOnClickListener {
                value?.invoke()
                setButtonCountDown()
            }
        }

    // ----------------------------------------------------------------------------------

    var onRefreshButtonCountDownDone: (() -> Unit)? = null
        set(value) {
            field = value
        }
    // ----------------------------------------------------------------------------------

    var onSeeModemInformationClick : (() -> Unit)? = null
        set(value) {
            field = value
        }

    // ----------------------------------------------------------------------------------

    var lastOnlineNotice = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var setButtonText = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isRefreshButtonEnabled = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var bottomLabel = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasBottomCta = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var lastSession : Long = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var refreshButtonHasCountDown = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var modemRefresedAt : Long = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context).inflate(R.layout.organism_dashboard_widget_modem_status, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashboardWidgetModemStatusAttr)
        typedArray.run {
            isModemActive = getBoolean(R.styleable.DashboardWidgetModemStatusAttr_isModemActive, false)
            label = getString(R.styleable.DashboardWidgetModemStatusAttr_label) ?: ""
            information = getString(R.styleable.DashboardWidgetModemStatusAttr_information) ?: ""
            deviceInformation = getString(R.styleable.DashboardWidgetModemStatusAttr_deviceInformation) ?: ""
            hasBottomView = getBoolean(R.styleable.DashboardWidgetModemStatusAttr_hasBottomView, true)
            lastOnlineNotice = getString(R.styleable.DashboardWidgetModemStatusAttr_lastOnlineNotice) ?: ""
            setButtonText = getString(R.styleable.DashboardWidgetModemStatusAttr_buttonText) ?: ""
            bottomLabel = getString(R.styleable.DashboardWidgetModemStatusAttr_bottomLabel) ?: ""
            isRefreshButtonEnabled = getBoolean(R.styleable.DashboardWidgetModemStatusAttr_isPrimaryButtonEnabled, true)
            hasBottomCta = getBoolean(R.styleable.DashboardWidgetModemStatusAttr_hasBottomCta, true)
            lastSession = getInt(R.styleable.DashboardWidgetModemStatusAttr_lastSession, 0).toLong()
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(seeModemInformation) {
            onSeeModemInformationClick?.invoke()
        }

    }

    fun refreshView(){
        when (isModemActive) {
            true -> {
                statusIndicator.background = ColorDrawable(ContextCompat.getColor(context, R.color.mud_package_status_green))
                statusIndicatorTextView.visibility = View.GONE
                status.text = resources.getString(R.string.modem_status_on)
                status.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
                lastOnlineNoticeContainer.visibility = View.VISIBLE
            }
            false -> {
                statusIndicator.background = ColorDrawable(ContextCompat.getColor(context, R.color.mud_palette_basic_red))
                status.text = resources.getString(R.string.modem_status_off)
                statusIndicatorTextView.visibility = View.VISIBLE
                lastOnlineNoticeContainer.visibility = View.VISIBLE
                status.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
            }
            else -> {
                statusIndicator.background = ColorDrawable(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
                status.text = resources.getString(R.string.modem_status_failed)
                status.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
                statusIndicatorTextView.visibility = View.VISIBLE
                lastOnlineNoticeContainer.visibility = View.GONE
                connectedDevices.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
            }
        }

        if (lastSession > 0L) setLastSession()
        else lastOnlineNoticeView.text = lastOnlineNotice

        IsEmptyUtil.setVisibility(hasBottomCta, seeModemInformation)
        IsEmptyUtil.setVisibility(hasBottomCta, bottomLineView)
        if (hasBottomCta) {
            connectedDevices.updatePadding(bottom = 16)
        }

        connectedDevices.text = if (isModemActive == null) deviceInformation else resources.getString(R.string.modem_connected_device_counted, deviceInformation)
        textLabel.text = label
        statusIndicatorTextView.text = information
        refreshButton.text = setButtonText
        if (!isRefreshButtonEnabled) refreshButton.isEnabled = false
        bottomContainerView.visibility = if (hasBottomView) View.VISIBLE else View.GONE
    }



    private fun setButtonCountDown() {
        val currentTimeStamp = System.currentTimeMillis() / 1000
        val timeDiffs = modemRefresedAt + 30 - currentTimeStamp

        if (modemRefresedAt > 0 && refreshButtonHasCountDown && timeDiffs <= 30 && timeDiffs > 0){
            refreshButton.isEnabled = false
            val timer = object : CountDownTimer(timeDiffs * 1000, 1000) {
                override fun onTick(p0: Long) {
                    setButtonText = "Refresh Status Modem (${floor(p0.toDouble() / 1000).toLong()}s)"
                }

                override fun onFinish() {
                    refreshButton.isEnabled = true
                    setButtonText = "Refresh Status Modem"
                    onRefreshButtonCountDownDone?.invoke()
                }

            }

            timer.start()
        }
    }

    private fun setLastSession() {

        val currentTimestamp = System.currentTimeMillis() / 1000
        val timeDiffs = currentTimestamp - lastSession
        Log.d("ANGGA", "$timeDiffs")
        lastOnlineNoticeView.text = when {
            timeDiffs < 60 -> resources.getString(R.string.modem_just_refreshed)
            getMinutes(timeDiffs) < 60 -> resources.getString(R.string.modem_last_refresh_minutes, getMinutes(timeDiffs))
            getHours(timeDiffs) < 24 -> resources.getString(R.string.modem_last_refresh_hours, getHours(timeDiffs))
            getDays(timeDiffs) < 30 -> resources.getString(R.string.modem_last_refresh_days, getDays(timeDiffs))
            getWeeks(timeDiffs) < 4 -> resources.getString(R.string.modem_last_refresh_weeks, getWeeks(timeDiffs))
            getMonths(timeDiffs) < 12 -> resources.getString(R.string.modem_last_refresh_months, getMonths(timeDiffs))
            else -> resources.getString(R.string.modem_last_refresh_years, getYears(timeDiffs))
        }
    }



    private fun getMinutes(timeDiffs: Long) : Long {
        return floor(timeDiffs.toDouble() / 60).toLong()
    }

    private fun getHours(timeDiffs: Long) : Long {
        return floor(timeDiffs.toDouble() / 3600).toLong()
    }

    private fun getDays(timeDiffs:Long) : Long {
        return floor(timeDiffs.toDouble() / 86400).toLong()
    }

    private fun getWeeks(timeDiffs: Long) : Long {
        return floor(timeDiffs.toDouble() / 604800).toLong()
    }

    private fun getMonths(timeDiffs: Long) : Long {
        return floor(timeDiffs.toDouble() / 2629743).toLong()
    }

    private fun getYears(timeDiffs: Long) : Long {
        return floor(timeDiffs.toDouble() / 31556926).toLong()
    }



}
