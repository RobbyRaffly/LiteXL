package com.myxlultimate.component.organism.roamingServiceStatusCard

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.google.android.gms.common.internal.ResourceUtils
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.roamingServiceStatusCard.enum.RoamingServiceMode
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_roaming_service_status_card.view.*

class RoamingServiceStatusCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            tvTitle.text = value
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            tvDescription.text = value
        }

    // ----------------------------------------------------------------------------------

    var isButtonVisible = true
        set(value) {
            field = value
            if (value){
                tvRoamingSetting.visibility = View.VISIBLE
            } else {
                tvRoamingSetting.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------


    var roamingStatus = RoamingServiceMode.ACTIVE
        set(value) {
            field = value
            if (value == RoamingServiceMode.ACTIVE) {
                ivRoamingStatus.setImageDrawable(imageAttribute(R.attr.iconRoamingActive))
            } else {
                ivRoamingStatus.setImageDrawable(imageAttribute(R.attr.iconRoamingNonActive))
            }
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(tvRoamingSetting, value)
        }

    // ----------------------------------------------------------------------------------

    var minimalView = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_roaming_service_status_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoamingServiceStatusCard)
        typedArray.run {
            title = getString(R.styleable.RoamingServiceStatusCard_title) ?: ""
            description = getString(R.styleable.RoamingServiceStatusCard_description) ?: ""
            isButtonVisible = getBoolean(R.styleable.RoamingServiceStatusCard_isButtonVisible, true)
            roamingStatus =
                RoamingServiceMode.values()[getInt(
                    R.styleable.RoamingServiceStatusCard_roamingStatus,
                    0
                )]

            minimalView = getBoolean(R.styleable.RoamingServiceStatusCard_minimalView, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(tvRoamingSetting, onPress)
    }

    // ----------------------------------------------------------------------------------

    private fun imageAttribute(@AttrRes iconAttribute: Int): Drawable? {
        return ContextCompat.getDrawable(context, TypedValue().apply {
            context.theme.resolveAttribute(iconAttribute, this, true)
        }.resourceId)
    }

    // ----------------------------------------------------------------------------------

    fun refreshView(){
        if (minimalView){
            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.confirmationIcon, typedValue, true)
            val drawable = ContextCompat.getDrawable(context, typedValue.resourceId)

            ivRoamingStatus.setImageDrawable(drawable)
            tvDescription.visibility = View.GONE
            tvRoamingSetting.visibility = View.GONE
            tvTitle.text = resources.getString(R.string.modem_having_problem)
            setViewLayout()
        }
    }

    private fun setViewLayout() {
        containerView.updatePadding(
            top = 0,
            bottom = 0,
            right = 0
        )
    }

    // ----------------------------------------------------------------------------------

}