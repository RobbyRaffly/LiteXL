package com.myxlultimate.component.organism.networkCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_network_card.view.*

class NetworkCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value
            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var iconCode = ""
        set(value) {
            field = value
            iconView.text = value
        }


    // ----------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(cardView,value)
        }


    // ----------------------------------------------------------------------------------

    var hasLookHereButton = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value,lookHereView)
        }

    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            if (!hasLookHereButton) endIconImageView.visibility = View.VISIBLE

            field = value
            if(isActive) {
                endIconImageView.imageSource = getDrawable(context,R.drawable.ic_success_active)
            }
            else {
                endIconImageView.visibility = View.GONE
                hasLookHereButton = true
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_network_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NetworkCardAttr)
        typedArray.run {
            name = getString(R.styleable.NetworkCardAttr_name) ?: ""
            iconCode = getString(R.styleable.NetworkCardAttr_iconCode)?:""
            hasLookHereButton = getBoolean(R.styleable.NetworkCardAttr_hasLookHere,false)
            isActive = getBoolean(R.styleable.NetworkCardAttr_isActive,false)
        }

        TouchFeedbackUtil.attach(cardView,onCardPress)

    }
}