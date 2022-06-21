package com.myxlultimate.component.organism.benefitComparisonCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_calculation_new_plan_card.view.*

class CalculationNewPlanCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var titleRefunded = ""
        set(value) {
            field = value
            tvTitleRefunded.text = value
        }

    // ----------------------------------------------------------------------------------

    var refundedAmount = ""
        set(value) {
            field = value
            tvAmountRefunded.text = value
        }

    // ----------------------------------------------------------------------------------

    var titleBalance = ""
        set(value) {
            field = value
            tvTitleBalance.text = value
        }

    // ----------------------------------------------------------------------------------

    var balanceAmount = ""
        set(value) {
            field = value
            tvAmountBalance.text = value
        }

    // ----------------------------------------------------------------------------------

    var titleTotalBalance = ""
        set(value) {
            field = value
            tvTitleTotalBalance.text = value
        }

    // ----------------------------------------------------------------------------------

    var totalBalance = ""
        set(value) {
            field = value
            tvAmountTotalBalance.text = value
        }

    // ----------------------------------------------------------------------------------

    var informationTitle = ""
        set(value) {
            field = value
            informationWithWarning.title = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_calculation_new_plan_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CalculationNewPlanCard)
        typedArray.run {
            titleRefunded = getString(R.styleable.CalculationNewPlanCard_titleRefunded) ?: ""
            refundedAmount = getString(R.styleable.CalculationNewPlanCard_refundedAmount) ?: ""
            titleBalance = getString(R.styleable.CalculationNewPlanCard_titleBalance) ?: ""
            balanceAmount = getString(R.styleable.CalculationNewPlanCard_balanceAmount) ?: ""
            titleTotalBalance = getString(R.styleable.CalculationNewPlanCard_titleTotalBalance) ?: ""
            totalBalance = getString(R.styleable.CalculationNewPlanCard_totalBalance) ?: ""
            informationTitle = getString(R.styleable.CalculationNewPlanCard_informationTitle) ?: ""
        }
        typedArray.recycle()

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}