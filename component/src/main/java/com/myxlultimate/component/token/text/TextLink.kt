package com.myxlultimate.component.token.text

import android.content.Context
import android.util.AttributeSet
import com.myxlultimate.component.R
import com.myxlultimate.component.token.text.base.Base
import com.myxlultimate.component.token.text.base.Mode
import com.myxlultimate.component.token.text.base.Weight
import kotlinx.android.synthetic.main.token_text.view.*

class TextLink (
    context: Context,
    attrs: AttributeSet? = null
) : Base(context, attrs) {

    override val size = R.dimen.textLinkSize
    override val lineHeight = R.dimen.textLinkLineHeight
    override val weight = Weight.BOLD
    override val alwaysClickable = true

    var linkColor = R.color.primaryBlue
    set(value) {
        field = value
        setColor(linkColor)
    }

    init {
        setup()
    }

    override fun setColor(_color: Int?) {
        if (mode == Mode.LIGHT) {
            mode.setColor(TextEl, context, linkColor)
        }
    }
}