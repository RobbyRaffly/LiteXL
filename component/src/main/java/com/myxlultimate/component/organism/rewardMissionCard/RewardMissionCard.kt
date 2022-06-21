package com.myxlultimate.component.organism.rewardMissionCard

import android.content.Context
import android.text.method.Touch
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.missionCard.enum.RewardStatus
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_reward_mission_card.view.*

class RewardMissionCard @JvmOverloads constructor(
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

    var information = ""
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
    var imageSource: Any? = getDrawable(context, R.drawable.ic_redemption_entry_postpaid_new)
        set(value) {
            field = value
            setOnClick()
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            imageView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var rewardStatus = RewardStatus.ENABLE
        set(value) {
            field = value
            setOnClick()
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            setOnClick()
        }

    // ----------------------------------------------------------------------------------

    var hasInformationIcon = false
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
// ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_reward_mission_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RewardMissionCard)
        typedArray.run {
            title = getString(R.styleable.RewardMissionCard_title) ?: ""
            information = getString(R.styleable.RewardMissionCard_information) ?: ""
            buttonTitle = getString(R.styleable.RewardMissionCard_buttonTitle) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(
                R.styleable.RewardMissionCard_imageSourceType,
                2
            )]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.RewardMissionCard_imageSourceType)
                    ?: AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_redemption_entry_postpaid_new
                    )
            } else {
                getString(R.styleable.RewardMissionCard_imageSourceType) ?: ""
            }
            rewardStatus =
                RewardStatus.values()[getInt(R.styleable.RewardMissionCard_rewardStatus, 0)]
            hasRightArrow = getBoolean(R.styleable.RewardMissionCard_hasRightArrow, true)
        }

        typedArray.recycle()
    }

    private fun refreshView() {
        titleView.text = title
        informationView.text = information
        buttonTitleView.text = buttonTitle
        buttonTitleDisabledView.text = buttonTitle
        circleView.visibility = if (hasRightArrow) View.VISIBLE else View.INVISIBLE
        informationIconView.visibility = if (hasInformationIcon) View.VISIBLE else View.GONE
    }

    private fun setOnClick() {
        when (rewardStatus) {
            RewardStatus.ENABLE -> {
                TouchFeedbackUtil.attach(cardView, onPress)
                informationView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                buttonTitleView.visibility = View.VISIBLE
                buttonTitleDisabledView.visibility = View.GONE
                imageView.imageSource = imageSource
                imageView.visibility = View.VISIBLE
                lineView.visibility = View.VISIBLE
                bottomView.visibility = View.VISIBLE
            }
            RewardStatus.HAS_REDEEM -> {
                TouchFeedbackUtil.detach(cardView)
                informationView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                buttonTitleView.visibility = View.GONE
                buttonTitleDisabledView.visibility = View.VISIBLE
                imageView.imageSource = imageSource
                imageView.visibility = View.VISIBLE
                lineView.visibility = View.VISIBLE
                bottomView.visibility = View.VISIBLE
            }
            RewardStatus.CUSTOM -> {
                TouchFeedbackUtil.detach(cardView)
                imageView.visibility = if (imageSource == null) View.INVISIBLE else View.VISIBLE
                lineView.visibility = View.VISIBLE
                bottomView.visibility = View.VISIBLE
            }
            RewardStatus.OUT_OF_STOCK -> {
                TouchFeedbackUtil.detach(cardView)
                lineView.visibility = View.GONE
                bottomView.visibility = View.GONE
            }
            else -> {
                TouchFeedbackUtil.detach(cardView)
                informationView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
                buttonTitleView.visibility = View.GONE
                buttonTitleDisabledView.visibility = View.VISIBLE
                imageView.visibility = View.INVISIBLE
                lineView.visibility = View.VISIBLE
                bottomView.visibility = View.VISIBLE
            }
        }
    }
}