package com.myxlultimate.component.template.storeSegment

import androidx.databinding.BindingAdapter

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("label")
    fun setViewAllText(storeSegment: StoreSegment, charSequence: CharSequence) {
        storeSegment.setViewAllText(charSequence)
    }
}