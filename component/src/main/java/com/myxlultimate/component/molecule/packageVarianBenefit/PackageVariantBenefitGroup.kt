package com.myxlultimate.component.molecule.packageVarianBenefit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.packageVarianBenefit.adapters.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.molecule_package_variant_benefit_group.view.*

class PackageVariantBenefitGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val name: String,
        val benefits: MutableList<PackageVariantBenefitItem.Data>,
        val isShimmerOn: Boolean = false
    )

    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    
    var name = ""
    
    // -----------------------------------------------------------------------------

    var benefits = mutableListOf<PackageVariantBenefitItem.Data>()
        set(value) {
            field = value

            recycleAdapter.items = value
        }

    // -----------------------------------------------------------------------------

    private val recycleAdapter by lazy { RecycleViewAdapter() }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_package_variant_benefit_group, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PackageVariantBenefitGroupAttr)
        typedArray.run {
            name = typedArray.getString(R.styleable.PackageVariantBenefitGroupAttr_name) ?: ""
        }
        typedArray.recycle()

        itemContainerView.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = benefits }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is PackageVariantBenefitItem) {
            super.addView(child, index, params)
        } else {
            benefits.add(
                PackageVariantBenefitItem.Data(
                    child.iconImage,
                    child.name,
                    child.isShimmerOn
                ))
            recycleAdapter.items = benefits
        }
    }
}