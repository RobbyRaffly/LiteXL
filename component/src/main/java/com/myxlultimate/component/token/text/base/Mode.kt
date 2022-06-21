package com.myxlultimate.component.token.text.base

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil

enum class Mode(
  val defaultColor: Int
) {
    LIGHT(R.color.basicBlack),
    DARK(R.color.basicWhite),
    DISABLED(R.color.basicDarkGrey) {
        override fun setClickListener(textView: TextView, onClickListener: (() -> Unit)?, alwaysClickable: Boolean) {
            TouchFeedbackUtil.detach(textView)
        }

        override fun setColor(textView: TextView, context: Context, color: Int) {
            textView.setTextColor(ContextCompat.getColor(context, defaultColor))
        }
    };

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun build(name: String?): Mode {
        return values().firstOrNull { it.name == name } ?: this
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    open fun setClickListener(textView: TextView, onClickListener: (() -> Unit)?, alwaysClickable: Boolean) {
        if ((onClickListener == null && !alwaysClickable)) {
            return Unit
        } else {
            TouchFeedbackUtil.attach(textView, onClickListener)
        }
    }

    // ----------------------------------------------------------------------------------

    open fun setColor(textView: TextView, context: Context, color: Int = defaultColor) {
        if (color != 0) {
            textView.setTextColor(ContextCompat.getColor(context, color))
        }
    }
}