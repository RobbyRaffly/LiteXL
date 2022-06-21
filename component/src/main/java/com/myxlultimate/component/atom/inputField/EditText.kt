package com.myxlultimate.component.atom.inputField

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.R
import com.google.android.material.textfield.TextInputEditText

class EditText @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet,
        defStyleAttr: Int =  R.attr.editTextStyle)
    : TextInputEditText(context, attributeSet, defStyleAttr)  {

    var onSelectionChanged: ((Int, Int)->Unit)? = null

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        onSelectionChanged?.invoke(selStart, selEnd)
    }

}