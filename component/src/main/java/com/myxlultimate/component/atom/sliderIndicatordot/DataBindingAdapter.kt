package com.myxlultimate.component.atom.sliderIndicatordot

import androidx.databinding.BindingAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("isActive")
    fun setStatus(indicatorDot: SliderIndicatorDot, isActive: Boolean) {
        indicatorDot.setStatus(isActive)
    }

}