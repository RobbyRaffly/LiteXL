package com.myxlultimate.component.organism.packageCard.enums

enum class OptionMode {
    SINGLE,
    MULTIPLE,
    MULTIPLE_SMALL,
    SELECT_SMALL;
    companion object {
        operator fun invoke(mode: String = ""): OptionMode {
            return values().firstOrNull { it.name == mode } ?: SINGLE
        }
    }
}