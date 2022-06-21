package com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_biz_optimus_quota_card.view.*
import kotlinx.android.synthetic.main.organism_biz_optimus_quota_card.view.actionButtonLabelView
import kotlinx.android.synthetic.main.organism_biz_optimus_quota_card.view.actionButtonView
import kotlinx.android.synthetic.main.organism_biz_optimus_quota_card.view.bottomView
import kotlinx.android.synthetic.main.template_card_widget.view.*

class MemberInfoQuotaCard @JvmOverloads constructor(
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

    var imageSourceType: ImageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            iconView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource: Any? = ContextCompat.getDrawable(context, R.drawable.ic_data_prio)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var information1 = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var value1 = ""
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------

    var information2 = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var value2 = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasBottomView = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var bottomInformationText = ""
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(actionButtonView, value)
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_optimus_quota_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActiveMemberInfoCard)
        typedArray.run {
            title = getString(R.styleable.ActiveMemberInfoCard_title) ?: ""
            imageSource = getDrawable(R.styleable.ActiveMemberInfoCard_imageDrawable)
            value1 = getString(R.styleable.ActiveMemberInfoCard_value) ?: ""
            value2 = getString(R.styleable.ActiveMemberInfoCard_otherValue) ?: ""
            information1 = getString(R.styleable.ActiveMemberInfoCard_information) ?: ""
            information2 = getString(R.styleable.ActiveMemberInfoCard_otherInformation) ?: ""

            bottomInformationText =
                getString(R.styleable.ActiveMemberInfoCard_bottomInformation) ?: ""
            hasBottomView = getBoolean(R.styleable.ActiveMemberInfoCard_hasBottomInformation, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(actionButtonView, onActionButtonPress)

    }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        iconView.imageSource = imageSource
        iconView.imageSourceType = imageSourceType

        nameView.text = title
        informationValue1.text = value1
        informationValue2.text = value2
        informationTitle1.text = information1
        informationTitle2.text = information2

        if (hasBottomView && bottomInformationText.isNotEmpty()) {
            bottomView.visibility = View.VISIBLE

            actionButtonLabelView.text = bottomInformationText
        } else {
            bottomView.visibility = View.GONE
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}