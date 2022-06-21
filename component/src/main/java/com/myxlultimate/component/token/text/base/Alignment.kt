package com.myxlultimate.component.token.text.base

import android.view.Gravity
import android.widget.TextView

enum class Alignment(
  val align: Int
) {
    LEFT(Gravity.START),
    CENTER(Gravity.CENTER_HORIZONTAL),
    RIGHT(Gravity.END);

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun build(name: String?): Alignment {
        return values().firstOrNull { it.name == name } ?: this
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun setAlignment(textView: TextView) {
        textView.gravity = align
    }
}