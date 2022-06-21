package com.myxlultimate.component.token.image.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.token_image.view.*

/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * IMAGE BASE COMPONENT
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
    protected open val layout = R.layout.token_image

    // ----------------------------------------------------------------------------------

    /**
     * On Click Listener
     */
    private var onClickListener: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    /**
     * Source Type
     */
    protected open val sourceType = SourceType.ASSET

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

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.imageAttr)

        attrs.let {
           setSource(typedArray.getString(R.styleable.imageAttr_source))
        }

        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Click Listener Setter
     */
    fun setOnClick(_onClickListener: (() -> Unit)?) {
        onClickListener = _onClickListener
        if ((onClickListener == null)) {
            TouchFeedbackUtil.detach(ImageEl)
        } else {
            TouchFeedbackUtil.attach(ImageEl, onClickListener)
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Image Properties Setters
     */
    fun setSource(source: Any?) {
        sourceType.setSource(ImageEl, context, this, source)
    }

}
