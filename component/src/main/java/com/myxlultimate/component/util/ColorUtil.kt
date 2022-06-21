package com.myxlultimate.component.util

import android.content.res.ColorStateList
import android.graphics.Color

object ColorUtil {
    fun parseColor(hexColor: String, onParsed: (Int) -> Unit, onError: (() -> Unit)? = null) {
        try {
            val colorInt = Color.parseColor(hexColor)
            onParsed(colorInt)
        } catch (e: Exception) {
            onError?.invoke()
        }
    }
}