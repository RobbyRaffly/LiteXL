package com.myxlultimate.component.organism.modemWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.template.cardWidget.CardMode
import kotlinx.android.synthetic.main.organism_modem_error_state_widget.view.*

class ModemDeviceErrorWidget(
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {

    var label = ""
        set(value) {
            field = value
            refreshView()
        }

// ---------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            refreshView()
        }

// ---------------------------------------------------------------------------------

    var bottomCta = ""
        set(value) {
            field = value
            refreshView()
        }

// ---------------------------------------------------------------------------------

    var rightIcon = getDrawable(context,R.drawable.ic_tbs_acs_home)
        set(value) {
            field = value
            refreshView()
        }

// ---------------------------------------------------------------------------------

    var onBottomCtaClicked: (() -> Unit)? = null
        set(value) {
            field = value
            containerView.onActionButtonPress = value
        }

// ---------------------------------------------------------------------------------

    var cardMode = CardMode.NORMAL
        set(value) {
            field = value
            containerView.cardMode = value
        }

// ---------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context).inflate(R.layout.organism_modem_error_state_widget, this, true)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ModemDeviceErrorWidget)
        typedArray.run {
            label = getString(R.styleable.ModemDeviceErrorWidget_label) ?: ""
            information = getString(R.styleable.ModemDeviceErrorWidget_information) ?: ""
            bottomCta = getString(R.styleable.ModemDeviceErrorWidget_bottomCtaLabel) ?: ""
            rightIcon = typedArray.getDrawable(R.styleable.ModemDeviceErrorWidget_rightIcon)
            cardMode = CardMode.values()[getInt(R.styleable.ModemDeviceErrorWidget_cardMode, 0)]
        }
        typedArray.recycle()
    }

    private fun refreshView() {
        containerView.actionButtonLabel = bottomCta
        contentView.text = information
        containerView.label = label
        containerView.icon = rightIcon
    }
}