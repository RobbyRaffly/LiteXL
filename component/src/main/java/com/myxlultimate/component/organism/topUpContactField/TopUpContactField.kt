package com.myxlultimate.component.organism.topUpContactField

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_top_up_contact_field.view.*

class TopUpContactField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var value = ""
        set(value) {
            field = value
            formFieldValueView.text = value
        }
    
    // ----------------------------------------------------------------------------------  
    
    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(contactButton, value)
        }

    // ----------------------------------------------------------------------------------  

    var onTextFieldPress: (() -> Unit)? = null
        set(value) {
            field = value
            formFieldValueView.onPress = value
        }

    // ----------------------------------------------------------------------------------  

    var onChange: ((String) -> Unit)? = null
    set(value) {
        field = value
        formFieldValueView.onTextChangeListener = value
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_top_up_contact_field, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TopUpContactFieldAttr)
        typedArray.run {
            value = typedArray.getString(R.styleable.TopUpContactFieldAttr_value)?:""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(true, contactButton, onPress)
        
    }
}