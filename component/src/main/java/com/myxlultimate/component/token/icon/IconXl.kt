package com.myxlultimate.component.token.icon

import android.content.Context
import android.util.AttributeSet
import com.myxlultimate.component.R
import com.myxlultimate.component.token.icon.base.Base
import com.myxlultimate.component.token.icon.base.Type

class IconXl (
    context: Context,
    attrs: AttributeSet? = null
) : Base(context, attrs) {

    override var type: Type = Type.MYXL
    override var size: Int = 90

    init {
        setup()
    }

}
