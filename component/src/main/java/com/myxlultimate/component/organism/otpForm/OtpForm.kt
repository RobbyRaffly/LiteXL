package com.myxlultimate.component.organism.otpForm

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.text.base.Mode
import kotlinx.android.synthetic.main.organism_otp_form.view.*

/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * OTP FORM COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class OtpForm @JvmOverloads constructor(
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
    private var resendDelay = 0
    private var numberOfChars = 1


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_otp_form, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.otpFormAttr)

        attrs.let {
            setNumberOfChars(typedArray.getInteger(R.styleable.otpFormAttr_numberOfChars, 1), true)
            setValue(typedArray.getString(R.styleable.otpFormAttr_value))
            setResendText(typedArray.getString(R.styleable.otpFormAttr_resendText))
            setResendDelay(typedArray.getInteger(R.styleable.otpFormAttr_resendDelay, 0))
            setErrorMessage(typedArray.getString(R.styleable.otpFormAttr_errorMessage))
            setResendTextColor(typedArray.getResourceId(R.styleable.otpFormAttr_color,R.color.primaryBlue))
        }

        typedArray.recycle()

        setFormListener()

        OtpFormElForm.inputType =
            InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
    }

    // ----------------------------------------------------------------------------------

    private fun setFormListener() {
        OtpFormElForm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                setValue(p0.toString())
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

    fun setOnTextChanged(_onTextChangedListener: ((String) -> Unit)?) {
        OtpFormElHolder.setOnTextChangedListener(_onTextChangedListener)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Set Otp Form Properties
     */
    fun setNumberOfChars(_numberOfChars: Int = 1, isInitial: Boolean = false) {
        if (!isInitial) return

        numberOfChars = _numberOfChars
        val filter = arrayOfNulls<InputFilter>(1)
        filter[0] = LengthFilter(numberOfChars)
        OtpFormElForm.filters = filter

        OtpFormElHolder.setNumberOfChars(_numberOfChars, isInitial)
    }

    // ----------------------------------------------------------------------------------

    fun setValue(_value: String? = "") {
        value = (_value?.toUpperCase()) ?: ""
        OtpFormElHolder.setValue(value)
    }

    // ----------------------------------------------------------------------------------

    fun clearValue() {
        value = ""
        OtpFormElHolder.setValue("")
        OtpFormElForm.text?.clear()
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

    fun setResendTextColor(color : Int) {
        OtpFormElResendButton.linkColor = color
    }
    // ----------------------------------------------------------------------------------

    fun setResendDelay(_resendDelay: Int = 0) {
        if (resendDelay == 0 && _resendDelay == 0) return

        resendDelay = _resendDelay
    }

    // ----------------------------------------------------------------------------------

    fun setErrorMessage(errorMessage: String? = "") {
        OtpFormElErrorMessage.visibility = if (errorMessage != null && errorMessage != "") View.VISIBLE else View.GONE

        OtpFormElErrorMessage.setText(errorMessage ?: "")
    }

}