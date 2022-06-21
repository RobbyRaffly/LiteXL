package com.myxlultimate.component.util

import android.view.View
import androidx.core.view.isVisible

object IsEmptyUtil {

    fun setVisibility(value: String, view: View) {
        view.isVisible = value.isNotEmpty()
    }

    fun setVisibility(value: Int, view: View) {
        view.isVisible = value > 0
    }

    fun setVisibility(isVisible: Boolean, view: View) {
        view.isVisible = isVisible
    }

    fun setVisibility(anyValue: Any?, view: View) {
        view.isVisible = anyValue != null
    }
}
