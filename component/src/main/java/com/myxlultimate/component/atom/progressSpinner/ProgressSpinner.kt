package com.myxlultimate.component.atom.progressSpinner

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.atom_progress_spinner.view.*

class ProgressSpinner@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Spinner Properties
     */
    private var color = R.color.prepaidTurquoise

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.atom_progress_spinner, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.progressSpinnerAttr)

        attrs.let {
            setColor(typedArray.getResourceId(R.styleable.progressDialogAttr_color,0))
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
    @SuppressWarnings("deprecation")
    fun setColor(_color :Int) {
        if (_color == 0) return
        
        color = _color
        val resColor = ContextCompat.getColor(context, color)

        val drawableSpinner = ProgressSpinnerEl.indeterminateDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawableSpinner.colorFilter = BlendModeColorFilter(resColor, BlendMode.SRC_ATOP);
        } else {
            drawableSpinner.setColorFilter(resColor, PorterDuff.Mode.SRC_ATOP);
        }
    }

}