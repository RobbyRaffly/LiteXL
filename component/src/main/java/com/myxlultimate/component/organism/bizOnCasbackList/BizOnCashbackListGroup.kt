package com.myxlultimate.component.organism.bizOnCasbackList

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaType
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem
import com.myxlultimate.component.molecule.packageBenefit.enums.BenefitMode
import com.myxlultimate.component.organism.bizOnCasbackList.adapter.BizonItemCashbackInfoAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_bizon_cashback.view.*

class BizOnCashbackListGroup@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            setRefreshView()
        }

    var level = ""
        set(value) {
            field = value
            setRefreshView()
        }

    var items = mutableListOf<PackageBenefitItem.Data>()
        set(value) {
            field = value
            bizonItemCashbackInfoAdapter.items = value
        }

    var onItemPress: ((Int) -> Unit)? = null

    private val bizonItemCashbackInfoAdapter by lazy {
        BizonItemCashbackInfoAdapter()
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_bizon_cashback, this, true)

        rv_cashback.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = bizonItemCashbackInfoAdapter.also {
                it.items = items
            }
        }
    }

    private fun setRefreshView() {
        titleView.text = title
        levelView.text = resources.getString(R.string.xl_bizon_level_title, level)

    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is PackageBenefitItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                PackageBenefitItem.Data(
                    amount = child.amount,
                    iconImage = child.iconImage,
                    information = child.information,
                    name = child.name,
                    labelCta = resources.getString(R.string.xl_bizon_title_cta),
                    benefitMode = BenefitMode.WITHCTA,
                    quotaType = QuotaType.PRICE,
                    isUnlimited = false
                )
            )
            bizonItemCashbackInfoAdapter.items = items
        }
    }
}