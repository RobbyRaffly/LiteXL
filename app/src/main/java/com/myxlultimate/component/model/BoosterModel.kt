package com.myxlultimate.component.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class BoosterModel(
    var BoosterTitle:String,
    var descBooster:String,
    var imgBooster: Int
):Parcelable
