package com.myxlultimate.component.atom.restrictedInput

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class RestrictedInput @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    override fun onSelectionChanged(start: Int, end: Int) {
        val text: CharSequence? = text
        if (text != null) {
            if (start != text.length || end != text.length) {
                setSelection(text.length, text.length)
                return
            }
        }

        super.onSelectionChanged(start, end)
    }

    override fun getSelectionStart(): Int {

        for (element in Thread.currentThread().stackTrace) {
            if (
                element.methodName == "canCopy" ||
                element.methodName == "canCut" ||
                element.methodName == "canSelectAllText" ||
                element.methodName == "canShare"
            ) {
                return -1
            }
        }

        return super.getSelectionStart()
    }
}