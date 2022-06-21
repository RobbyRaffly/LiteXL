package com.myxlultimate.component.organism.bizOnLevelIndicatorCard.enum

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes
import com.myxlultimate.component.R
import kotlinx.android.parcel.Parcelize

enum class BizOnWalletStatus(val method: String,
                             @StringRes val pictureBase64: Int)  {

    GOPAY("GOPAY", R.string.xl_bizon_cashback_gopay),
    OVO("OVO", R.string.xl_bizon_cashback_gopay), // TODO need change picture illustration from designer
    SHOPEEPAY("SHOPEEPAY",R.string.xl_bizon_cashback_gopay), // TODO need change picture illustration from designer
    DANA("DANA", R.string.xl_bizon_cashback_gopay), // TODO need change picture illustration from designer
    AKULAKU("AKULAKU",R.string.xl_bizon_cashback_gopay), // TODO need change picture illustration from designer
    KREDIVO("KREDIVO",R.string.xl_bizon_cashback_gopay), // TODO need change picture illustration from designer
    NONE("", 0)

}