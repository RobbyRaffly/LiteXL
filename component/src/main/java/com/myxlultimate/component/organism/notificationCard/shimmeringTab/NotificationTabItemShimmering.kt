package com.myxlultimate.component.organism.notificationCard.shimmeringTab

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.shimmering_tab_menu.view.*

class NotificationTabItemShimmering @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val isShimmerOn: Boolean
    )

    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.notification_tab_shimmer_item, this, true)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.NotificationTabItemShimmering)
        typedArray.run {
            isShimmerOn = getBoolean(R.styleable.NotificationTabItemShimmering_isShimmerOn, false)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


}