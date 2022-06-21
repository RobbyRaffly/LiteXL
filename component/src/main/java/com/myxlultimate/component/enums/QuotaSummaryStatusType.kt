package com.myxlultimate.component.enums

import androidx.annotation.ColorRes
import com.myxlultimate.component.R

enum class QuotaSummaryStatusType(
    var type: String,
    @ColorRes var color: Int = R.color.mud_palette_basic_green
) {
    NONE("NONE"),
    PASS("PASS", R.color.mud_palette_basic_green),
    XL_PASS("XL_PASS", R.color.mud_palette_basic_green),
    HAJJ("HAJJ", R.color.mud_palette_basic_green),
    BASIC_PRICE("BASIC_PRICE", R.color.mud_palette_basic_red), // Tarif dasar
    ROAMING_OFF("ROAMING_OFF", R.color.mud_palette_basic_dark_grey),
    SUSPENDED("SUSPENDED", R.color.mud_palette_basic_red),
    CUSTOM("CUSTOM", R.color.mud_palette_basic_green),
    FUP_LOW("FUP_LOW", R.color.mud_palette_yellow_normal),
    FUP_EMPTY("FUP_EMPTY", R.color.mud_palette_basic_red);

    companion object {
        operator fun invoke(type: String = ""): QuotaSummaryStatusType {
            return values().firstOrNull { it.name == type } ?: NONE
        }
    }
}