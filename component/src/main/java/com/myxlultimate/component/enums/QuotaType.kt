package com.myxlultimate.component.enums

import com.myxlultimate.component.R

enum class QuotaType(
    var icon : Int
) {
    DATA(R.string.xl_internet),
    TEXT(R.string.xl_message_square),
    VOICE(R.string.xl_call),
    FAMILY_SLOT(R.string.xl_family_package),
    DEVICE(R.string.xl_modem),
    CUSTOM(R.string.xl_call),
    PRICE(R.string.xl_call),
    MONETARY(R.string.xl_call),
    BALANCE(R.string.xl_internet);

    companion object {
        operator fun invoke(type: String = ""): QuotaType {
            return values().firstOrNull { it.name == type } ?: DATA
        }
    }
}