package com.myxlultimate.component.organism.noticeCard

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.icon.IconXl
import com.myxlultimate.component.token.text.TextBody2

enum class Mode(
    val defaultColor: Int,
    val fontColor: Int
) {
    SUCCESS(R.color.turquoiseSoft,R.color.turquoiseDark),
    WARNING(R.color.yellowSoft,R.color.yellowDark),
    ERROR(R.color.redSoft,R.color.redDark);

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun build(name: String?): Mode {
        return values().firstOrNull { it.name == name } ?: this
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    open fun setBackgroundColor(context: Context, noticeCardView: LinearLayout){
        val backgroundStyle = noticeCardView.background as GradientDrawable
        backgroundStyle.setColor(ContextCompat.getColor(context, defaultColor))
    }

    // ----------------------------------------------------------------------------------
    open fun setComponentColor(iconLeft: IconXl, title: TextBody2, iconRight: IconXl){
        iconLeft.setColor(fontColor)
        title.setColor(fontColor)
        iconRight.setColor(fontColor)
    }
}
