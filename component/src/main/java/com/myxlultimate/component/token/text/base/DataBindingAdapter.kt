package com.myxlultimate.component.token.text.base

import androidx.databinding.BindingAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("text")
    fun setText(base: Base, text: String) {
        base.setText(text)
    }

    @JvmStatic
    @BindingAdapter("color")
    fun setColor(base: Base, color: Int) {
        base.setColor(color)
    }

    @JvmStatic
    @BindingAdapter("mode")
    fun setMode(base: Base, mode: String) {
        base.setMode(mode)
    }

    @JvmStatic
    @BindingAdapter("align")
    fun setAlignment(base: Base, align: String) {
        base.setAlignment(align)
    }

    @JvmStatic
    @BindingAdapter("hasUnderline")
    fun setUnderline(base: Base, hasUnderline: Boolean) {
        base.setUnderline(hasUnderline)
    }

    @JvmStatic
    @BindingAdapter("hasStrike")
    fun setStrike(base: Base, hasStrike: Boolean) {
        base.setStrikethrough(hasStrike)
    }
}