package com.myxlultimate.component.organism.otpForm

import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("value")
    fun setValue(form: OtpForm, value: String) {
        form.setValue(value)
    }

    @JvmStatic
    @BindingAdapter("numberOfChars")
    fun setNumberOfChars(form: OtpForm, numberOfChars: Int) {
        form.setNumberOfChars(numberOfChars)
    }

    @JvmStatic
    @BindingAdapter("resendText")
    fun setResendText(form: OtpForm, text: String) {
        form.setResendText(text)
    }

    @JvmStatic
    @BindingAdapter("resendDelay")
    fun setResendDelay(form: OtpForm, resendDelay: Int) {
        form.setResendDelay(resendDelay)
    }

    @JvmStatic
    @BindingAdapter("errorMessage")
    fun setErrorMessage(form: OtpForm, errorMessage: String) {
        form.setErrorMessage(errorMessage)
    }

}