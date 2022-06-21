package com.myxlultimate.component.molecule.sliderIndicator

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.atom.sliderIndicatordot.Mode
import com.myxlultimate.component.atom.sliderIndicatordot.SliderIndicatorDot
import kotlinx.android.synthetic.main.molecule_slider_indicator.view.*


/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * SLIDER INDICATOR COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class SliderIndicator @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Slide Properties
     */
    private var numberOfSlides = 1
    private var activeIndex = 0

    private var mode = "LIGHT"


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_slider_indicator, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.sliderIndicatorAttr)

        attrs.let {
            setMode(typedArray.getString(R.styleable.sliderIndicatorAttr_mode))
            setNumberOfSlides(typedArray.getInteger(R.styleable.sliderIndicatorAttr_numberOfSlides, 1))
            setActiveIndex(typedArray.getInteger(R.styleable.sliderIndicatorAttr_activeIndex, 0))
        }

        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Set Slider Indicator Properties
     */
    fun setMode(_mode: String?) {
        mode = _mode ?: ""
    }

    fun setNumberOfSlides(_numberOfSlides: Int = 1) {
        numberOfSlides = _numberOfSlides

        setIndicatorDots()
    }

    // ----------------------------------------------------------------------------------

    fun setActiveIndex(_activeIndex: Int = 0) {
        activeIndex = _activeIndex

        setIndicatorDots()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Set Indicator Dots
     */
    private fun setIndicatorDots() {
        val layoutParent = SliderIndicatorEl
        layoutParent.removeAllViews()

        for (index in 0 until numberOfSlides) {
            val indicatorDot = SliderIndicatorDot(context)
            indicatorDot.setMode(mode)
            if (index == activeIndex) {
                indicatorDot.setStatus(true)
            } else {
                indicatorDot.setStatus(false)
            }

            if (index != numberOfSlides - 1) {
                val layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(
                    0,
                    0,
                    resources.getDimensionPixelSize(R.dimen.sliderIndicatorGap),
                    0
                )
                indicatorDot.layoutParams = layoutParams
            }
            layoutParent.addView(indicatorDot)
        }
    }

}