package com.myxlultimate.component.atom.termAndConditionLine

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.atom_terms_and_condition_line.view.*

class TermsAndConditionLine @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value

            titleView.text = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.atom_terms_and_condition_line, this, true)

        orientation = HORIZONTAL

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TermsAndConditionLineAttr)
        typedArray.run {
            title = getString(R.styleable.TermsAndConditionLineAttr_title) ?: ""
        }
        typedArray.recycle()
    }
}