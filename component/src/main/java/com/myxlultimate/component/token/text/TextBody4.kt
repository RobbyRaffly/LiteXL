package com.myxlultimate.component.token.text

import android.content.Context
import android.util.AttributeSet
import com.myxlultimate.component.R
import com.myxlultimate.component.token.text.base.Base

class TextBody4 (
    context: Context,
    attrs: AttributeSet? = null
) : Base(context, attrs) {

    override val size = R.dimen.textBody4Size
    override val lineHeight = R.dimen.textBody4LineHeight

    init {
        setup()
    }
}