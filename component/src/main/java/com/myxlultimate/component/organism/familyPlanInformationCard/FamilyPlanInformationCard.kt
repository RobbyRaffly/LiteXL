package com.myxlultimate.component.organism.familyPlanInformationCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_family_plan_information_card.view.*

class FamilyPlanInformationCard@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var imageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource : Any? = ""
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

    var quotaLimit = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var quotaUsed = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var buttonSettingTitle = resources.getString(R.string.organism_family_plan_information_card_arrange)
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(cardView,value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        iconView.imageSourceType = imageSourceType
        iconView.imageSource = imageSource
        titleView.text = title
        quotaLimitView.text = quotaLimit
        quotaUsedView.text = quotaUsed
        settingView.text = buttonSettingTitle
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_plan_information_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FamilyPlanInformationCard)
        typedArray.run {
            imageSourceType =
                ImageSourceType.values()[getInt(R.styleable.FamilyPlanInformationCard_imageSourceType, 3)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.FamilyPlanInformationCard_imageSource)
            } else {
                getString(R.styleable.FamilyPlanInformationCard_imageSource)
            }
            title = getString(R.styleable.FamilyPlanInformationCard_title)?:""
            quotaLimit = getString(R.styleable.FamilyPlanInformationCard_quotaLimitText)?:""
            quotaUsed = getString(R.styleable.FamilyPlanInformationCard_quotaUsedText)?:""
            buttonSettingTitle = getString(R.styleable.FamilyPlanInformationCard_buttonTitle)?: resources.getString(R.string.organism_family_plan_information_card_arrange)
        }
        typedArray.recycle()

    }
}