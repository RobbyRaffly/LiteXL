package com.myxlultimate.component.token.icon.base

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.myxlultimate.component.R

enum class Type(
    val defaultFont: Int
) {
    FA_SOLID(R.font.fontawesomesolid),
    FA_REGULAR(R.font.fontawesome),
    FA_BRANDS(R.font.fontawesomebrands),
    MYXL(R.font.myxl);

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    open fun setType(iconView: TextView, context: Context) {
        val typeface = ResourcesCompat.getFont(context, defaultFont)

        iconView.setTypeface(typeface, Typeface.NORMAL)
    }
}
