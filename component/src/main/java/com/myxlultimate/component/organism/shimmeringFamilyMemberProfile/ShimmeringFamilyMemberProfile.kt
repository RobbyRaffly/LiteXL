package com.myxlultimate.component.organism.shimmeringFamilyMemberProfile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.shimmering_family_member_profile.view.*

class ShimmeringFamilyMemberProfile @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_family_member_profile, this, true)
        isShimmerOn = false
    }
}