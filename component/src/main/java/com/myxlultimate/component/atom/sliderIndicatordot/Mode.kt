package com.myxlultimate.component.atom.sliderIndicatordot

import android.content.Context
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R

enum class Mode(
    val defaultActiveColor: Int,
    val defaultInactiveColor : Int,
    val defaultInactiveOpacity : Float
) {
    LIGHT(R.color.primaryBlue, R.color.basicMediumGrey,1f),
    DARK(R.color.basicWhite,R.color.basicWhite, 0.3f);

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    fun build(name: String?): Mode {
        return values().firstOrNull { it.name == name } ?: this
    }

}