package com.myxlultimate.component.organism.bannerCard

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_banner_card_banner.view.*
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.enums.SizeMode
import com.myxlultimate.component.organism.storeCard.enums.SecondaryColorMode
import com.myxlultimate.component.util.ColorUtil
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import java.lang.Exception

class BannerCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val backgroundImage: String,
        val backgroundColorMode: BackgroundColorMode = BackgroundColorMode.LIGHT,
        val sizeMode: SizeMode = SizeMode.FULL,
        val category: String,
        val categoryColor: String,
        val title: String,
        val price: Long = 0,
        val originalPrice: Long = 0
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var backgroundImage = ""
        set(value) {
            field = value

            backgroundView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var backgroundColorMode = BackgroundColorMode.LIGHT
        set(value) {
            field = value

            val textColor = ContextCompat.getColor(context, value.defaultTextColor)
            nameView.setTextColor(textColor)
            nameSmallView.setTextColor(textColor)
            priceView.setTextColor(textColor)
            if (categoryColor == "") {
                categoryView.setTextColor(ContextCompat.getColor(context, SecondaryColorMode.values()[value.ordinal].defaultColor))
            }
        }

    // ----------------------------------------------------------------------------------

    var sizeMode = SizeMode.FULL
        set(value) {
            field = value
            if (value == SizeMode.COMPACT) {
                price = 0
                originalPrice = 0
                hasPrice = false
                // set ratio
                var layoutParamsRatio = containerView.layoutParams as ConstraintLayout.LayoutParams
                layoutParamsRatio.dimensionRatio = "H,3:1"
                containerView.layoutParams = layoutParamsRatio
                layoutParamsRatio = rightGapView.layoutParams as ConstraintLayout.LayoutParams
                layoutParamsRatio.dimensionRatio = "H,1:1"
                rightGapView.layoutParams = layoutParamsRatio
            }
            else {
                // set ratio
                var layoutParamsRatio = containerView.layoutParams as ConstraintLayout.LayoutParams
                layoutParamsRatio.dimensionRatio = "H,8:5"
                containerView.layoutParams = layoutParamsRatio
                layoutParamsRatio = rightGapView.layoutParams as ConstraintLayout.LayoutParams
                layoutParamsRatio.dimensionRatio = "H,2:1"
                rightGapView.layoutParams = layoutParamsRatio
                
            }

            nameView.visibility = if (value == SizeMode.FULL) View.VISIBLE else View.GONE
            nameSmallView.visibility = if (value == SizeMode.COMPACT) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var category = ""
        set(value) {
            field = value

            categoryView.text = value
        }

    // ----------------------------------------------------------------------------------

    var categoryColor = ""
        set(value) {
            field = value

            ColorUtil.parseColor(
                value,
                onParsed = {
                    categoryView.setTextColor(it)
                },
                onError = {
                    categoryView.setTextColor(ContextCompat.getColor(context, SecondaryColorMode.values()[backgroundColorMode.ordinal].defaultColor))
                }
            )
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value

            nameView.text = value
            nameSmallView.text = value
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value

            priceView.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value, true)
            )

            hasPrice = value > 0
        }

    // ----------------------------------------------------------------------------------

    var originalPrice: Long = 0
        set(value) {
            if (value > 0 && field > 0) price = field
            field = value

            originalPriceView.apply {
                text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                        ConverterUtil.convertDelimitedNumber(value, true)
                    )
                visibility = if (value > 0) View.VISIBLE else View.GONE
                if (text.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                    } else {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

    private var hasPrice = true
        set(value) {
            field = value

            priceContainerView.visibility = if (value) View.VISIBLE else View.GONE
            nameView.maxLines = if (value) 2 else 4
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_banner_card_banner, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerCardBannerAttr)
        typedArray.run {
            backgroundImage = getString(R.styleable.BannerCardBannerAttr_backgroundImage) ?: ""
            backgroundColorMode = BackgroundColorMode.values()[getInt(R.styleable.BannerCardBannerAttr_backgroundColorMode, 0)]
            category = getString(R.styleable.BannerCardBannerAttr_category) ?: ""
            categoryColor = getString(R.styleable.BannerCardBannerAttr_categoryColor) ?: ""
            title = getString(R.styleable.BannerCardBannerAttr_title) ?: ""
            price = getInt(R.styleable.BannerCardBannerAttr_price, 0).toLong()
            originalPrice = getInt(R.styleable.BannerCardBannerAttr_originalPrice, 0).toLong()
            sizeMode = SizeMode.values()[getInt(R.styleable.BannerCardBannerAttr_sizeMode, 0)]
        }

        TouchFeedbackUtil.attach(containerView, onPress)
    }
}