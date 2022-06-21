package com.myxlultimate.component.organism.transactionSummary

import com.myxlultimate.component.R

enum class RowValueType(
    val style: Int,
    val labelColor: Int,
    var valueColor : Int
) {
    TOTAL(R.style.TextAppearance_MudComponents_H4, R.color.mud_palette_basic_black, R.color.mud_palette_primary_blue),
    ID(R.style.TextAppearance_MudComponents_H5, R.color.mud_palette_basic_black, R.color.mud_palette_basic_black),
    DETAIL(R.style.TextAppearance_MudComponents_Body2, R.color.mud_palette_basic_black, R.color.mud_palette_basic_black),
    TOTAL_TRX(R.style.TextAppearance_MudComponents_H5, R.color.mud_palette_basic_black, R.color.mud_palette_primary_blue);
}