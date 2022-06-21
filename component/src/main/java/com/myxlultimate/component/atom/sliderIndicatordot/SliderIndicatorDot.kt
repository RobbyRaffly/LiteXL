package com.myxlultimate.component.atom.sliderIndicatordot

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.atom_slider_indicator_dot.view.*


/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * SLIDER BASE COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class SliderIndicatorDot @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Button Properties
     */
    private var status = Status.INACTIVE
    private var mode = Mode.LIGHT

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.atom_slider_indicator_dot, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.sliderIndicatorAttr)

        attrs.let {
            setMode(typedArray.getString(R.styleable.sliderIndicatorAttr_mode))
            setStatus(typedArray.getBoolean(R.styleable.sliderIndicatorAttr_isActive, false))
        }

        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Indicator Properties Setters
     */

    /**
     * Mode and Style Setter
     */
    fun setMode(_mode: String?) {
        mode = mode.build(_mode)
    }

    fun setStatus(isActive: Boolean) {
        status = if (isActive) {
            Status.ACTIVE
        } else {
            Status.INACTIVE
        }

        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)

        val layoutParams = sliderIndicatorDotEl.layoutParams
        layoutParams.width = resources.getDimensionPixelSize(status.width)

        sliderIndicatorDotEl.layoutParams = layoutParams
        if(status == Status.INACTIVE) sliderIndicatorDotEl.alpha = mode.defaultInactiveOpacity
        sliderIndicatorDotEl.backgroundTintList = if(status == Status.ACTIVE) ContextCompat.getColorStateList(context, if(mode == Mode.LIGHT) typedValue.resourceId else mode.defaultActiveColor) else ContextCompat.getColorStateList(context, mode.defaultInactiveColor)
    }

}