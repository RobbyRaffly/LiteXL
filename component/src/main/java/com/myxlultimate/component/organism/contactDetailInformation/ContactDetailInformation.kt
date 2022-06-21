package com.myxlultimate.component.organism.contactDetailInformation

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_contact_detail_information.view.*

class ContactDetailInformation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val name: String,
        val information: String
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var cardBackgroundColor = R.color.mud_palette_aquamarine
    set(value) {
        field = value
        materialCardViewLayout.setCardBackgroundColor(ColorStateList.valueOf(getColor(context, value)))
    }
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var name = ""
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
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.text = title
        initialView.text = name
        IsEmptyUtil.setVisibility(name, initialView)
        defaultView.visibility=if (name.isEmpty()) View.VISIBLE else View.GONE
        informationView.text = information
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_contact_detail_information, this, true)

        orientation = VERTICAL

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ContactDetailInformation)
        typedArray.run {
            title = getString(R.styleable.ContactDetailInformation_title)?:""
            name = getString(R.styleable.ContactDetailInformation_name)?:""
            information = getString(R.styleable.ContactDetailInformation_information)?:""
            cardBackgroundColor = getResourceId(R.styleable.ContactDetailInformation_cardBackgroundColor,R.color.mud_palette_aquamarine)
        }
        typedArray.recycle()

    }
}