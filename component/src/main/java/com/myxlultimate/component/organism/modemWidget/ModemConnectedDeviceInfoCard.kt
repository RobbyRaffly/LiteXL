package com.myxlultimate.component.organism.modemWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_modem_connected_device_info.view.*
import kotlinx.android.synthetic.main.organism_modem_profil_card.view.*
import kotlinx.android.synthetic.main.organism_modem_profil_card.view.labelView

class ModemConnectedDeviceInfoCard @JvmOverloads constructor(
    context:Context,
    attrs:AttributeSet? = null
) : LinearLayout (context, attrs){

    // -------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------

    var connectedDeviceCount = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_modem_connected_device_info, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ModemConnectedDeviceInfoAttr)
        typedArray.run {
            label = getString(R.styleable.ModemConnectedDeviceInfoAttr_label) ?: ""
            connectedDeviceCount = getString(R.styleable.ModemConnectedDeviceInfoAttr_connectedDeviceCount) ?: ""
        }
        typedArray.recycle()
    }

    private fun refreshView(){
        labelView.text = label
        connectedDevicesView.text = connectedDeviceCount
    }
}