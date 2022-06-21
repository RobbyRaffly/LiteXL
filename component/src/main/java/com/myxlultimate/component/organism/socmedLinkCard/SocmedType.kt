package com.myxlultimate.component.molecule.accountLinkCard

import com.myxlultimate.component.R
import com.myxlultimate.component.atom.sliderIndicatordot.Mode

enum class SocmedType(
val imageDrawable:Int,
val socialMediaName : String
){
    FACEBOOK(R.drawable.ic_facebook,"Facebook"),
    GOOGLE(R.drawable.ic_google,"Google");

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    fun build(name: String?): SocmedType {
        return values().firstOrNull { it.name == name } ?: this
    }
}