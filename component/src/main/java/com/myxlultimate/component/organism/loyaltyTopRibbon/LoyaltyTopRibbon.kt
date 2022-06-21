package com.myxlultimate.component.organism.loyaltyTopRibbon

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_loyalty_top_ribbon.view.*

class LoyaltyTopRibbon constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val defaultStartColor = "#c40d42"
    private val defaultEndColor = "#18448A"

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

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_top_ribbon, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyTopRibbon)
        typedArray.run {
            startHexColor = getString(R.styleable.LoyaltyTopRibbon_startColor) ?: defaultStartColor
            endHexColor = getString(R.styleable.LoyaltyTopRibbon_endColor) ?: defaultEndColor
            title = getString(R.styleable.LoyaltyTopRibbon_title) ?: ""
        }

        typedArray.recycle()
    }

    private fun setUpColor() {
        DrawableUtil.CreateGradientBackground(context, startHexColor, endHexColor)?.let {
            containerView.background = it
        }
    }

    private fun refreshView() {
        titleView.text = title
        IsEmptyUtil.setVisibility(title,titleView)
    }
}