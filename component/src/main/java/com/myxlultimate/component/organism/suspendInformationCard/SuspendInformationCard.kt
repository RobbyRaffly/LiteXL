package com.myxlultimate.component.organism.suspendInformationCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_suspend_information_card.view.*

class SuspendInformationCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value

            titleView.visibility = if(title.isEmpty()) View.GONE else View.VISIBLE
            titleView.text = value


        }
    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            descriptionView.text = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_suspend_information_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuspendInformationCard)
        typedArray.run {
            title = getString(R.styleable.SuspendInformationCard_title)?:""
            description = getString(R.styleable.SuspendInformationCard_description)?:""
        }
        typedArray.recycle()
    }
}