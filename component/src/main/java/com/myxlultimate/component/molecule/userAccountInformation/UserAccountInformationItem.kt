package com.myxlultimate.component.molecule.userAccountInformation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.molecule_user_account_information_item.view.*

class UserAccountInformationItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isActive = true
        set(value) {
            field = value
            refreshView()
        }

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

    var hasWarningLabel = false
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.text = title

        nameView.text = name

        if (isActive) nameView.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.mud_palette_basic_black
            )
        )
        else nameView.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.mud_palette_basic_dark_grey
            )
        )

        if (hasWarningLabel) {
            baseLayoutWarning.visibility = View.VISIBLE
        } else {
            baseLayoutWarning.visibility = View.GONE
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_user_account_information_item, this, true)

        orientation = VERTICAL

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.UserAccountInformationItem)
        typedArray.run {
            title = getString(R.styleable.UserAccountInformationItem_title) ?: ""
            name = getString(R.styleable.UserAccountInformationItem_name) ?: ""
            isActive = getBoolean(R.styleable.UserAccountInformationItem_isActive, true)
            hasWarningLabel =
                getBoolean(R.styleable.UserAccountInformationItem_hasWarningLabel, false)
        }
        typedArray.recycle()

    }
}