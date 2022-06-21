package com.myxlultimate.component.organism.loyatyTierStatusItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_loyalty_tier_status_item.view.*

class LoyaltyTierStatusItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultStartColor = "#c40d42"
    private val defaultEndColor = "#18448A"


    var title = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value,labelView)
            labelView.text = value
        }

    var iconCode = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value,iconView)
            iconView.imageSource = value
        }

    var startHexColor = defaultStartColor
        set(value) {
            field = value
            setUpColor()
        }

    var endHexColor = defaultEndColor
        set(value) {
            field = value
            setUpColor()
        }

    var isVerticalGradient = false
        set(value) {
            field = value
            setUpColor()
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_tier_status_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyTierStatusItem)
        typedArray.run {
            title = getString(R.styleable.LoyaltyTierStatusItem_label) ?: ""
            iconCode = getString(R.styleable.LoyaltyTierStatusItem_iconCode) ?: ""
            startHexColor =
                getString(R.styleable.LoyaltyTierStatusItem_startColor) ?: defaultStartColor
            endHexColor = getString(R.styleable.LoyaltyTierStatusItem_endColor) ?: defaultEndColor
            isVerticalGradient = getBoolean(R.styleable.LoyaltyTierStatusItem_isGradientVerticle,false)
        }
        typedArray.recycle()
    }

    private fun setUpColor() {
        if (isVerticalGradient) {
            DrawableUtil.CreateGradientVerticalBackground(context, startHexColor, endHexColor, 16f)?.let {
                containerView.background = it
            }
        } else {
            DrawableUtil.CreateGradientBackground(context, startHexColor, endHexColor, 16f)?.let {
                containerView.background = it
            }
        }
    }


}