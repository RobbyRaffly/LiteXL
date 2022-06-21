package com.myxlultimate.component.token.image.base

import androidx.databinding.BindingAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("source")
    fun setSource(base: Base, source: String) {
        base.setSource(source)
    }

}