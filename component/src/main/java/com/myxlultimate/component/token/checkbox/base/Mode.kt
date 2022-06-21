package com.myxlultimate.component.token.checkbox.base

import android.content.Context
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import com.myxlultimate.component.token.icon.base.Base

enum class Mode(
    val backgroundColor: Int,
    val checkmarkColor: Int
) {
    LIGHT(R.color.primaryBlue, R.color.basicWhite),
    DARK(R.color.basicWhite, R.color.primaryBlue),
    POSTPAID(R.color.mud_palette_prio_gold,R.color.mud_palette_basic_white),
    POSTPAID_DARK(R.color.mud_palette_basic_white,R.color.mud_palette_prio_gold),
    HOME(R.color.mud_palette_home_primary,R.color.mud_palette_basic_white),
    HOME_DARK(R.color.mud_palette_basic_white,R.color.mud_palette_home_primary),
    DISABLED(R.color.basicMediumGrey, R.color.basicMediumGrey) {
        override fun setClickListener(checkboxView: RelativeLayout, onClickListener: (() -> Unit)?) {
            TouchFeedbackUtil.detach(checkboxView)
        }

        override fun setCheckmark(iconView: Base, onStateChange: ((Boolean) -> Unit)?) {
            if (onStateChange != null) {
                onStateChange(false)
            }

            iconView.setColor(checkmarkColor)
        }
    };

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun build(name: String?): Mode {
        return values().firstOrNull { it.name == name } ?: this
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    open fun setClickListener(checkboxView: RelativeLayout, onClickListener: (() -> Unit)?) {
        TouchFeedbackUtil.attach(checkboxView, onClickListener)
    }


    fun setColor(checkboxView: RelativeLayout, context: Context) {
        checkboxView.backgroundTintList = ContextCompat.getColorStateList(context, backgroundColor)
    }

    open fun setCheckmark(iconView: Base, onStateChange: ((Boolean) -> Unit)?) {
        iconView.setColor(checkmarkColor)
    }
}