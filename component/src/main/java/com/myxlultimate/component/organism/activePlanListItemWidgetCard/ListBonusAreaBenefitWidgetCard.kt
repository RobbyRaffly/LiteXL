package com.myxlultimate.component.organism.activePlanListItemWidgetCard

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem
import com.myxlultimate.component.molecule.packageBenefit.enums.BenefitMode
import com.myxlultimate.component.organism.activePlanListItemWidgetCard.adapter.BonusAreaBenefitWidgetAdapter
import kotlinx.android.synthetic.main.organism_list_bonus_area_benefit_widget_card.view.*

class ListBonusAreaBenefitWidgetCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    // ----------------------------------------------------------------------------------

    var items = mutableListOf<PackageBenefitItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        BonusAreaBenefitWidgetAdapter()
    }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_list_bonus_area_benefit_widget_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TransactionHistoryWidget)
        typedArray.recycle()

        bonusAreaBenefitWidgetRecycleView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            adapter = recycleAdapter.also { it.items = items }
        }
    }

}