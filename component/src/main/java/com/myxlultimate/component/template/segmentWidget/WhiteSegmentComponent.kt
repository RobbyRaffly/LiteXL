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
import kotlinx.android.synthetic.main.component_segment_white.view.*

class WhiteSegmentComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value

            titleView.text = value
        }

    var withoutPadding = false
        set(value) {
            field = value
            if (value) {
                wrapperView.setPadding(0, 6, 0, 6)
            }

        }

    var useDarkBackground: Boolean = false
        set(value) {
            field = value

            if (value) containerView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.mud_palette_basic_black
                )
            )
        }


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.component_segment_white, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WhiteSubSegmentComponent)
        typedArray.run {
            title = getString(R.styleable.WhiteSubSegmentComponent_title) ?: ""
            withoutPadding = getBoolean(R.styleable.WhiteSubSegmentComponent_withoutPadding, false)
            useDarkBackground = getBoolean(R.styleable.WhiteSubSegmentComponent_useDarkBackground, false)
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (itemContainerView == null) {
            super.addView(child, index, params)
        } else {
            itemContainerView.addView(child, index, params)
        }
    }

}