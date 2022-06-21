package com.myxlultimate.component.molecule.quotaDetail

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.FupLimitRule
import com.myxlultimate.component.enums.QuotaType
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.molecule_quota_detail_item.view.*

class QuotaDetailItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val name: String,
        val quotaType: QuotaType,
        val iconImage: String,
        val information: String,
        val remaining: Long,
        val total: Long,
        val isUnlimited: Boolean = false,
        val isFup: Boolean = false,
        val fupText: String = "",
        val fupLimitRule: FupLimitRule = FupLimitRule.NONE,
        val lowThreshold:Float = 10f,
        val disableDetailItem: Boolean? = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var disableDetailItem = false
        set(value) {
            field = value

            parentLayout.alpha = if (value) 0.5F else 1F
        }

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value

            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var quotaType = QuotaType.DATA

    var fupLimitRule = FupLimitRule.NONE

    // ----------------------------------------------------------------------------------

    var iconImage = ""
        set(value) {
            field = value

            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value

            informationView.text = value
            informationUnlimitedView.text = value
        }

    // ----------------------------------------------------------------------------------

    var isUnlimited = false
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var remaining: Long = 0
        set(value) {
            field = value

            remainingView.text = when (quotaType) {
                QuotaType.DATA -> {
                    val data = ConverterUtil.convertDataUnit(value.toFloat())
                    context.getString(R.string.quota_data_value, data.first, data.second)
                }
                QuotaType.TEXT -> {
                    resources.getString(R.string.quota_text_value, value)
                }
                QuotaType.VOICE -> {
                    val voice = ConverterUtil.convertVoice(value.toInt())
                    resources.getQuantityString(R.plurals.quota_voice_value, voice, voice)
                }
                QuotaType.FAMILY_SLOT -> {
                    resources.getQuantityString(R.plurals.quota_family_package_value, value.toInt(), value.toInt())
                }
                QuotaType.DEVICE -> {
                    resources.getQuantityString(R.plurals.quota_device_value, value.toInt(),value.toInt())
                }
                QuotaType.CUSTOM -> {
                    val voice = ConverterUtil.convertVoice(value.toInt())
                    resources.getQuantityString(R.plurals.quota_custom_value, voice, voice)
                }
                QuotaType.MONETARY -> {
                    value.toString()
                }
                QuotaType.PRICE, QuotaType.BALANCE -> {
                    resources.getString(
                        R.string.indonesian_rupiah_balance_remaining,
                        ConverterUtil.convertDelimitedNumber(value, true)
                    )
                }
            }

            percentage = if(total!=0L)
                100F * (value.toFloat() / total)
            else
                100f
        }

    // ----------------------------------------------------------------------------------

    var total: Long = 0
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    private var percentage = 0F

    var lowThreshold = 10f
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var isFup = false
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var fupText = ""
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        totalView.text = when (quotaType) {
            QuotaType.DATA -> {
                val data = ConverterUtil.convertDataUnit(total.toFloat())
                if (isFup) {
                    fupText + context.getString(R.string.quota_data_value, data.first, data.second) + fupLimitRule.title
                } else {
                    context.getString(R.string.quota_data_value, data.first, data.second)
                }
            }
            QuotaType.TEXT -> {
                if (isFup) {
                    fupText + resources.getString(R.string.quota_text_value, total) + fupLimitRule.title
                } else {
                    resources.getString(R.string.quota_text_value, total)
                }
            }
            QuotaType.VOICE -> {
                val voice = ConverterUtil.convertVoice(total.toInt())
                if (isFup) {
                    fupText + resources.getQuantityString(R.plurals.quota_voice_value, voice, voice) + fupLimitRule.title
                } else {
                    resources.getQuantityString(R.plurals.quota_voice_value, voice, voice)
                }
            }
            QuotaType.FAMILY_SLOT -> {
                if (isFup) {
                    fupText + resources.getQuantityString(R.plurals.quota_family_package_value, total.toInt(), total.toInt()) + fupLimitRule.title
                } else {
                    resources.getQuantityString(R.plurals.quota_family_package_value, total.toInt(), total.toInt())
                }
            }
            QuotaType.DEVICE -> {
                resources.getQuantityString(R.plurals.quota_device_value,total.toInt(),total.toInt())
            }
            QuotaType.CUSTOM -> {
                val voice = ConverterUtil.convertVoice(total.toInt())
                if (isFup) {
                    fupText + resources.getQuantityString(R.plurals.quota_custom_value, voice, voice) + fupLimitRule.title
                } else {
                    resources.getQuantityString(R.plurals.quota_custom_value, voice, voice)
                }
            }
            QuotaType.MONETARY -> {
                total.toString()
            }
            QuotaType.PRICE, QuotaType.BALANCE -> {
                resources.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(total, true)
                )
            }
        }

        percentage = if (remaining <= 0 && total <= 0)
            0f
        else if(total>0L)
            100F * (remaining / total.toFloat())
        else
            100f

        // condition percentage
        var layoutParams = progressView.layoutParams as LayoutParams
        layoutParams.weight = percentage
        progressView.layoutParams = layoutParams

        val spacePercentage = 100F - percentage
        layoutParams = progressSpacerView.layoutParams as LayoutParams
        layoutParams.weight = spacePercentage
        progressSpacerView.layoutParams = layoutParams

        if(percentage < lowThreshold){
            remainingView.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_red))
            progressView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.mud_palette_basic_red)
            if(isFup){
                informationView.setTextColor(ContextCompat.getColorStateList(context, R.color.mud_palette_basic_red))
            }else{
                informationView.setTextColor(ContextCompat.getColorStateList(context, R.color.mud_palette_basic_dark_grey))
            }
        } else {
            remainingView.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
            val typedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(R.attr.quotaWidgetColor, typedValue, true)
            progressView.backgroundTintList = ContextCompat.getColorStateList(context, typedValue.resourceId)
        }

        // condition unlimited
        informationView.visibility = if (!isUnlimited || isFup) View.VISIBLE else View.GONE
        remainingView.visibility = if (!isUnlimited) View.VISIBLE else View.GONE
        barContainerView.apply {
            alpha = if (!isUnlimited || isFup) 1F else 0F
            layoutParams = LayoutParams(
                layoutParams.width,
                if (!isUnlimited || isFup) LayoutParams.WRAP_CONTENT else 0
            )
            visibility = if (!isUnlimited || isFup) View.VISIBLE else View.GONE
        }

        informationUnlimitedView.visibility = if (isUnlimited && !isFup && informationUnlimitedView.text.isNotEmpty()) View.VISIBLE else View.GONE
        unlimitedFlagView.visibility = if (isUnlimited) View.VISIBLE else View.GONE
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_quota_detail_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuotaDetailItemAttr)
        typedArray.run {
            name = getString(R.styleable.QuotaDetailItemAttr_name) ?: ""
            quotaType = QuotaType.values()[getInt(R.styleable.QuotaDetailItemAttr_quotaType, 0)]
            iconImage = getString(R.styleable.QuotaDetailItemAttr_iconImage) ?: ""
            information = getString(R.styleable.QuotaDetailItemAttr_information) ?: ""
            remaining = getInt(R.styleable.QuotaDetailItemAttr_remaining,0).toLong()
            total = getInt(R.styleable.QuotaDetailItemAttr_total,0).toLong()
            isUnlimited = getBoolean(R.styleable.QuotaDetailItemAttr_isUnlimited, false)
            isFup = getBoolean(R.styleable.QuotaDetailItemAttr_isFup, false)
            fupText = getString(R.styleable.QuotaDetailItemAttr_fupText)?:""
            lowThreshold = getFloat(R.styleable.QuotaDetailItemAttr_lowThreshold,10f)
            disableDetailItem = getBoolean(R.styleable.QuotaDetailItemAttr_disableDetailItem,false)
        }
        typedArray.recycle()
    }
}