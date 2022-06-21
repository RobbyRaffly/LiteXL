package com.myxlultimate.component.util

import android.widget.TextView
import androidx.core.widget.TextViewCompat

fun TextView.setTextViewAppearance(resource: Int) {
    TextViewCompat.setTextAppearance(
        this,
        resource
    )
}