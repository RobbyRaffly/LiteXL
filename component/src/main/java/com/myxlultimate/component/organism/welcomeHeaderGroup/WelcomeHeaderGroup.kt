package com.myxlultimate.component.organism.welcomeHeaderGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_welcome_header.view.*

class WelcomeHeaderGroup @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * On Click Listener
     */
    private var onBackButtonClickListener: (() -> Unit)? = null

    /**
     * Variables
     */
    var title : CharSequence
    get() =  titleView.getText()
    set(value) {
        titleView.setText(value as String)
    }

    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_welcome_header, this, true)

        context.theme.obtainStyledAttributes(attrs, R.styleable.welcomeHeaderAttr, 0, 0).apply {
            try {
                getString(R.styleable.welcomeHeaderAttr_title)?.let {
                    title = it
                }
            }finally {
                recycle()
            }
        }

        setOnBackButtonClick() {}

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Set On CLick Listener
     */
    fun setOnBackButtonClick(_onBackButtonClickListener: (() -> Unit)?) {
        backIconEl.setOnClick(_onBackButtonClickListener)
    }
}