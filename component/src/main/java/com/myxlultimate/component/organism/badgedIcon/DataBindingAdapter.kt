package com.myxlultimate.component.organism.badgedIcon

import androidx.databinding.BindingAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("counter")
    fun setTitle(badgedIcon: BadgedIcon, counter : Int) {
        badgedIcon.setCounter(counter)
    }

    @JvmStatic
    @BindingAdapter("iconCode")
    fun setIcon(badgedIcon: BadgedIcon, icon: Int) {
        badgedIcon.setIcon(icon)
    }

    @JvmStatic
    @BindingAdapter("size")
    fun setIconSize(badgedIcon: BadgedIcon, size: Int) {
        badgedIcon.setIconSize(size)
    }

    @JvmStatic
    @BindingAdapter("gapSize")
    fun setGapSize(badgedIcon: BadgedIcon, size: Int) {
        badgedIcon.setGapSize(size)
    }

    @JvmStatic
    @BindingAdapter("color")
    fun setColor(badgedIcon: BadgedIcon, color: Int) {
        badgedIcon.setColor(color)
    }
}