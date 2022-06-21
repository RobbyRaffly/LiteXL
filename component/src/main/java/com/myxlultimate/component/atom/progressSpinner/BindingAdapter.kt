package com.myxlultimate.component.atom.progressSpinner

import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("color")
    fun setValue(progressSpinner: ProgressSpinner, color: Int) {
        progressSpinner.setColor(color)
    }

}