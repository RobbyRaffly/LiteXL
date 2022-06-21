package com.myxlultimate.component.organism.notificationCard.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.shimmering_notification_date_item.view.*

class ShimmeringNotificationDateItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                shimmeringLayout.startShimmer()

            } else {
                shimmeringLayout.stopShimmer()
            }
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_notification_date_item, this, true)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ShimmeringNotificationDateItem)
        typedArray.run {
            isShimmerOn = getBoolean(R.styleable.ShimmeringNotificationDateItem_isShimmerOn, false)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}