package com.myxlultimate.component.organism.missionHistoryCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_badged_icon.view.*
import kotlinx.android.synthetic.main.organism_mission_history_card.view.*

class MissionHistoryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    var unRedeemCount = 0
        set(value) {
            field = value
            refreshView()
        }

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(cvContainer, value)
        }

    private fun refreshView() {
        tvTitle.text = title
        badgedIconNotification.visibility = if (unRedeemCount>0) View.VISIBLE else View.INVISIBLE
        if(unRedeemCount > 99){
            val layoutParamsRatio = badgedIconNotification.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsRatio.width = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParamsRatio.height = 0
            badgedIconNotification.layoutParams = layoutParamsRatio
        }
        badgedIconNotificationTitle.setText(unRedeemCount.toString())
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_mission_history_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MissionHistoryCard)
        typedArray.run {
            title = getString(R.styleable.MissionHistoryCard_title) ?: ""
            unRedeemCount = getInt(R.styleable.MissionHistoryCard_unRedeemCount, 0)
        }
        typedArray.recycle()
    }
}