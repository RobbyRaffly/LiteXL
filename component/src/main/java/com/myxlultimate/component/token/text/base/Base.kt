package com.myxlultimate.component.token.text.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.token_text.view.*

/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * TEXT BASE COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
abstract class Base @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Layout to Use
     */
    protected open val layout = R.layout.token_text

    // ----------------------------------------------------------------------------------

    /**
     * On Click Listener
     */
    private var onClickListener: (() -> Unit)? = null
    protected open val alwaysClickable = false

    // ----------------------------------------------------------------------------------

    /**
     * Mode
     */
    protected var mode = Mode.LIGHT

    // ----------------------------------------------------------------------------------

    /**
     * Font Properties
     */
    protected open val size = R.dimen.textBody1Size
    protected open val lineHeight = R.dimen.textBody1LineHeight
    protected open val weight = Weight.REGULAR

    protected open var alignment = Alignment.LEFT
    protected open var font = R.font.museo_sans_300

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

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.textAttr)

        attrs.let {
            setText(typedArray.getString(R.styleable.textAttr_text))
            setMode(typedArray.getString(R.styleable.textAttr_mode))
            setColor(typedArray.getResourceId(R.styleable.textAttr_color, 0))
            setAlignment(typedArray.getString(R.styleable.textAttr_align))
            setUnderline(typedArray.getBoolean(R.styleable.textAttr_hasUnderline, false))
            setStrikethrough(typedArray.getBoolean(R.styleable.textAttr_hasStrike,false))
            setSize()
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
        mode.setClickListener(TextEl, onClickListener, alwaysClickable)

        if (!isColorSet) {
            color = mode.defaultColor
        }

        mode.setColor(TextEl, context, color)
        setColor()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Click Listener Setter
     */
    fun setOnClick(_onClickListener: (() -> Unit)?) {
        onClickListener = _onClickListener
        mode.setClickListener(TextEl, onClickListener, alwaysClickable)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Font Properties Setters
     */
    fun setAlignment(_alignment: String?) {
        alignment = alignment.build(_alignment)
        alignment.setAlignment(TextEl)
    }

    // ----------------------------------------------------------------------------------

    fun setUnderline(hasUnderline: Boolean = false) {
        TextEl.paintFlags = if (hasUnderline) {
            Paint.UNDERLINE_TEXT_FLAG
        } else {
            0
        }
    }

    fun setStrikethrough(hasStrike:Boolean=false){
        if (TextEl.text.isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= 23 && hasStrike) {
                TextEl.foreground = context.getDrawable(R.drawable.strikethrough_foreground)
            } else {
                TextEl.paintFlags = if(hasStrike) {
                    Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    0
                }
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Size Setter and Getter
     */
    private fun setSize() {
        val textSize = getSize()
        val textLineHeight = getLineheight()

        TextEl.apply {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            setLineSpacing(textLineHeight, 1F)
        }

        val padding = textLineHeight.toInt() / 2
        TextEl.setPadding(0, padding, 0, padding)
    }

    // ----------------------------------------------------------------------------------

    fun getSize(): Float  = resources.getDimension(size)

    // ----------------------------------------------------------------------------------

    fun getLineheight(): Float  = resources.getDimension(lineHeight) - getSize()

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Font Setter and Getter
     */
    private fun setFont() {
        val typeface = getTypeface()

        TextEl.setTypeface(typeface, Typeface.NORMAL)
    }

    // ----------------------------------------------------------------------------------

    fun getTypeface(): Typeface? = ResourcesCompat.getFont(context, weight.defaultFont)

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Text Setter and Getter
     */
    fun setText(text: String? = "") {
        TextEl.text = text
    }

    // ----------------------------------------------------------------------------------

    fun getText(): String = TextEl.text.toString()

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Color Setters
     */
    open fun setColor(_color: Int? = 0) {
        if (_color != 0 && _color != null) {
            color = _color
            mode.setColor(TextEl, context, color)

            isColorSet = true
        }
    }

}