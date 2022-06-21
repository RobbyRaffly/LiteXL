package com.myxlultimate.component.token.text

import android.content.Context
import android.util.AttributeSet
import com.myxlultimate.component.R
import com.myxlultimate.component.token.text.base.Base
import com.myxlultimate.component.token.text.base.Weight

class TextGreetings(
    context: Context,
    attrs: AttributeSet? = null
) : Base(context, attrs) {

    override val size = R.dimen.textGreetingsSize
    override val lineHeight = R.dimen.textGreetingsLineHeight
    override val weight = Weight.REGULAR

    init {
        setup()
    }
}