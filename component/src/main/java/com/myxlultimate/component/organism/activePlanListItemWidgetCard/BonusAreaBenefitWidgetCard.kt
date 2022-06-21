package com.myxlultimate.component.organism.activePlanListItemWidgetCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaType
import kotlinx.android.synthetic.main.organism_bonus_area_benefit_widget_card.view.*

class BonusAreaBenefitWidgetCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs)  {

    data class Data(
        val name: String,
        val iconImage: String,
        val information: String,
        val quotaType: QuotaType = QuotaType.DATA,
        val amount: Long,
        val isUnlimited: Boolean,
        val isShimmerOn: Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    var name = ""
        set(value) {
            field = value
            benefitItem.name = value
        }

    var iconImage = ""
        set(value) {
            field = value
            benefitItem.iconImage = value
        }

    var information = ""
        set(value) {
            field = value
            benefitItem.information = value
        }

    var quotaType = QuotaType.DATA
        set(value) {
            field = value
            benefitItem.quotaType = quotaType
        }

    var amount = 0L
        set(value) {
            field = value
            benefitItem.amount = amount
        }

    var isUnlimited = false
        set(value) {
            field = value
            benefitItem.isUnlimited
        }

    var isShimmerOn = false
        set(value) {
            field = value
            benefitItem.isShimmerOn
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_bonus_area_benefit_widget_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BonusAreaBenefitWidgetCard)
        typedArray.run {
            name = typedArray.getString(R.styleable.BonusAreaBenefitWidgetCard_name) ?: ""
            iconImage = typedArray.getString(R.styleable.BonusAreaBenefitWidgetCard_iconImage) ?: ""
            information = typedArray.getString(R.styleable.BonusAreaBenefitWidgetCard_information) ?: ""
            quotaType = QuotaType.values()[getInt(R.styleable.BonusAreaBenefitWidgetCard_quotaType, 0)]
            amount = getInt(R.styleable.BonusAreaBenefitWidgetCard_amount, 0).toLong()
            isUnlimited = getBoolean(R.styleable.BonusAreaBenefitWidgetCard_isUnlimited, false)
            isShimmerOn = getBoolean(R.styleable.BonusAreaBenefitWidgetCard_isShimmerOn, false)
        }
        typedArray.recycle()

    }
}