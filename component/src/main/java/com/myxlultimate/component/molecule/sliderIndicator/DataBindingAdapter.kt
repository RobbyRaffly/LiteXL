package com.myxlultimate.component.molecule.sliderIndicator

import androidx.databinding.BindingAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("numberOfSlides")
    fun setNumberOfSlides(sliderIndicator: SliderIndicator, numberOfSlides: Int) {
        sliderIndicator.setNumberOfSlides(numberOfSlides)
    }

    @JvmStatic
    @BindingAdapter("activeIndex")
    fun setActiveIndex(sliderIndicator: SliderIndicator, activeIndex: Int) {
        sliderIndicator.setActiveIndex(activeIndex)
    }

}