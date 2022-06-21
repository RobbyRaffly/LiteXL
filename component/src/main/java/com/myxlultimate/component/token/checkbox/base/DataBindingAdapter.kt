package com.myxlultimate.component.token.checkbox.base

import androidx.databinding.BindingAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("isActive")
    fun setState(base: Base, isActive: Boolean) {
        base.setState(isActive)
    }

    @JvmStatic
    @BindingAdapter("mode")
    fun setMode(base: Base, mode: String) {
        base.setMode(mode)
    }

}