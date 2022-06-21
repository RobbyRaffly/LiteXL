package com.myxlultimate.component.molecule.packageVarianBenefit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.molecule_balance_widget.view.*
import kotlinx.android.synthetic.main.molecule_package_variant_benefit_item.view.*
import kotlinx.android.synthetic.main.molecule_package_variant_benefit_item.view.containerView
import kotlinx.android.synthetic.main.molecule_package_variant_benefit_item.view.shimmerLayout

class PackageVariantBenefitItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val iconImage: String,
        val name: String,
        val isShimmerOn: Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var iconImage = ""
        set(value) {
            field = value
            iconView.imageSource = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value
            if (value.isNotEmpty()){
                nameView.text = value
            }
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        nameView.visibility = if (name.isNotEmpty() && !isShimmerOn) View.VISIBLE else View.GONE
        iconView.visibility = if (iconImage.isNotEmpty() && !isShimmerOn) View.VISIBLE else View.GONE
        shimmerLayout.visibility = if (isShimmerOn) View.VISIBLE else View.GONE
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_package_variant_benefit_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PackageVariantBenefitItemAttr)
        typedArray.run {
            iconImage = typedArray.getString(R.styleable.PackageVariantBenefitItemAttr_iconImage) ?: ""
            name = typedArray.getString(R.styleable.PackageVariantBenefitItemAttr_name) ?: ""
            isShimmerOn = typedArray.getBoolean(R.styleable.PackageVariantBenefitItemAttr_isShimmerOn, false)
        }
        typedArray.recycle()
    }

}