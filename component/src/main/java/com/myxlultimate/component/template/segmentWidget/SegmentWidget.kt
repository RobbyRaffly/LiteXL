package com.myxlultimate.component.template.segmentWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.template_segment_widget.view.*

class SegmentWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var label = ""
        set(value) {
            field = value

            labelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var hasViewAllButton = true
        set(value) {
            field = value

            viewAllButtonView.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var removeShadow = false
    set(value) {
        field = value
        if(removeShadow) {
            cardView.cardElevation = 0f
        }
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
            shimmerView.visibility = if(value) View.VISIBLE else View.GONE
            titleView.visibility = if(value) View.GONE else View.VISIBLE
        }

    // ----------------------------------------------------------------------------------

    var onViewAllButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(viewAllButtonView, value)
        }

    // ----------------------------------------------------------------------------------

    var hasLines = true
    set(value) {
        field = value

    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_segment_widget, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SegmentWidgetAttr)
        typedArray.run {
            label = getString(R.styleable.SegmentWidgetAttr_label) ?: ""
            hasViewAllButton = getBoolean(R.styleable.SegmentWidgetAttr_hasViewAllButton, true)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(viewAllButtonView, onViewAllButtonPress)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (mainView == null) {
            super.addView(child, index, params)
        } else {
            mainView.addView(child, index, params)
        }
    }

}