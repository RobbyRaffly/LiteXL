package com.myxlultimate.component.organism.hybridModeWidgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_hybrid_balance_card.view.*

class HybridBalanceInfoCard (
    context: Context,
    attr : AttributeSet? = null
) : LinearLayout(context, attr) {

    var label = ""
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var balance = ""
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var buttonText = ""
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var onButtonPressed : (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------

    var onExclamationMarkPressed : (() -> Unit) ? = null
        set(value) {
            field = value
            refreshView()
        }

    // -----------------------------------------------------------------------------------------------------------




    init {
        LayoutInflater.from(context).inflate(R.layout.organism_hybrid_balance_card, this, true)
    }

    private fun refreshView(){
        buttonView.apply {
            text = buttonText
            setOnClickListener {
                onButtonPressed?.invoke()
            }
        }
        exclamationMarkView.setOnClickListener {
            onExclamationMarkPressed?.invoke()
        }

        topLabelView.text = label
        informationView.text = information
        balanceView.text = balance
    }
}