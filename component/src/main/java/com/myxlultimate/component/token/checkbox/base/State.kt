package com.myxlultimate.component.token.checkbox.base

import android.view.View
import com.myxlultimate.component.token.icon.base.Base

enum class State() {
    ACTIVE,
    INACTIVE;

    fun setCheckmark(iconView: Base, onStateChange: ((Boolean) -> Unit)?) {
        if (this == INACTIVE) {
            iconView.visibility = View.GONE
        } else {
            iconView.visibility = View.VISIBLE
        }

        if (onStateChange != null) {
            onStateChange(this == ACTIVE)
        }
    }
}