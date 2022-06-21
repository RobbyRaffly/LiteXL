package com.myxlultimate.component.organism.storeWidget.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.shimmering_package_family_item.view.*

class ShimmeringPackageFamilyItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data (
        val isShimmerOn : Boolean? = false
    )

    var isShimmerOn = false
        set(value) {
            field = value
            if(value) {
                shimmerLayout.startShimmer()

            } else {
                shimmerLayout.stopShimmer()
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_package_family_item, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShimmeringPackageFamilyItem)
        typedArray.run {
            isShimmerOn = getBoolean(R.styleable.ShimmeringStoreCardWidget_isShimmerOn,false)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}