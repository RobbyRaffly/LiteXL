package com.myxlultimate.component.enums

import com.myxlultimate.component.R

enum class BackgroundColorMode(
    var type: String,
    val defaultTextColor: Int
) {
    LIGHT("LIGHT", R.color.mud_palette_basic_black),
    DARK("DARK", R.color.mud_palette_basic_white),
    CUSTOM("CUSTOM", R.color.mud_palette_basic_black);

    companion object {
        operator fun invoke(type: String = ""): BackgroundColorMode {
            return values().firstOrNull { it.name == type } ?: LIGHT
        }
    }
}