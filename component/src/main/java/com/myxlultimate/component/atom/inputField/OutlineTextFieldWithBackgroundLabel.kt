package com.myxlultimate.component.atom.inputField

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.atom_outline_text_field_with_background_label.view.*

class OutlineTextFieldWithBackgroundLabel(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet,0) {

    var showTextCounter = false
        set(value) {
            field = value
            setUpMaximumLetters( maxLetters,currentLetter)

        }
    var currentLetter = 0
        set(value) {
            field = value
            setUpMaximumLetters( maxLetters,currentLetter)
        }
    var maxLetters = 100
        set(value) {
            field = value
            setUpMaximumLetters( maxLetters,currentLetter)
        }

    private fun setUpMaximumLetters(maxLetter: Int, currentLetter: Int) {
        if (showTextCounter) {
            textInputLayoutView.suffixText = if (currentLetter > maxLetter) resources.getString(
                R.string.outlined_textfield_maks_char,
                maxLetter,
                maxLetter
            ) else resources.getString(
                R.string.outlined_textfield_maks_char,
                currentLetter,
                maxLetter
            )
            isError = currentLetter > maxLetter
            setLimit(maxLetters)
            ContextCompat.getColorStateList(context, R.color.mud_palette_basic_light_grey)?.let {
                textInputLayoutView.setSuffixTextColor(
                    it
                )
            }

        } else {
            textInputLayoutView.suffixText = endText
        }
    }

    var onIconPress: (() -> Unit)? = null
        set(value) {
            field = value
            textInputLayoutView.setEndIconOnClickListener {
                value?.let { it() }
            }
        }

    var initialColor = R.color.mud_palette_primary_blue
    var color = R.color.mud_palette_primary_blue
        set(value) {
            field = value
            setBoxSrokeColor(value)
        }

    var boxWidth: Int = 2
        set(value) {
            field = value
            setBoxStrokeWidth(value)
        }

    var boxColor = R.color.mud_palette_basic_white
        set(value) {
            field = value
            setBoxBackgroundColor(value)
        }

    var isNoPadding: Boolean = false
        set(value) {
            field = value
            if (value) {
                textFieldView.setPadding(0, 0, 0, 0)
            }
        }

    var isTextDisabled: Boolean = false
        set(value) {
            field = value
            if (value) {
                setEditTextDisabled(value)
            }
        }

    var blurColor = R.color.mud_palette_basic_medium_grey
        set(value) {
            field = value
            setBoxSrokeColor(color, value)
        }

    var fontColor = R.color.mud_palette_basic_black
        set(value) {
            field = value
            setTextColor(value)
        }

    var isReadOnly = false
        set(value) {
            if (field != value) {
                field = value
                textInputLayoutView.isFocusable = !isReadOnly
                textInputLayoutView.isFocusableInTouchMode = !isReadOnly
                textFieldView.inputType = if (isReadOnly) InputType.TYPE_NULL else inputType
                color = if (isReadOnly) blurColor else initialColor
                setHintColor(if (isReadOnly) R.color.mud_palette_basic_medium_grey else initialColor)
            }

        }

    /**
     * Sets the [OutlineTextField] text
     */
    var text: CharSequence?
        get() = textInputLayoutView.editText?.text
        set(value) {
            textInputLayoutView.editText?.setText(value)
            onTextChangeListener?.let { it(value as String) }
        }

    var value = ""

    /**
     * Sets hint of [OutlineTextField]
     */
    var hint: CharSequence?
        get() = textInputLayoutView.hint
        set(value) {
            textInputLayoutView.hint = value
        }

    var removeHelper = false
        set(value) {
            field = value
            if (value) {
                errorInformationView.visibility = View.GONE
            } else {

                errorInformationView.visibility = View.VISIBLE

            }

        }

    /**
     * Sets the hint of [OutlineTextField]
     */
    var textHint: CharSequence?
        get() = textInputLayoutView.editText?.hint
        set(value) {
            textInputLayoutView.editText?.hint = value
        }

    /**
     * Sets the [InputType] of [OutlineTextField]
     *
     * By default, It is [InputType.TYPE_CLASS_NUMBER]
     */
    var inputType: Int = 0
        set(value) {
            textInputLayoutView.editText?.inputType = value
            field = value
        }

    /**
     * Sets the end icon mode of [OutlineTextField].
     *
     * By default, it is [TextInputLayout.END_ICON_CLEAR_TEXT]
     */
    var endIconMode: Int = 0
        set(value) {
            textInputLayoutView.endIconMode = value
            field = value
        }

    /**
     * Sets the [helperText] of [OutlineTextField]
     */
    var helperText: CharSequence?
        get() = errorInformationView.text
        set(value) {
            errorInformationView.text = value
        }


    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            textInputLayoutView.isFocusableInTouchMode = false
            TouchFeedbackUtil.attach(true, textInputLayoutView, value)
        }

    /**
     * Sets the error state of [OutlineTextField]
     */
    var isError: Boolean = false
        set(value) {
            field = value
            if (value) {
                setBoxSrokeColor(R.color.basicRed, R.color.basicRed)
                setHintColor(R.color.basicRed)
                setHelperTextColor(R.color.basicRed)
                setDefaultHintTextColor(R.color.basicRed)
            } else {
                setHelperTextColor(R.color.blueGrey)
                setBoxSrokeColor(color, blurColor)
                setDefaultHintTextColor(R.color.basicDarkGrey)
                setHintColor(color)
            }
        }

    var onTextChangeListener: ((String) -> Unit)? = null

    var onSelectionChanged: ((Int, Int) -> Unit)? = null
        set(value) {
            field = value
            textFieldView?.onSelectionChanged = value
        }

    private var enable = false
        set(value) {
            field = value
            textInputLayoutView.isEnabled = value
        }

    var endText = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                textInputLayoutView.suffixText = value
            }
            ContextCompat.getColorStateList(context, color)?.let {
                textInputLayoutView.setSuffixTextColor(
                    it
                )
            }
        }

    var rightText = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                textInputLayoutView.suffixText = value
                ContextCompat.getColorStateList(context, R.color.mud_palette_basic_white)?.let {
                    textInputLayoutView.setSuffixTextColor(it)
                }
            }
//            textRight.apply {
//                text = value
//                visibility = if (value.isNotEmpty()) View.VISIBLE else View.GONE
//            }
        }

    var rightTextColor = R.color.basicDarkGrey
        set(value) {
            field = value
//            ContextCompat.getColorStateList(context, value)?.let {
//                textRight.setTextColor(it)
//            }
        }

    var onRightTextPress: (() -> Unit)? = null
        set(value) {
            field = value
//            TouchFeedbackUtil.attach(true, textRight, value)
        }

    fun setValue(_value: String? = "", isInitial: Boolean = false) {
        if (_value == value || _value == null) return

        value = _value
        if (textFieldView.text.toString() != value) {
            textFieldView.setText(value)
        }

        if (!isInitial) {
            onTextChangeListener?.let { it(value) }
        }
    }

    fun setEditTextDisabled(isDisabled: Boolean = false) {
        if (isDisabled) {
            setBoxSrokeColor(
                R.color.mud_palette_basic_medium_grey,
                R.color.mud_palette_basic_medium_grey
            )
            setBoxBackgroundColor(R.color.mud_palette_basic_light_grey)
            setTextColor(R.color.basicBlack)
            textFieldView.isEnabled = false
        } else {
            setBoxSrokeColor(color, blurColor)
            setBoxBackgroundColor(boxColor)
            setTextColor(fontColor)
            textFieldView.isEnabled = true

        }
    }

    /**
     * Sets the Default color of [hint]
     */
    fun setDefaultHintTextColor(@ColorRes color: Int) {
        textInputLayoutView.defaultHintTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, color))
    }

    /**
     * Sets the outline border color of [OutlineTextField]
     */
    fun setBoxSrokeColor(
        @ColorRes focusedBorderColor: Int = R.color.mud_palette_primary_blue,
        @ColorRes blurBorderColor: Int = R.color.mud_palette_basic_medium_grey
    ) {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf()
        )

        val colors = intArrayOf(
            ContextCompat.getColor(context, focusedBorderColor),
            ContextCompat.getColor(context, blurBorderColor)
        )

        val colorStateList = ColorStateList(states, colors)
        textInputLayoutView.setBoxStrokeColorStateList(colorStateList)
    }

    fun setBoxStrokeWidth(width: Int = 2) {
        textInputLayoutView.boxStrokeWidth = width
    }

    fun setBoxBackgroundColor(@ColorRes backgroundColor: Int = R.color.mud_palette_basic_white) {
        textInputLayoutView.boxBackgroundColor = ContextCompat.getColor(context, backgroundColor)
    }

    fun setTextColor(@ColorRes textColor: Int = R.color.mud_palette_basic_black) {
        textFieldView.setTextColor(ContextCompat.getColor(context, textColor))
    }

    /**
     * Sets the color of [hint].
     *
     * This color appears when the hint is floated upwards.
     *
     * @property hintColor
     */
    fun setHintColor(@ColorRes hintColor: Int) {
        textInputLayoutView.hintTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, hintColor))
    }

    /**
     * Sets the color of [helperText]
     *
     * @property helperTextColor
     */
    fun setHelperTextColor(@ColorRes helperTextColor: Int) {
        errorInformationView.setTextColor(
            ContextCompat.getColor(
                context,
                helperTextColor
            )
        )
    }


    /**
     * Sets the color of the hint of [TextInputEditText] which is inside he [OutlineTextField]
     *
     * @property hintColor
     */
    fun setTextHintColor(@ColorRes hintColor: Int) {
        textInputLayoutView.editText?.setHintTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    hintColor
                )
            )
        )
    }

    /**
     * Sets the start icon of [OutlineTextField]
     *
     * @property resource
     */
    fun setStartIconDrawable(@DrawableRes resource: Int) {
        textInputLayoutView.setStartIconDrawable(resource)
    }

    /**
     * Sets the start icon of [OutlineTextField]
     *
     * @property drawable
     */
    fun setStartIconDrawable(@Nullable drawable: Drawable) {
        textInputLayoutView.startIconDrawable = drawable
    }

    /**
     * Sets [ColorStateList] of start icon of [OutlineTextField]
     *
     * @property colorStateList
     */
    fun setStartIconTintList(colorStateList: ColorStateList) {
        textInputLayoutView.setStartIconTintList(colorStateList)
    }

    /**
     * Sets the end icon of [OutlineTextField]
     *
     * @property resource
     */
    fun setEndIconDrawable(@DrawableRes resource: Int) {
        textInputLayoutView.setEndIconDrawable(resource)
    }

    /**
     * Sets the end icon of [OutlineTextField]
     *
     * @property drawable
     */
    fun setEndIconDrawable(@Nullable drawable: Drawable) {
        textInputLayoutView.endIconDrawable = drawable
    }

    /**
     * Sets [ColorStateList] of end icon of [OutlineTextField]
     *
     * @property colorStateList
     */
    fun setEndIconTintList(colorStateList: ColorStateList) {
        textInputLayoutView.setEndIconTintList(colorStateList)
    }

    /**
     * Returns the [TextInputEditText] of [OutlineTextField]
     */
    fun getEditText() = textInputLayoutView?.editText

    fun setLimit(max: Int = 0) {
        textFieldView.filters =
            if (max > 0) arrayOf(InputFilter.LengthFilter(max)) else arrayOf()
    }

    fun setOnTextChange(_onTextChangeListener: ((String) -> Unit)?) {
        onTextChangeListener = _onTextChangeListener
    }

    fun setListeners() {

        textFieldView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // No Implementation
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No Implementation
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setValue(p0.toString())
                if (p0 != null) {
                    setUpMaximumLetters(maxLetters, p0.length)
                }
            }
        })
    }

    var setHintLabelText = ""
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    private val radius = resources.getDimensionPixelSize(R.dimen.textFieldRadius).toFloat()

    // -----------------------------------------------------------------------------------------------------------

    var setStrokeBoxColor : Int? = null
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

//    var isError : Boolean = false
//        set(value) {
//            field = value
//            refreshView()
//        }

    // -----------------------------------------------------------------------------------------------------------

    var onRightIconImagePressed : (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var hasRightIcon = false
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var setHintTextColor : Int? = null
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var setFloatingHintTextColor : Int? = null
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var setWarningInformationText = ""
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var setErrorInformationText = ""
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context).inflate(R.layout.atom_outline_text_field_with_background_label, this, true)
        textInputLayoutView.setBoxCornerRadii(
            radius,
            radius,
            radius,
            radius
        )

        textInputLayoutView.setEndIconOnClickListener {
            onIconPress?.let { it() }
        }

        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.OutlineTextField,
            0, 0
        ).apply {

            try {
                getString(R.styleable.OutlineTextField_text)?.let {
                    text = it
                }
                getString(R.styleable.OutlineTextField_hint)?.let {
                    hint = it
                }
                getString(R.styleable.OutlineTextField_textHint)?.let {
                    textHint = it
                }
                getString(R.styleable.OutlineTextField_helperText)?.let {
                    helperText = it
                }
                getInt(
                    R.styleable.OutlineTextField_android_inputType,
                    InputType.TYPE_CLASS_NUMBER
                ).let {
                    inputType = it
                }
                getDrawable(R.styleable.OutlineTextField_startIconDrawable)?.let {
                    setStartIconDrawable(it)
                }
                getColorStateList(R.styleable.OutlineTextField_startIconTint)?.let {
                    setStartIconTintList(it)
                }
                getBoolean(R.styleable.OutlineTextField_startIconCheckable, false).let {
                    textInputLayoutView.isStartIconCheckable = it
                }
                getInt(
                    R.styleable.OutlineTextField_endIconMode,
                    TextInputLayout.END_ICON_NONE
                ).let {
                    endIconMode = it
                }
                getDrawable(R.styleable.OutlineTextField_endIconDrawable)?.let {
                    setEndIconDrawable(it)
                }
                getColorStateList(R.styleable.OutlineTextField_endIconTint)?.let {
                    setEndIconTintList(it)
                }
                getBoolean(R.styleable.OutlineTextField_endIconCheckable, true).let {
                    textInputLayoutView.isEndIconCheckable = it
                }
                getBoolean(R.styleable.OutlineTextField_android_enabled, true).let {
                    enable = it
                }
                getResourceId(
                    R.styleable.OutlineTextField_color,
                    R.color.mud_palette_primary_blue
                ).let {
                    color = it
                    initialColor = it
                }
                getResourceId(
                    R.styleable.OutlineTextField_blurColor,
                    R.color.mud_palette_basic_medium_grey
                ).let {
                    blurColor = it
                }
                getInt(
                    R.styleable.OutlineTextField_boxWidth,
                    2
                ).let {
                    boxWidth = it
                }
                getResourceId(
                    R.styleable.OutlineTextField_boxColor,
                    R.color.mud_palette_basic_white
                ).let {
                    boxColor = it
                }
                getResourceId(
                    R.styleable.OutlineTextField_fontColor,
                    R.color.mud_palette_basic_black
                ).let {
                    fontColor = it
                }
                getBoolean(R.styleable.OutlineTextField_isNoPadding, false).let {
                    isNoPadding = it
                }
                getBoolean(R.styleable.OutlineTextField_isError, false).let {
                    isError = it
                }
                getBoolean(R.styleable.OutlineTextField_isTextDisabled, false).let {
                    isTextDisabled = it
                }
                getBoolean(R.styleable.OutlineTextField_removeHelper, false).let {
                    removeHelper = it
                }
                getString(R.styleable.OutlineTextField_endText)?.let {
                    endText = it
                }
                getString(R.styleable.OutlineTextField_rightText)?.let {
                    rightText = it
                }
                getBoolean(R.styleable.OutlineTextField_isReadOnly, false).let {
                    isReadOnly = it
                }
                getInt(R.styleable.OutlineTextField_maximalText, 100).let {
                    maxLetters = it
                }
                getBoolean(R.styleable.OutlineTextField_showTextCounter, false).let {
                    showTextCounter = it
                }
            } finally {
                recycle()
            }
            setListeners()

        }
    }

    private fun refreshView(){
        topLabelHintTextView.text = setHintLabelText

        setHintTextColor()
        setRightIconVisibility()
        setHintLabel()
        setHintLabelForErrorState()
        setTextChange()
        setWarningInformation()
    }

    private fun setWarningInformation(){
        if (setWarningInformationText.isNotEmpty()){
            exclamationMarkView.visibility = VISIBLE
            informationView.apply {
                visibility = VISIBLE
                text = setWarningInformationText
            }
        }
    }

    private fun setRightIconVisibility(){
        IsEmptyUtil.setVisibility(hasRightIcon, rightIconView)

        TouchFeedbackUtil.attach(rightIconView){
            onRightIconImagePressed?.invoke()
        }
    }

    private fun setTextChange(){
        val labelMotion = findViewById<MotionLayout>(R.id.topLabelMotionLayout)
        textFieldView.doAfterTextChanged {
            it?.let {
                if (it.isNotEmpty() && !isError) {
                    labelMotion.transitionToEnd()
                    textInputLayoutView.boxStrokeColor = ContextCompat.getColor(context, setStrokeBoxColor ?: R.color.mud_palette_basic_white)
                    textFieldView.requestFocus()
                    textFieldView.setSelection(it.length)
                }
            }
        }
    }

    private fun setHintLabel(){

        val labelMotion = findViewById<MotionLayout>(R.id.topLabelMotionLayout)


        textFieldView.setOnFocusChangeListener { _, isFocused ->
            val strokeColor = textInputLayoutView.boxStrokeColor
            if (isFocused) {
                labelMotion.transitionToEnd()
                topLabelHintBackground.background = ColorDrawable(strokeColor)
                rightIconView.imageTintList = ColorStateList.valueOf(strokeColor)
            } else if (!isFocused && textFieldView.text?.isNotEmpty() == true) {
                textInputLayoutView.setDefaultStrokeColor(strokeColor)
                rightIconView.imageTintList = ColorStateList.valueOf(strokeColor)
            } else {
                labelMotion.transitionToStart()
                textInputLayoutView.setDefaultStrokeColor(ContextCompat.getColor(context, R.color.disabled))
                rightIconView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disabled))

            }

        }

    }

    private fun setHintLabelForErrorState(){
        val labelMotion = findViewById<MotionLayout>(R.id.topLabelMotionLayout)
        if (isError){
            val colorRed = ContextCompat.getColor(context, R.color.mud_palette_basic_red)
            labelMotion.getConstraintSet(R.id.end).setColorValue(
                R.id.topLabelHintTextView,
                "TextColor",
                ContextCompat.getColor(context, R.color.mud_palette_basic_white)
            )
            textInputLayoutView.boxStrokeColor = colorRed
            topLabelHintBackground.background = ColorDrawable(colorRed)
            errorInformationView.apply {
                setTextColor(colorRed)
                text = setErrorInformationText
                visibility = View.VISIBLE
            }

        } else {
            labelMotion.getConstraintSet(R.id.end).setColorValue(
                R.id.topLabelHintTextView,
                "TextColor",
                ContextCompat.getColor(context, setFloatingHintTextColor ?: R.color.mud_palette_basic_black)
            )
            errorInformationView.visibility = View.GONE
            textInputLayoutView.boxStrokeColor = ContextCompat.getColor(context, setStrokeBoxColor ?: R.color.mud_palette_basic_white)
            topLabelHintBackground.background = ColorDrawable(ContextCompat.getColor(context, setStrokeBoxColor ?: R.color.mud_palette_basic_white))
        }
    }

    private fun setHintTextColor(){
        val labelMotion = findViewById<MotionLayout>(R.id.topLabelMotionLayout)
        labelMotion.getConstraintSet(R.id.start).setColorValue(
            R.id.topLabelHintTextView,
            "TextColor",
            ContextCompat.getColor(context, setHintTextColor ?: R.color.mud_palette_basic_white)
        )
        labelMotion.getConstraintSet(R.id.end).setColorValue(
            R.id.topLabelHintTextView,
            "TextColor",
            ContextCompat.getColor(context, setFloatingHintTextColor ?: R.color.mud_palette_basic_black)
        )

    }

    fun getTextField() : TextView = textFieldView

    // this funtion is to set outline color when its in unFocused state
    private fun TextInputLayout.setDefaultStrokeColor(
        @ColorInt color: Int
    ) {
        try {
            val defaultStrokeColor = TextInputLayout::class.java.getDeclaredField("defaultStrokeColor")
            defaultStrokeColor.isAccessible = true
            defaultStrokeColor.set(this, color)
        } catch (e: NoSuchFieldException) {

        }
    }
}