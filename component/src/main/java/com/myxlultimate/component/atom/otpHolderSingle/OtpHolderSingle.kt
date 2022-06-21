package com.myxlultimate.component.atom.otpHolderSingle

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.atom_otp_holder_single.view.*


/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * SLIDER BASE COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class OtpHolderSingle @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.atom_otp_holder_single, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.otpHolderSingleAttr)

        attrs.let {
            setValue(typedArray.getString(R.styleable.otpHolderSingleAttr_value))
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
    fun setValue(value: String? = "") {
        if (value != null) {
            OtpHolderSingleElValue.setText(value)
        }
    }

}