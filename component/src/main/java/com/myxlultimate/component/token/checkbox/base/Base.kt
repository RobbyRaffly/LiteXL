package com.myxlultimate.component.token.checkbox.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.token_checkbox.view.*


/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * BUTTON BASE COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
abstract class Base @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Layout to Use
     */
    private var state = State.INACTIVE

    // ----------------------------------------------------------------------------------

    /**
     * Layout to Use
     */
    protected open val layout = R.layout.token_checkbox

    // ----------------------------------------------------------------------------------

    /**
     * On Change Listener
     */
    private var onChangeListener: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    /**
     * Mode
     */
    protected var mode = Mode.LIGHT

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    @SuppressLint("CustomViewStyleable")
    protected fun setup() {
        LayoutInflater.from(context)
            .inflate(layout, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.checkboxAttr)

        attrs.let {
            setMode(typedArray.getString(R.styleable.checkboxAttr_mode))
            setState(typedArray.getBoolean(R.styleable.checkboxAttr_isActive, false))
        }

        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Mode and Style Setter
     */
    fun setMode(_mode: String?) {
        mode = mode.build(_mode)
        mode.setClickListener(CheckboxEl) {
            val isActive = state == State.ACTIVE
            setState(!isActive)

            if (onChangeListener != null) {
                onChangeListener!!(!isActive)
            }
        }

        mode.setColor(CheckboxEl, context)
        mode.setCheckmark(CheckboxElIcon, onChangeListener)
    }

    // ----------------------------------------------------------------------------------

    /**
     * Click Listener Setter
     */
    fun setOnChange(_onChangeListener: (Boolean) -> Unit) {
        onChangeListener = _onChangeListener
        setMode(mode.name)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Checkbox Properties Setters
     */
    fun setState(isActive: Boolean = false) {
        state = if (isActive) {
            State.ACTIVE
        } else {
            State.INACTIVE
        }

        state.setCheckmark(CheckboxElIcon, onChangeListener)
    }


}