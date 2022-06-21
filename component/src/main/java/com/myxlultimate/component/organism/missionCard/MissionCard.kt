package com.myxlultimate.component.organism.missionCard

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_mission_card.view.*


class MissionCard constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.BASE64
    set(value) {
        field = value
    }
    // ----------------------------------------------------------------------------------

    var image : Any? = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasProgressFlag = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var missionProgressLabel = ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var validityCustom = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasRightArrow = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isDisabled = false
        set(value) {
            field = value
            setUpOnClick()
        }

    // ----------------------------------------------------------------------------------

    var hasRedDot = false
        set(value) {
            field = value
            refreshView()
//            layoutCircleRedDot.visibility = View.VISIBLE
        }

    // ----------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value
            setUpOnClick()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_mission_card, this, true)


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MissionCard)
        typedArray.run {
//            hasRedDot = getBoolean(R.styleable.MissionCard_hasRedDot, false)
            title = getString(R.styleable.MissionCard_title) ?: ""
            validityCustom = getString(R.styleable.MissionCard_customValidity)?:""
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.MissionCard_imageSourceType, 3)]
            image = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.MissionCard_imageSource)
            } else {
                getString(R.styleable.MissionCard_imageSource)
            }
            hasProgressFlag = getBoolean(R.styleable.MissionCard_hasProgressFlag, false)
            missionProgressLabel = getString(R.styleable.MissionCard_missionProgressLabel)?:""
            hasRightArrow = getBoolean(R.styleable.MissionCard_hasRightArrow, true)
            isDisabled = getBoolean(R.styleable.MissionCard_isDisabled, false)
        }

        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        imageView.imageSourceType = imageSourceType
        imageView.imageSource = image
        missionProgressParentView.visibility = if (hasProgressFlag) View.VISIBLE else View.GONE
        missionProgressView.text = missionProgressLabel
        titleView.text = title
        validityLabelView.text = validityCustom
        circleView.visibility = if (hasRightArrow) View.VISIBLE else View.GONE
        layoutCircleRedDot.visibility =
            if (hasRedDot)
                View.VISIBLE
            else
                View.GONE
    }

    private fun setUpOnClick() {
        val typedValue = TypedValue()
        context.theme?.resolveAttribute(R.attr.colorBackgroundPrimary, typedValue, true)
        val color = ContextCompat.getColor(context, typedValue.resourceId)

        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_pressed)
        )
        val colors = intArrayOf(
            color,
            color,
            color,
            color
        )
        val myList = ColorStateList(states, colors)
        missionProgressParentView.backgroundTintList =
            if (isDisabled)
                ContextCompat.getColorStateList(context, R.color.mud_palette_basic_medium_grey)
            else myList
        TouchFeedbackUtil.attach(cardView, onCardPress)
    }
}