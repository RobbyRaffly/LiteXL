package com.myxlultimate.component.atom.otpHolderSingle

import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("value")
    fun setValue(holder: OtpHolderSingle, value: String) {
        holder.setValue(value)
    }

}