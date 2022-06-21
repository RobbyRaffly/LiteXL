package com.myxlultimate.component.organism.dompetCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_dompet_warning_card.view.*

class DompetWarningCard @JvmOverloads constructor(
    context : Context,
    attrs : AttributeSet? = null
) : LinearLayout(context, attrs) {

    var information = ""
        set(value) {
            field = value
            refreshView()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_dompet_warning_card, this,true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DompetWarningCardAttr)
        typedArray.run {
            information = getString(R.styleable.DompetWarningCardAttr_information) ?: ""
        }
        typedArray.recycle()
    }

    fun refreshView(){
        informationView.text = information
    }
}