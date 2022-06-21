package com.myxlultimate.component.organism.benefitComparisonCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaType
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem

class BenefitComparisonCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val currentBenefit: PackageBenefitItem by lazy { findViewById<PackageBenefitItem>(R.id.currentBenefit) }
    val newBenefit: PackageBenefitItem by lazy { findViewById<PackageBenefitItem>(R.id.newBenefit) }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var currentBenefitImage = ""
        set(value) {
            field = value
            currentBenefit.iconImage = value
        }

    // ----------------------------------------------------------------------------------

    var currentBenefitName = ""
        set(value) {
            field = value
            currentBenefit.name = value
        }

    // ----------------------------------------------------------------------------------

    var currentBenefitInformation = ""
        set(value) {
            field = value
            currentBenefit.information = value
        }

    // ----------------------------------------------------------------------------------

    var currentBenefitUnlimitedStatus = false
        set(value) {
            field = value
            currentBenefit.isUnlimited = value
        }

    // ----------------------------------------------------------------------------------

    var currentBenefitQuotaType = QuotaType.DATA
        set(value) {
            field = value
            currentBenefit.quotaType = value
        }

    // ----------------------------------------------------------------------------------

    var newBenefitImage = ""
        set(value) {
            field = value
            newBenefit.iconImage = value
        }

    // ----------------------------------------------------------------------------------

    var newBenefitName = ""
        set(value) {
            field = value
            newBenefit.name = value
        }

    // ----------------------------------------------------------------------------------

    var newBenefitInformation = ""
        set(value) {
            field = value
            newBenefit.information = value
        }

    // ----------------------------------------------------------------------------------

    var newBenefitUnlimitedStatus = false
        set(value) {
            field = value
            newBenefit.isUnlimited = value
        }

    // ----------------------------------------------------------------------------------

    var newBenefitQuotaType = QuotaType.DATA
        set(value) {
            field = value
            newBenefit.quotaType = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_benefit_comparison_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BenefitComparisonCard)
        typedArray.run {
            currentBenefitImage =
                getString(R.styleable.BenefitComparisonCard_currentBenefitImage) ?: ""
            currentBenefitName =
                getString(R.styleable.BenefitComparisonCard_currentBenefitName) ?: ""
            currentBenefitInformation =
                getString(R.styleable.BenefitComparisonCard_currentBenefitInformation) ?: ""
            currentBenefitUnlimitedStatus =
                getBoolean(R.styleable.BenefitComparisonCard_currentBenefitUnlimitedStatus, false)
            currentBenefitQuotaType = QuotaType.values()[getInt(
                R.styleable.BenefitComparisonCard_currentBenefitQuotaType,
                0
            )]
            newBenefitImage = getString(R.styleable.BenefitComparisonCard_newBenefitImage) ?: ""
            newBenefitName = getString(R.styleable.BenefitComparisonCard_newBenefitName) ?: ""
            newBenefitInformation =
                getString(R.styleable.BenefitComparisonCard_newBenefitInformation) ?: ""
            newBenefitUnlimitedStatus =
                getBoolean(R.styleable.BenefitComparisonCard_newBenefitUnlimitedStatus, false)
            newBenefitQuotaType =
                QuotaType.values()[getInt(R.styleable.BenefitComparisonCard_newBenefitQuotaType, 0)]
        }
        typedArray.recycle()

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}