package com.myxlultimate.component.organism.familyPlanInformationCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_family_plan_information_confirmation_card.view.*

class FamilyPlanInformationConfirmCard@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var titleBold = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var subTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var buttonPrimaryAcceptTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var buttonAcceptTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var buttonDeclineTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onAcceptPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(applyButtonAccept,value)
        }

    // ----------------------------------------------------------------------------------

    var onAcceptPrimaryPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(applyButtonPrimaryAccept,value)
        }

    // ----------------------------------------------------------------------------------

    var onDeclinePress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(applyButtonDecline,value)
        }


    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        IsEmptyUtil.setVisibility(title, titleView)
        IsEmptyUtil.setVisibility(titleBold, titleBoldView)
        IsEmptyUtil.setVisibility(subTitle, subTitleView)
        IsEmptyUtil.setVisibility(buttonPrimaryAcceptTitle, applyButtonPrimaryAccept)
        IsEmptyUtil.setVisibility(buttonAcceptTitle, applyButtonAccept)
        IsEmptyUtil.setVisibility(buttonDeclineTitle, applyButtonDecline)

        titleView.text = title
        titleBoldView.text = titleBold
        subTitleView.text = subTitle
        applyButtonPrimaryAccept.text = buttonPrimaryAcceptTitle
        applyButtonAccept.text = buttonAcceptTitle
        applyButtonDecline.text = buttonDeclineTitle
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_plan_information_confirmation_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FamilyPlanInformationConfirmCard)
        typedArray.run {
            title = getString(R.styleable.FamilyPlanInformationConfirmCard_title)?:""
            titleBold = getString(R.styleable.FamilyPlanInformationConfirmCard_titleBold)?:""
            subTitle = getString(R.styleable.FamilyPlanInformationConfirmCard_subtitle)?:""
            buttonAcceptTitle = getString(R.styleable.FamilyPlanInformationConfirmCard_buttonTitleAccept)?:resources.getString(R.string.organism_confirm_family_card_quota_button_title_accept)
            buttonPrimaryAcceptTitle = getString(R.styleable.FamilyPlanInformationConfirmCard_buttonLabelPrimary)?:resources.getString(R.string.organism_confirm_family_card_quota_button_title_accept)
            buttonDeclineTitle = getString(R.styleable.FamilyPlanInformationConfirmCard_buttonTitleDecline)?:resources.getString(R.string.organism_confirm_family_card_quota_button_title_decline)
        }
        typedArray.recycle()

    }
}