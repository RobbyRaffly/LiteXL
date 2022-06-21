package com.myxlultimate.component.template.segmentWidget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.component_white_sub_segment.view.*

class WhiteSubSegmentComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            titleViewBill.text = value
        }

    var subTitle = ""
        set(value) {
            field = value
            if (value != "") {
                titleViewBillPeriod.visibility = View.VISIBLE
            }
            titleViewBillPeriod.text = value
        }

    var useDarkBackground: Boolean = false
        set(value) {
            field = value
            if (value) subComponentContainer.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.mud_palette_basic_black
                )
            )
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.component_white_sub_segment, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WhiteSubSegmentComponent)
        typedArray.run {
            title = getString(R.styleable.WhiteSubSegmentComponent_title) ?: ""
            subTitle = getString(R.styleable.WhiteSubSegmentComponent_subtitle) ?: ""
            useDarkBackground = getBoolean(R.styleable.WhiteSubSegmentComponent_useDarkBackground, false)
        }
        typedArray.recycle()
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (itemContainerView == null) {
            super.addView(child, index, params)
        } else {
            itemContainerView.addView(child, index, params)
        }
    }

}