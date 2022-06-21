package com.myxlultimate.component.organism.faq

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.faq.adapter.FaqPackageItemAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_faq_package_item_group.view.*

class FaqPackageItemGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<FaqPackageItem.Data>()
        set(value) {
            field = value
            faqPackageItemAdapter.items = value
        }

    var onItemPress: ((Int) -> Unit)? = null

    private val faqPackageItemAdapter by lazy {
        FaqPackageItemAdapter { index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_faq_package_item_group, this, true)

        rvfaqPackageItem.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 17))
            adapter = faqPackageItemAdapter.also {
                it.items = items
            }
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is FaqPackageItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                FaqPackageItem.Data(
                    child.iconDrawable,
                    child.title,
                    child.description
                )
            )
            faqPackageItemAdapter.items = items
        }
    }

}