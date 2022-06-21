package com.myxlultimate.component.molecule.quotaDetail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.databinding.OrganismQuotaDetailRowSummaryGroupBinding
import com.myxlultimate.component.molecule.quotaDetail.adapters.QuotaDetailRowSummaryAdapter
import com.myxlultimate.component.util.ListUtil

class QuotaDetailRowSummaryGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val binding = OrganismQuotaDetailRowSummaryGroupBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    // ----------------------------------------------------------------------------------

    var items = mutableListOf<QuotaDetailRowSummaryWidget.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        QuotaDetailRowSummaryAdapter()
    }

    // ----------------------------------------------------------------------------------

    init {
        binding.containerList.apply {
            adapter = recycleAdapter.also {
                it.items = items
            }
        }
    }


    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is QuotaDetailRowSummaryWidget) {
            super.addView(child, index, params)
        } else {
            items.add(
                QuotaDetailRowSummaryWidget.Data(
                    child.title,
                    child.subTitle,
                    child.imageSourceType,
                    child.imageSource,
                    child.imageSourceTypeIcon,
                    child.imageSourceIcon
                )
            )
            recycleAdapter.items = items
        }
    }
}