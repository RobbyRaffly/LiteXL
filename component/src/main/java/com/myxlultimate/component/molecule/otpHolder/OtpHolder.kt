package com.myxlultimate.component.molecule.otpHolder

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.atom.otpHolderSingle.OtpHolderSingle
import kotlinx.android.synthetic.main.molecule_otp_holder.view.*


/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * OTP HOLDER COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class OtpHolder @JvmOverloads constructor(
        context: Context,
        private val attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * OTP Properties
     */
    private var numberOfChars = 1
    private var value = ""
    private var oldValue = ""

    // ----------------------------------------------------------------------------------

    /**
     * Listeners
     */
    private var onFilledListener: ((String) -> Unit)? = null
    private var onRetractedListener: (() -> Unit)? = null
    private var onTextChangedListener: ((String) -> Unit)? = null


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_otp_holder, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.otpHolderAttr)

        attrs.let {
            setNumberOfChars(typedArray.getInteger(R.styleable.otpHolderAttr_numberOfChars, 1), true)
            setValue(typedArray.getString(R.styleable.otpHolderAttr_value))
        }

        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Set Listeners
     */
    fun setOnFilled(_onFilledListener: ((String) -> Unit)?) {
        onFilledListener = _onFilledListener
    }

    // ----------------------------------------------------------------------------------

    fun setOnRetracted(_onRetractedListener: (() -> Unit)?) {
        onRetractedListener = _onRetractedListener
    }

    // ----------------------------------------------------------------------------------

    fun setOnTextChangedListener(_onTextChangedListener: ((String) -> Unit)?) {
        onTextChangedListener = _onTextChangedListener
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Set Slider Indicator Properties
     */
    fun setNumberOfChars(_numberOfChars: Int = 1, isInitial: Boolean = false) {
        numberOfChars = _numberOfChars

        if (!isInitial) {
            setHolderSingles(value.substring(0, numberOfChars).toCharArray())
        }
    }

    // ----------------------------------------------------------------------------------

    fun setValue(_value: String? = "") {
        val newValue = (_value ?: "").trim()
        if (value.length == numberOfChars && newValue.length < numberOfChars && onRetractedListener != null) {
            onRetractedListener!!()
        }

        value = newValue

        setHolderSingles(value.toCharArray())
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Set Indicator Dots
     */
    private fun setHolderSingles(chars: CharArray) {
        val layoutParent = OtpHolderEl
        //first launch otp
        if (value.isEmpty() && oldValue.isEmpty()) {
            layoutParent.removeAllViews()
            for (index in 0 until numberOfChars) {
                val holderSingle = OtpHolderSingle(context)
                holderSingle.setValue("")
                if (index < numberOfChars - 1) {

                    var layoutParams = LinearLayout.LayoutParams(
                            0,
                            LayoutParams.WRAP_CONTENT, 
                            1F
                    )
                    holderSingle.layoutParams = layoutParams
                }
                layoutParent.addView(holderSingle)
            }
            return
        }
        
        for (index in 0 until numberOfChars) {
            var char = ""
            if (index < chars.size && index >= oldValue.length) {
                //when add char
                char = chars[index].toString()
                val tempHolderSingle = layoutParent.getChildAt(index) as OtpHolderSingle
                tempHolderSingle.setValue(char)
            }
            else if(index >= chars.size && index < oldValue.length) {
                //when remove char
                val tempHolderSingle = layoutParent.getChildAt(index) as OtpHolderSingle
                tempHolderSingle.setValue(char)
            }
        }
        
        oldValue = value

        onTextChangedListener?.invoke(value)

        if (chars.size == numberOfChars && onFilledListener != null) {
            onFilledListener!!(value)
        }
    }

}