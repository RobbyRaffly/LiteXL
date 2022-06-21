package com.myxlultimate.component.token.icon.base

import androidx.databinding.BindingAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("iconCode")
    fun setIcon(base: Base, icon: Int) {
        base.setIcon(icon)
    }

    @JvmStatic
    @BindingAdapter("color")
    fun setColor(base: Base, color: Int) {
        base.setColor(color)
    }

    @JvmStatic
    @BindingAdapter("size")
    fun setSize(base: Base, size: Int) {
        base.setSize(size)
    }

    @JvmStatic
    @BindingAdapter("mode")
    fun setMode(base: Base, mode: String) {
        base.setMode(mode)
    }


}