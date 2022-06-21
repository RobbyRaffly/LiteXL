package com.myxlultimate.component.organism.quickMenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.quick_menu_shimmer_group.view.*

class QuickMenuShimmer @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isShimmserOn = false
        set(value) {
            field = value
            if (value) {
                parentSkeletonlayout.startShimmer()
            } else {
                parentSkeletonlayout.stopShimmer()
            }
            parentSkeletonlayout.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.quick_menu_shimmer_group, this, true)
        isShimmserOn = false
    }

}
