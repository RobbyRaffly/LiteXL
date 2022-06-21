package com.myxlultimate.component.token.icon.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.token_icon.view.*

/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * ICON BASE COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
abstract class Base @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyle: Int? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Layout to Use
     */
    protected open val layout = R.layout.token_icon


    // ----------------------------------------------------------------------------------

    /**
     * On Click Listener
     */
    private var onClickListener: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    /**
     * Mode and Type
     */
    protected var mode = Mode.LIGHT
    protected open var type = Type.FA_SOLID

    // ----------------------------------------------------------------------------------

    /**
     * Font Properties
     */
    open var size = 28
    protected open var font = R.font.fontawesome

    // ----------------------------------------------------------------------------------

    /**
     * Font Color
     */
    protected var color = mode.defaultColor
    private var isColorSet = false

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

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.iconAttr)

        attrs.let {
            setIcon(typedArray.getResourceId(R.styleable.iconAttr_iconCode, 0))
            setMode(typedArray.getString(R.styleable.iconAttr_mode))
            setColor(typedArray.getResourceId(R.styleable.iconAttr_color, 0))
            setSize(typedArray.getInteger(R.styleable.iconAttr_size, size))
            setFont()
        }

        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Mode Setter
     */
    fun setMode(_mode: String?) {
        mode = mode.build(_mode)
        mode.setClickListener(IconEl, onClickListener)

        if (!isColorSet) {
            color = mode.defaultColor
        }

        mode.setColor(IconEl, context, color)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Click Listener Setter
     */
    fun setOnClick(_onClickListener: (() -> Unit)?) {
        onClickListener = _onClickListener
        mode.setClickListener(IconEl, onClickListener)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    open fun setSize(_size: Int? = 0) {
        if (_size == null || _size == 0) {
            return
        }

        size = _size  / 2

        IconEl.apply {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, size.toFloat())
        }
    }

    // ----------------------------------------------------------------------------------

    private fun setFont() {
        type.setType(IconEl, context)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Set Icon
     */
    fun setIcon(icon: Int = 0) {
        if (icon == 0) {
            return
        }

        var icon = resources.getText(icon).toString()

        if (icon.isEmpty()) {
            return
        }

        IconEl.text = icon
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Color Setters
     */
    open fun setColor(_color: Int? = 0) {
        if (_color != 0 && _color != null) {
            color = _color
            mode.setColor(IconEl, context, color)

            isColorSet = true
        }
    }

}
