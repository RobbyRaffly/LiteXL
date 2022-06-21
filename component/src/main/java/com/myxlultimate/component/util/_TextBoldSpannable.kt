package com.myxlultimate.component.util

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView

fun TextView.setBoldThroughSpannable(charSequence: CharSequence, start: Int, end: Int) {
    val ssBuilder = SpannableStringBuilder(charSequence)
    val spannableString = SpannableString(charSequence)
    charSequence.let {
        spannableString.setSpan(
            ForegroundColorSpan(Color.BLACK),
            start, // start
            end, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }
    this.text = ssBuilder
}