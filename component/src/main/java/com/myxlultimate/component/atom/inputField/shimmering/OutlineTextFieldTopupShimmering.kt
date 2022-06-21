package com.myxlultimate.component.atom.inputField.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.shimmering_welcome_header_toolbar.view.*

class OutlineTextFieldTopupShimmering @JvmOverloads constructor(
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
            .inflate(R.layout.shimmering_outline_text_field_topup, this, true)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.OutlineTextFieldTopupShimmering)
        typedArray.run {
            isShimmerOn = getBoolean(R.styleable.OutlineTextFieldTopupShimmering_isShimmerOn, false)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}