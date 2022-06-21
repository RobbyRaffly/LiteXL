package com.myxlultimate.component.organism.accountVerificationHeader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.template_header_account_verification.view.*


class AccountVerificationHeaderToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource = ""
        set(value) {
            field = value

            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value
            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var id = ""
        set(value) {
            field = value

            idView.text = value
        }

    // ----------------------------------------------------------------------------------

    var onBackButtonPress: (() -> Unit)? = null
    set(value) {
        field = value
        TouchFeedbackUtil.attach(toolbarView, value)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_header_account_verification, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.headerAccountVerificationAttr)
        typedArray.run {
            title = getString(R.styleable.headerAccountVerificationAttr_title) ?: ""
            imageSource = getString(R.styleable.headerAccountVerificationAttr_image) ?: ""
            name = getString(R.styleable.headerAccountVerificationAttr_name) ?: ""
            id = getString(R.styleable.headerAccountVerificationAttr_id) ?: ""
        }
        typedArray.recycle()
        TouchFeedbackUtil.attach(toolbarView, onBackButtonPress)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}