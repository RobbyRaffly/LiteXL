package com.myxlultimate.component.token.icon.base

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
        override fun setClickListener(iconView: TextView, onClickListener: (() -> Unit)?) {
            TouchFeedbackUtil.detach(iconView)
        }
    };

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun build(name: String?): Mode {
        return values().firstOrNull { it.name == name } ?: this
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    open fun setClickListener(iconView: TextView, onClickListener: (() -> Unit)?) {
        if ((onClickListener == null)) {
            return Unit
        } else {
            TouchFeedbackUtil.attach(iconView, onClickListener)
        }
    }

    // ----------------------------------------------------------------------------------

    open fun setColor(iconView: TextView, context: Context, color: Int = defaultColor) {
        if (color != 0) {
            iconView.setTextColor(ContextCompat.getColor(context, color))
        }
    }
}