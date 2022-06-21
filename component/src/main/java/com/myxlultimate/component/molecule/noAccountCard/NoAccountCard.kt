package com.myxlultimate.component.molecule.noAccountCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_no_account_card.view.*

class NoAccountCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(cardView,value)
        }

    var buttonTitle = resources.getString(R.string.organism_no_account_card_instruction)
    set(value) {
        field = value
        addTextView.text = value
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_no_account_card, this, true)

        TouchFeedbackUtil.attach(cardView,onPress)
    }
}