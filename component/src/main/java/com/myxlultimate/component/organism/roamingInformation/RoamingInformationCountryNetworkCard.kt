package com.myxlultimate.component.organism.roamingInformation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_roaming_information_service_card.view.*

class RoamingInformationCountryNetworkCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            titleInformation.text = value
        }

    // ----------------------------------------------------------------------------------

    var country = ""
        set(value) {
            field = value
            textCountry.text = value
        }

    // ----------------------------------------------------------------------------------

    var countryValue = ""
        set(value) {
            field = value
            countryName.text = value
        }

    // ----------------------------------------------------------------------------------

    var network = ""
        set(value) {
            field = value
            textNetwork.text = value
        }

    // ----------------------------------------------------------------------------------

    var networkValue = ""
        set(value) {
            field = value
            networkName.text = value
        }

    // ----------------------------------------------------------------------------------

    var errorNetwork = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, layoutInfoWarning)
            textErrorNetwork.text = value
        }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_roaming_information_service_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.RoamingInformationCountryNetworkCard)
        typedArray.run {
            title = getString(R.styleable.RoamingInformationCountryNetworkCard_title)?:""
            country = getString(R.styleable.RoamingInformationCountryNetworkCard_country)?:""
            countryValue = getString(R.styleable.RoamingInformationCountryNetworkCard_countryValue)?:""
            network = getString(R.styleable.RoamingInformationCountryNetworkCard_network)?:""
            networkValue = getString(R.styleable.RoamingInformationCountryNetworkCard_networkValue)?:""
            errorNetwork = getString(R.styleable.RoamingInformationCountryNetworkCard_errorNetwork)?:""
        }
        typedArray.recycle()
    }

}