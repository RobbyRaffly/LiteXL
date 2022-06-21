package com.myxlultimate.component.template.quatreModal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.template_quatre_full_modal.view.*

class QuatreModal @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value

            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var icon = ""
        set(value) {
            field = value

            iconView.text = value
            iconView.top
        }

    // ----------------------------------------------------------------------------------

    var onIconPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(iconView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_quatre_full_modal, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.quatreModalAttr)
        attrs.let {
            title = typedArray.getString(R.styleable.quatreModalAttr_title) ?: ""
            icon = typedArray.getString(R.styleable.quatreModalAttr_iconCode) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(iconView, onIconPress)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (containerView == null) {
            super.addView(child, index, params)
        } else {
            containerView.addView(child, index, params)
        }
    }
}