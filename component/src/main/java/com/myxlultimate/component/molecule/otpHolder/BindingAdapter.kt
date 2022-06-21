package com.myxlultimate.component.molecule.otpHolder

import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("value")
    fun setValue(holder: OtpHolder, value: String) {
        holder.setValue(value)
    }

    @JvmStatic
    @BindingAdapter("numberOfChars")
    fun setNumberOfChars(holder: OtpHolder, numberOfChars: Int) {
        holder.setNumberOfChars(numberOfChars)
    }

}