package com.myxlultimate.component.enums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class FupLimitRule(
    val type: String,
    val title: String
) : Parcelable {

    DAY("DAY","/day"),
    WEEK("WEEK","/week"),
    MONTH("MONTH","/month"),
    NONE("NONE","");


    companion object {
        operator fun invoke(type: String = ""): FupLimitRule {
            return values().firstOrNull { it.type == type } ?: NONE
        }
    }
}