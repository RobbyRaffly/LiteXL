package com.myxlultimate.component.organism.setLimitFamilyCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.atom.inputField.OutlineTextField
import kotlinx.android.synthetic.main.organism_set_limit_family_card.view.*

class SetLimitFamilyCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val checkBox: CheckBox by lazy { findViewById<CheckBox>(R.id.termsTextCheckBoxView) }
    val outlineTextField : OutlineTextField by lazy { findViewById<OutlineTextField>(R.id.quotaWidgetView) }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    var information = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isLimitError = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

//    var limit = outlineTextField.text?:""

    // ----------------------------------------------------------------------------------

    var setOnChangeListener: ((String) -> Unit)? = null
        set(value) {
            field = value
        }

    // ----------------------------------------------------------------------------------

    var quotaTitle = resources.getString(R.string.organism_set_limit_family_card_quota_title)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isChecked:Boolean = false
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var termsTitle = resources.getString(R.string.organism_set_limit_family_card_quota_no_limit)
    set(value) {
        field = value
       refreshView()
    }

    // ----------------------------------------------------------------------------------

    var textHint = "0"
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var buttonTitle = resources.getString(R.string.organism_set_limit_family_card_quota_button_title)
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var onCardPress : (() -> Unit)? = null
        set(value) {
            field = value
            applyButtonView.setOnClickListener {
                onCardPress?.let { it() }
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.text = title
        informationView.text = information
        quotaWidgetView.isError = isLimitError
        checkBox.isChecked = isChecked
        quotaTitleView.text = quotaTitle
        termsTextView.text = termsTitle
        outlineTextField.textHint = textHint
        applyButtonView.text = buttonTitle
    }


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_set_limit_family_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SetLimitFamilyCard)
        typedArray.run {
            title = getString(R.styleable.SetLimitFamilyCard_title)?:""
            information = getString(R.styleable.SetLimitFamilyCard_information)?:""
            isLimitError = getBoolean(R.styleable.SetLimitFamilyCard_isLimitError,false)
            quotaTitle = getString(R.styleable.SetLimitFamilyCard_quotaTitle)?:resources.getString(R.string.organism_set_limit_family_card_quota_title)
            termsTitle = getString(R.styleable.SetLimitFamilyCard_termsTitle)?:resources.getString(R.string.organism_set_limit_family_card_quota_no_limit)
            buttonTitle = getString(R.styleable.SetLimitFamilyCard_buttonTitle)?:resources.getString(R.string.organism_set_limit_family_card_quota_button_title)
            textHint = getString(R.styleable.SetLimitFamilyCard_textHint)?:"0"
            isChecked = getBoolean(R.styleable.SetLimitFamilyCard_isChecked,false)
        }
        typedArray.recycle()

        quotaWidgetView.setOnTextChange {
            onTextChanged()
        }
    }


    fun onTextChanged() {

        quotaWidgetView.setOnTextChange{}
        var cursorPos = outlineTextField.getEditText()?.selectionStart?:0
        val formatted = (outlineTextField.text.toString().toLongOrNull()?:0L).toString()
        cursorPos -= (outlineTextField.text.toString().length - formatted.length)
        if(cursorPos<0)
            cursorPos = 0
        else if(cursorPos>formatted.length)
            cursorPos = formatted.length

        outlineTextField.text = formatted
        outlineTextField.getEditText()?.setSelection(cursorPos)

        setOnChangeListener?.invoke(formatted)

        outlineTextField.setOnTextChange {
            onTextChanged()
        }
    }
}