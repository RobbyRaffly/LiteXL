package com.myxlultimate.component.organism.noticeInformationCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_notification_information_card.view.*

class NotificationInformationCard constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
    set(value) {
        field = value
        titleView.text = value
    }

    // ----------------------------------------------------------------------------------

    var information = ""
    set(value) {
        field = value
        informationView.text = value
    }

    // ----------------------------------------------------------------------------------

    var titleColor : Int = R.color.mud_palette_basic_red
        set(value) {
            field = value
            titleView.setTextColor(ContextCompat.getColor(context, value))
        }

    // ----------------------------------------------------------------------------------

    var informationColor : Int = R.color.mud_palette_basic_red
        set(value) {
            field = value
            informationView.setTextColor(ContextCompat.getColor(context, value))
        }

    // ----------------------------------------------------------------------------------

    var bgColorTint : Int = R.color.mud_palette_red_soft
        set(value) {
            field = value
            llNoticeCard.background.setTint(
                ContextCompat.getColor(
                    context,
                    value
                )
            )
        }

    // ----------------------------------------------------------------------------------

    var hasCloseButton = false
    set(value) {
        field = value
        closeBtnView.visibility = if(value) View.VISIBLE else View.GONE
    }

    // ----------------------------------------------------------------------------------

    var onClosePress: (() -> Unit)? = null
    set(value) {
        field = value
        TouchFeedbackUtil.attach(closeBtnView,value)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_notification_information_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.NotificationInformationCard)
        typedArray.run {
            title = getString(R.styleable.NotificationInformationCard_title)?:""
            information = getString(R.styleable.NotificationInformationCard_information)?:""
            hasCloseButton = getBoolean(R.styleable.NotificationInformationCard_hasCloseButton,false)
            titleColor = getResourceId(R.styleable.NotificationInformationCard_titleColor,R.color.mud_palette_basic_red)
            informationColor = getResourceId(R.styleable.NotificationInformationCard_informationColor,R.color.mud_palette_basic_red)
            bgColorTint = getResourceId(R.styleable.NotificationInformationCard_bgColorTint,R.color.mud_palette_red_soft)
        }
        typedArray.recycle()
    }
}