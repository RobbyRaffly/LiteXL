package com.myxlultimate.component.molecule.pinHolder

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.myxlultimate.component.R
import com.myxlultimate.component.atom.pinHolderSingle.PinHolderSingle
import kotlinx.android.synthetic.main.molecule_pin_holder.view.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * OTP HOLDER COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class PinHolder @JvmOverloads constructor(
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


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_pin_holder, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PinHolder)

        attrs.let {
            setNumberOfChars(typedArray.getInteger(R.styleable.PinHolder_numberOfChars, 1), true)
            setValue(typedArray.getString(R.styleable.PinHolder_value))
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
        val layoutParent = pinHolderView
        //first launch otp
        if (value.isEmpty() && oldValue.isEmpty()) {
            layoutParent?.removeAllViews()
            for (index in 0 until numberOfChars) {
                val holderSingle = PinHolderSingle(context)
                holderSingle.setValue("")
                if (index < numberOfChars - 1) {

                    var layoutParams = LinearLayout.LayoutParams(
                            0,
                            LayoutParams.WRAP_CONTENT,
                            1F
                    )
                    holderSingle.layoutParams = layoutParams
                }
                holderSingle.dotVisible = false
                layoutParent.addView(holderSingle)
            }
            return
        }

        for (index in 0 until numberOfChars) {
            var char = ""
            if (index < chars.size && index >= oldValue.length) {
                //when add char
                char = chars[index].toString()
                val tempHolderSingle = layoutParent.getChildAt(index) as PinHolderSingle
                tempHolderSingle.setValue(char)
                tempHolderSingle.maskLater()
            }
            else if(index >= chars.size && index < oldValue.length) {
                //when remove char
                val tempHolderSingle = layoutParent.getChildAt(index) as PinHolderSingle
                tempHolderSingle.setValue(char)
                tempHolderSingle.deleteChar()
            }
        }

        oldValue = value

        if (chars.size == numberOfChars && onFilledListener != null) {
            onFilledListener!!(value)
        }
    }

    fun maskNow(currentIndex :Int) {
        for (index in 0 until currentIndex) {
            val tempHolderSingle = pinHolderView.getChildAt(index) as PinHolderSingle
            tempHolderSingle.maskNow()
        }
    }

}