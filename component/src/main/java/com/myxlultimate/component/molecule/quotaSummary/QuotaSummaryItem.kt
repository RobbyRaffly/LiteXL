package com.myxlultimate.component.molecule.quotaSummary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.FupLimitRule
import com.myxlultimate.component.enums.QuotaType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_quota_summary_item.view.*

class QuotaSummaryItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isUnlimited = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var quotaType = QuotaType.DATA
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasQuota = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var remaining = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var total = 1
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var fupRemaining = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var fupTotal = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasUnlimited = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasAddButton = false
        set(value) {
            field = value
            addButtonView.visibility = if (value) View.VISIBLE else View.GONE
        }


    // ----------------------------------------------------------------------------------

    var isIgnoreLowQuota = false
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------

    var onAddButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(addButtonView, value)
        }

    // ----------------------------------------------------------------------------------

    var isDisabled = false
        set(value) {
            field = value
            if (isDisabled) {
                val color = getColor(context, R.color.mud_palette_basic_dark_grey)
                iconView.setTextColor(color)
                remainingView.setTextColor(color)
            }
        }

    // ----------------------------------------------------------------------------------
    // TODO: Disabled for temporary for 5.1.6 hotfix
    var percentage = 100F
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var fupLimitRule = FupLimitRule.NONE

    var lowQuotaPercent = 0.2F

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(addButtonView, value)
        }
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_quota_summary_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuotaSummaryItemAttr)
        typedArray.run {
            hasAddButton = getBoolean(R.styleable.QuotaSummaryItemAttr_hasAddButton, false)
            lowQuotaPercent = getFloat(R.styleable.QuotaSummaryItemAttr_lowQuotaPercent, 0.2F)
            quotaType = QuotaType.values()[getInt(R.styleable.QuotaSummaryItemAttr_quotaType, 0)]
            fupLimitRule = FupLimitRule.values()[getInt(R.styleable.QuotaSummaryItemAttr_fupLimitRule, 0)]
            total = getInt(R.styleable.QuotaSummaryItemAttr_total, 1)
            remaining = getInt(R.styleable.QuotaSummaryItemAttr_remaining, 0)
            fupTotal = getInt(R.styleable.QuotaSummaryItemAttr_fupTotal, 0)
            fupRemaining = getInt(R.styleable.QuotaSummaryItemAttr_fupRemaining, 0)
            hasUnlimited = getBoolean(R.styleable.QuotaSummaryItemAttr_hasUnlimited, false)
            percentage = (remaining.toFloat() / total.toFloat()) * 100
            hasQuota = getBoolean(R.styleable.QuotaSummaryItemAttr_hasQuota, true)
            isUnlimited = getBoolean(R.styleable.QuotaSummaryItemAttr_isUnlimited, false)
            isIgnoreLowQuota = getBoolean(R.styleable.QuotaSummaryItemAttr_isIgnoreLowQuota, false)

        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(addButtonView, onActionButtonPress)
    }

    private fun refreshView() {
        if (!hasQuota) {
            remainingView.text = "-"
            hasAddButton = false
            unlimitedFlagView.visibility = View.GONE
            remainingView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
        } else {
            val isFup = fupTotal>0


            if(isFup){

                var finalRemaining = fupRemaining
                var finalTotal = fupTotal
                val isShowFupPrefix = fupRemaining>0


                unlimitedFlagView.visibility = GONE
                fromFUPView.visibility = VISIBLE

                if(fupRemaining<=0 && total>0){
                    finalRemaining = remaining
                    finalTotal = total

                    if(remaining>0){
                        fromFUPView.visibility = GONE
                        unlimitedFlagView.visibility = VISIBLE
                    }

                }

                var remainingText = when (quotaType) {
                    QuotaType.DATA -> {
                        val data = ConverterUtil.convertDataUnit(finalRemaining.toFloat())
                        context.getString(
                            R.string.quota_data_value,
                            data.first,
                            data.second
                        )
                    }
                    QuotaType.TEXT -> {
                        resources.getString(R.string.quota_text_value, finalRemaining)
                    }
                    QuotaType.VOICE -> {
                        val voice = ConverterUtil.convertVoice(finalRemaining)
                        resources.getQuantityString(R.plurals.quota_voice_value, voice, voice)
                    }
                    QuotaType.FAMILY_SLOT -> {
                        resources.getQuantityString(R.plurals.quota_family_package_value, remaining, finalRemaining)
                    }
                    QuotaType.DEVICE -> {
                        resources.getQuantityString(R.plurals.quota_device_value,finalRemaining,finalRemaining)
                    }
                    QuotaType.CUSTOM -> {
                        val voice = ConverterUtil.convertVoice(finalRemaining)
                        resources.getQuantityString(R.plurals.quota_custom_value, voice, voice)
                    }
                    QuotaType.MONETARY -> {
                        finalRemaining.toString()
                    }
                    QuotaType.PRICE, QuotaType.BALANCE -> {
                        resources.getString(
                            R.string.indonesian_rupiah_balance_remaining,
                            ConverterUtil.convertDelimitedNumber(finalRemaining.toLong(), true)
                        )
                    }
                }

                if(isShowFupPrefix)
                    remainingText = "FUP "+remainingText + " ${fupLimitRule.title}"
                remainingView.text = remainingText

                val pct = finalRemaining.toFloat()/finalTotal*100f

                hasAddButton = if (pct < (lowQuotaPercent * 100F) && finalTotal >= 0F) {
                    remainingView.setTextColor(getColor(context, R.color.mud_palette_basic_red))
                    true
                } else {
                    remainingView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                    false
                }


            }else if (isUnlimited) {
                remainingView.text = context.getString(R.string.quota_data_unlimited)
                remainingView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                hasAddButton = false
                unlimitedFlagView.visibility = GONE
            } else {
                remainingView.text = when (quotaType) {
                    QuotaType.DATA -> {
                        val data = ConverterUtil.convertDataUnit(remaining.toFloat())
                        context.getString(
                            R.string.quota_data_value,
                            data.first,
                            data.second
                        )
                    }
                    QuotaType.TEXT -> {
                        resources.getString(R.string.quota_text_value, remaining)
                    }
                    QuotaType.VOICE -> {
                        val voice = ConverterUtil.convertVoice(remaining)
                        resources.getQuantityString(R.plurals.quota_voice_value, voice, voice)
                    }
                    QuotaType.FAMILY_SLOT -> {
                        resources.getQuantityString(R.plurals.quota_family_package_value, remaining, remaining)
                    }
                    QuotaType.DEVICE -> {
                        resources.getQuantityString(R.plurals.quota_device_value,remaining,remaining)
                    }
                    QuotaType.CUSTOM -> {
                        val voice = ConverterUtil.convertVoice(remaining)
                        resources.getQuantityString(R.plurals.quota_custom_value, voice, voice)
                    }
                    QuotaType.MONETARY -> {
                        remaining.toString()
                    }
                    QuotaType.PRICE, QuotaType.BALANCE-> {
                        resources.getString(
                            R.string.indonesian_rupiah_balance_remaining,
                            ConverterUtil.convertDelimitedNumber(remaining.toLong(), true)
                        )
                    }
                }

                if(!isIgnoreLowQuota) {
                    hasAddButton = if (percentage < (lowQuotaPercent * 100F) && percentage >= 0F) {
                        remainingView.setTextColor(getColor(context, R.color.mud_palette_basic_red))
                        true
                    } else {
                        remainingView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                        false
                    }
                }else{
                    remainingView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                    hasAddButton = false
                }

                unlimitedFlagView.visibility = if (hasUnlimited) View.VISIBLE else View.GONE
            }
        }

        iconView.text = context.getString(quotaType.icon)


    }
}
