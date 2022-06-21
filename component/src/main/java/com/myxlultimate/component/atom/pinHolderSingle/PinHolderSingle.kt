package com.myxlultimate.component.atom.pinHolderSingle

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.atom_pin_holder_single.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * SLIDER BASE COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class PinHolderSingle @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private var job:Job? = null

    fun Context.lifecycleOwner(): LifecycleOwner? {
        var curContext = this
        var maxDepth = 20
        while (maxDepth-- > 0 && curContext !is LifecycleOwner) {
            curContext = (curContext as ContextWrapper).baseContext
        }
        return if (curContext is LifecycleOwner) {
            curContext as LifecycleOwner
        } else {
            null
        }
    }

    var dotVisible = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value,dotView)
            IsEmptyUtil.setVisibility(!value,pinHolderSingleView)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.atom_pin_holder_single, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PinHolderSingle)

        attrs.let {
            setValue(typedArray.getString(R.styleable.PinHolderSingle_value))
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
            pinHolderSingleView.text = value
        }
    }

    fun maskLater(){
        context.lifecycleOwner()?.let {
            job = it.lifecycleScope.launch {
                delay(1000)
                dotVisible = true
            }
        }
    }

    fun maskNow(){
        job?.cancel()
        dotVisible = true
    }

    fun deleteChar(){
        job?.cancel()
        dotVisible = false
    }
}