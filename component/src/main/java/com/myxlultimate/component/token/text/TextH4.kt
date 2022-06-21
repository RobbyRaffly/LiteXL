package com.myxlultimate.component.token.text

import android.content.Context
import android.util.AttributeSet
import com.myxlultimate.component.R
import com.myxlultimate.component.token.text.base.Base
import com.myxlultimate.component.token.text.base.Weight

class TextH4 (
    context: Context,
    attrs: AttributeSet? = null
) : Base(context, attrs) {

    override val size = R.dimen.textH4Size
    override val lineHeight = R.dimen.textH4LineHeight
    override val weight = Weight.BOLD

    init {
        setup()
    }
}