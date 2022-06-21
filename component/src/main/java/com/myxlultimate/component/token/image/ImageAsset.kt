package com.myxlultimate.component.token.image

import android.content.Context
import android.util.AttributeSet
import com.myxlultimate.component.token.image.base.Base
import com.myxlultimate.component.token.image.base.SourceType

class ImageAsset (
    context: Context,
    attrs: AttributeSet? = null
) : Base(context, attrs) {

    override val sourceType = SourceType.ASSET

    init {
        setup()
    }

}