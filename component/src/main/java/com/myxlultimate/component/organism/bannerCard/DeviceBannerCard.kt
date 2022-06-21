package com.myxlultimate.component.organism.bannerCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.BackgroundColorMode
import kotlinx.android.synthetic.main.organism_banner_card_device.view.*

class DeviceBannerCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val backgroundImage: String,
        val backgroundColorMode: BackgroundColorMode = BackgroundColorMode.LIGHT,
        val category: String,
        val categoryColor: String,
        val title: String
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var backgroundImage = ""
        set(value) {
            field = value

            cardView.backgroundImage = value
        }

    // ----------------------------------------------------------------------------------

    var backgroundColorMode = BackgroundColorMode.LIGHT
        set(value) {
            field = value

            cardView.backgroundColorMode = value
        }

    // ----------------------------------------------------------------------------------

    var category = ""
        set(value) {
            field = value

            cardView.category = value
        }

    // ----------------------------------------------------------------------------------

    var categoryColor = ""
        set(value) {
            field = value

            cardView.categoryColor = value
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value

            cardView.title = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            cardView.onPress = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_banner_card_device, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerCardDeviceAttr)
        typedArray.run {
            backgroundImage = getString(R.styleable.BannerCardDeviceAttr_backgroundImage) ?: ""
            backgroundColorMode = BackgroundColorMode.values()[getInt(R.styleable.BannerCardDeviceAttr_backgroundColorMode, 0)]
            category = getString(R.styleable.BannerCardDeviceAttr_category) ?: ""
            categoryColor = getString(R.styleable.BannerCardDeviceAttr_categoryColor) ?: ""
            title = getString(R.styleable.BannerCardDeviceAttr_title) ?: ""
        }
    }
}