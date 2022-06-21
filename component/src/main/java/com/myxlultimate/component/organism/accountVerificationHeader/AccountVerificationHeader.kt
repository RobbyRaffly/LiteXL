package com.myxlultimate.component.organism.accountVerificationHeader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_account_verification_header.view.*

class AccountVerificationHeader @JvmOverloads constructor(
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
     * Variable
     */
    var title : CharSequence
    get() = titleView.getText()
    set(value) {
        titleView.setText(value as String)
    }

    var subImage : CharSequence?=""
    set(value) {
        field = value
        iconView.setSource(value as String)
    }

    var subName : CharSequence
    get() = nameView.getText()
    set(value) {
        nameView.setText(value as String)
    }

    var subDetail : CharSequence
    get() = idView.getText()
    set(value) {
        idView.setText(value as String)
    }

    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_account_verification_header, this, true)


        context.theme.obtainStyledAttributes(attrs, R.styleable.headerAccountVerificationAttr, 0, 0).apply {
            try {
                getString(R.styleable.headerAccountVerificationAttr_title)?.let {
                    title = it
                }
                getString(R.styleable.headerAccountVerificationAttr_image)?.let {
                    subImage = it
                }
                getString(R.styleable.headerAccountVerificationAttr_name)?.let {
                    subName = it
                }
                getString(R.styleable.headerAccountVerificationAttr_id)?.let {
                    subDetail = it
                }
            } finally {
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

    // ----------------------------------------------------------------------------------
}