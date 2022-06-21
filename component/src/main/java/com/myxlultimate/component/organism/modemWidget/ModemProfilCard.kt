package com.myxlultimate.component.organism.modemWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_modem_profil_card.view.*

class ModemProfilCard @JvmOverloads constructor(
    context:Context,
    attrs:AttributeSet? = null
):LinearLayout(context, attrs) {



// ----------------------------------------

    var isModemActive: Boolean? = false
        set(value) {
            field = value
            refreshView()
        }

// ----------------------------------------

    var label = ""
        set(value) {
            field = value
            refreshView()
        }

// ----------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()

        }

// ----------------------------------------

    var ssidName = ""
        set(value) {
            field = value
            refreshView()

        }

// ----------------------------------------

    var onActionButtonClick : (() -> Unit)? = null
        set(value) {
            field = value
            actionButtonView.setOnClickListener { value?.invoke() }
        }

// ----------------------------------------

    var warningText = ""
        set(value) {
            field = value
            refreshView()
        }

// ----------------------------------------

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_modem_profil_card, this, true)
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.ModemProfilCardAttr)
        typedArray.run {
            label = getString(R.styleable.ModemProfilCardAttr_label) ?: ""
            title = getString(R.styleable.ModemProfilCardAttr_title) ?: ""
            ssidName = getString(R.styleable.ModemProfilCardAttr_ssidName) ?: ""
            warningText = getString(R.styleable.ModemProfilCardAttr_warningTitle) ?: ""
            isModemActive = getBoolean(R.styleable.ModemProfilCardAttr_isModemActive, false)
        }
        typedArray.recycle()

    }

// ----------------------------------------

private fun refreshView(){
    when (isModemActive) {
        true -> {
            ssidNameView.text = ssidName
            actionButtonView.isEnabled = true
            noticeContainer.visibility = View.GONE
        }
        null -> {
            ssidNameView.text = "-"
            actionButtonView.isEnabled = false
            noticeContainer.visibility = View.VISIBLE
            tvWarningView.text = resources.getString(R.string.modem_status_failed)
        }
        else -> {
            ssidNameView.text = "-"
            actionButtonView.isEnabled = false
            noticeContainer.visibility = View.VISIBLE
            tvWarningView.text = resources.getString(R.string.profile_modem_off_warning)
        }
    }
    labelView.text = label
    titleView.text = title
    tvWarningView.text = warningText
}


}