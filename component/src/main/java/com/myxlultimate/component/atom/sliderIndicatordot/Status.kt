package com.myxlultimate.component.atom.sliderIndicatordot

import com.myxlultimate.component.R

enum class Status(
  val width: Int,
  val color: Int
) {
    INACTIVE(R.dimen.sliderIndicatorInactiveWidth, R.color.basicMediumGrey),
    ACTIVE(R.dimen.sliderIndicatorActiveWidth, R.color.primaryBlue)
}