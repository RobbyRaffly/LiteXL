package com.myxlultimate.component.organism.rewardInformationCard

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.template.cardWidget.IconMode
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_reward_information_card.view.*

class RewardInformationCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var buttonTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var imageSource = R.drawable.ic_rewards_prepaid
        set(value) {
            field = value
            cardWidget.iconMode = IconMode.FULL_RIGHT
            cardWidget.icon = getDrawable(context, value)
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            cardWidget.onActionButtonPress = value
        }
// ----------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.text = title
        cardWidget.labelLight = label
        cardWidget.actionButtonLabel = buttonTitle

    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_reward_information_card, this, true)

        orientation = VERTICAL

        val typedValue = TypedValue()
        context.theme?.resolveAttribute(R.attr.colorBackgroundPrimary, typedValue, true)
        val color = ContextCompat.getColor(context, typedValue.resourceId)
        cardWidget.actionButtonColor = color

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RewardInformationCard)
        typedArray.run {
            label = getString(R.styleable.RewardInformationCard_label) ?: ""
            title = getString(R.styleable.RewardInformationCard_title) ?: ""
            buttonTitle = getString(R.styleable.RewardInformationCard_buttonTitle) ?: ""
            imageSource = getResourceId(R.styleable.RewardInformationCard_imageSource, 0)
        }

        typedArray.recycle()
    }
}