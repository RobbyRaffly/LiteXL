package com.myxlultimate.component.molecule.footerItem

import com.myxlultimate.component.R

enum class Status(
    val font :Int,
    val color: Int
) {
    INACTIVE(R.font.museo_sans_300,R.color.basicMediumGrey),
    ACTIVE(R.font.museo_sans_700,R.color.basicBlack)
}