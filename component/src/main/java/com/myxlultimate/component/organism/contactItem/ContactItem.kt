package com.myxlultimate.component.organism.contactItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_addon_card.view.*
import kotlinx.android.synthetic.main.organism_contact_item.view.*

class ContactItem @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs){

    var name = ""
        set(value) {
            field = value

            nameView.text = value
            initialView.text = getInitialName(value)
            firstLetterView.text = getFirstLetter(value)
            if(value.isNotEmpty()){
                nameView.visibility = View.VISIBLE
                TextViewCompat.setTextAppearance(msisdnView, R.style.TextAppearance_MudComponents_H5)
                msisdnView.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
                msisdnView.setPadding(0,16,0,0)
            }
            else{
                nameView.visibility = View.GONE
                TextViewCompat.setTextAppearance(msisdnView, R.style.TextAppearance_MudComponents_H4)
                msisdnView.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_black))
                msisdnView.setPadding(0,0,0,0)
            }
        }

    // ----------------------------------------------------------------------------------

    var msisdn = ""
        set(value) {
            field = value
            if(value.isNotEmpty()){
                msisdnView.visibility = View.VISIBLE
                msisdnView.text = value
            }
            else{
                msisdnView.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------

    var isTheFirst = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, alphabetView)
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun getInitialName (name: String) : String {
        var lastLetter = ""
        var firstLetter = ""
        var initial = ""
        if(name.isNotEmpty()) {
            firstLetter = name.substring(0, 1).toUpperCase()
        }

        if(name.isNotEmpty() && name.split(" ").size>1){
            try {
                lastLetter = name.substring(name.lastIndexOf(" ")+1, name.lastIndexOf(" ")+2).toUpperCase()
            }catch (err:StringIndexOutOfBoundsException){
                lastLetter = ""
            }
        }
        initial = firstLetter+lastLetter
        return initial
    }

    // ----------------------------------------------------------------------------------

    fun getFirstLetter (name: String) : String {
        var firstLetter = ""
        if(name.isNotEmpty()) {
            firstLetter = name.substring(0, 1).toUpperCase()
        }
        return firstLetter
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_contact_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ContactItemAttr)
        typedArray.run {
            name = getString(R.styleable.ContactItemAttr_name) ?: ""
            msisdn = getString(R.styleable.ContactItemAttr_msisdn) ?: ""
            isTheFirst = getBoolean(R.styleable.ContactItemAttr_isTheFirst, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView, onPress)
    }
}