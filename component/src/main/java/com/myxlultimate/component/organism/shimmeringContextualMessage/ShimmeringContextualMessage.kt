package com.myxlultimate.component.organism.shimmeringContextualMessage

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.shimmering_contextual_message.view.*

class ShimmeringContextualMessage @JvmOverloads constructor(
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
            .inflate(R.layout.shimmering_contextual_message, this, true)

        // set ratio
        var layoutParamsRatio = containerView.layoutParams as ConstraintLayout.LayoutParams
        layoutParamsRatio.dimensionRatio = "H,8:3"

        isShimmerOn = false
    }
}