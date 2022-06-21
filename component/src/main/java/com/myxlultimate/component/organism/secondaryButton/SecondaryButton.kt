package com.myxlultimate.component.organism.secondaryButton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_secondary_button.view.*

/*
*
* USE FOR TROUBLESHOOTING LIST OF BUTTONS IN RV
*
* */

class SecondaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title : String,
        val isDisabled : Boolean
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            secondaryButtonView.text = value
        }

    // ----------------------------------------------------------------------------------

    var isButtonDisabled = false
        set(value) {
            field = value
            secondaryButtonView.isEnabled = !value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            secondaryButtonView.setOnClickListener {
                onPress?.let { it1 -> it1() }
            }
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_secondary_button, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SecondaryButton)
        typedArray.run {
            title = getString(R.styleable.SecondaryButton_title)?:""
            isButtonDisabled = getBoolean(R.styleable.SecondaryButton_isDisabled,false)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
}