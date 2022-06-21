package com.myxlultimate.component.organism.storeCard

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismThumbnailSquareBannerCardBinding
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil

class ThumbnailSquareBannerCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : CardView(context, attributeSet) {
    private var binding: OrganismThumbnailSquareBannerCardBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var backgroundImageUrl = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var icon = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var originalPrice: Long = 0
        set(value) {
            if (value > 0 && price <= 0) {
                price = value
            }
            field = value

            binding?.originalPriceView?.apply {
                text = context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )

                visibility = if (value > 0 && price != value) View.VISIBLE else View.GONE
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

    var price: Long = 0
        set(value) {
            field = value

            binding?.discountedPriceView?.text = context.getString(
                R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value, true)
            )
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.containerView?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------

    var title: String = ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var backgroundColorMode = BackgroundColorMode.DARK
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------


    private fun refreshView() {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)

        binding?.apply {
            backgroundImage.imageSource = backgroundImageUrl
            iconView.imageSource = icon
            titleView.text = title
            if (backgroundColorMode == BackgroundColorMode.DARK) {
                titleView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
                originalPriceView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
                discountedPriceView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
            } else {
                titleView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                originalPriceView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
                discountedPriceView.setTextColor(getColor(context, typedValue.resourceId))
            }
        }
    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismThumbnailSquareBannerCardBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
            )
    }
}