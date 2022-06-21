package com.myxlultimate.component.organism.pinForm

import android.content.Context
import android.text.*
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import com.myxlultimate.component.R
import com.myxlultimate.component.token.text.base.Mode
import kotlinx.android.synthetic.main.organism_pin_form.view.*
import kotlinx.android.synthetic.main.outline_textfield_layout.view.*

/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * PIN FORM COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class PinForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * OTP Properties
     */
    private var value = ""
    var currentValue = value
        set(value) {
            field = value
            onTextChangeListener?.let { it(value as String) }
        }
    private var resendDelay = 0
    private var numberOfChars = 1
    var onTextChangeListener: ((String) -> Unit)? = null


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_pin_form, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PinForm)

        attrs.let {
            setNumberOfChars(typedArray.getInteger(R.styleable.PinForm_numberOfChars, 1), true)
            setValue(typedArray.getString(R.styleable.PinForm_value))
            setResendText(typedArray.getString(R.styleable.PinForm_resendText))
            setResendDelay(typedArray.getInteger(R.styleable.PinForm_resendDelay, 0))
            setErrorMessage(typedArray.getString(R.styleable.PinForm_errorMessage))
            setResendTextColor(
                typedArray.getResourceId(
                    R.styleable.PinForm_color,
                    R.color.primaryBlue
                )
            )
        }

        typedArray.recycle()

        OtpFormElForm.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        setFormListener()
    }

    // ----------------------------------------------------------------------------------

    private fun setFormListener() {
        OtpFormElForm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                setValue(p0.toString())
                p0?.length?.minus(1)?.let {
                    OtpFormElHolder.maskNow(it)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No Implementation
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No Implementation
            }
        })
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Set Listeners
     */
    fun setOnFilled(_onFilledListener: ((String) -> Unit)?) {
        OtpFormElHolder.setOnFilled(_onFilledListener)
    }

    // ----------------------------------------------------------------------------------

    fun setOnRetracted(_onRetractedListener: () -> Unit) {
        OtpFormElHolder.setOnRetracted(_onRetractedListener)
    }

    // ----------------------------------------------------------------------------------

    fun setOnResendClick(_onResendClickListener: (() -> Unit)?) {
        OtpFormElResendButton.setOnClick(_onResendClickListener)
    }

    fun doOnTextChange(_onTexTChangeListerner: (() -> Unit)?) {
        OtpFormElForm.doOnTextChanged { text, start, count, after ->
            _onTexTChangeListerner
        }
    }

    fun setOnTextChange(_onTextChangeListener: ((String) -> Unit)?) {
        onTextChangeListener = _onTextChangeListener
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Set Otp Form Properties
     */
    fun setNumberOfChars(_numberOfChars: Int = 1, isInitial: Boolean = false) {
        if (!isInitial) return

        numberOfChars = _numberOfChars

        OtpFormElForm.filters = arrayOf(
            LengthFilter(numberOfChars),
            InputFilter { charSequence, start, end, dst, dstart, dend ->
                var keepOriginal = true
                val sb = StringBuilder(end - start)
                for (i in start until end) {
                    val c: Char = charSequence[i]
                    if (isCharAllowed(c)) {
                        sb.append(c)
                    } else keepOriginal = false
                }
                if (keepOriginal) null else {
                    if (charSequence is Spanned) {
                        val sp = SpannableString(sb)
                        TextUtils.copySpansFrom(charSequence as Spanned?, start, sb.length, null, sp, 0)
                        sp
                    } else {
                        sb
                    }
                }
            }
        )

        OtpFormElHolder.setNumberOfChars(_numberOfChars, isInitial)
    }

    private fun isCharAllowed(char: Char): Boolean {
        return Character.isDigit(char)
    }

    // ----------------------------------------------------------------------------------

    fun setValue(_value: String? = "") {
        value = (_value?.toUpperCase()) ?: ""
        OtpFormElHolder.setValue(value)
        currentValue = value
    }

    // ----------------------------------------------------------------------------------

    fun clearValue() {
        value = ""
        OtpFormElHolder.setValue("")
        OtpFormElForm.text?.clear()
        currentValue = value
    }

    // ----------------------------------------------------------------------------------

    fun setDisabled(isDisabled: Boolean) {
        OtpFormElForm.isEnabled = !isDisabled
    }

    // ----------------------------------------------------------------------------------

    fun setResendText(text: String? = "") {
        OtpFormElResendButton.setText(text)

        OtpFormElResendButton.setMode(if (resendDelay > 0) Mode.DISABLED.name else Mode.LIGHT.name)
    }

    // ----------------------------------------------------------------------------------

    fun setResendTextColor(color: Int) {
        OtpFormElResendButton.linkColor = color
    }
    // ----------------------------------------------------------------------------------

    fun setResendDelay(_resendDelay: Int = 0) {
        if (resendDelay == 0 && _resendDelay == 0) return

        resendDelay = _resendDelay
    }

    // ----------------------------------------------------------------------------------

    fun setErrorMessage(errorMessage: String? = "") {
        OtpFormElErrorMessage.visibility =
            if (errorMessage != null && errorMessage != "") View.VISIBLE else View.GONE

        OtpFormElErrorMessage.setText(errorMessage ?: "")
    }

    // ----------------------------------------------------------------------------------

}