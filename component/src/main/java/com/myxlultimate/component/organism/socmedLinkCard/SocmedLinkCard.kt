package com.myxlultimate.component.organism.socmedLinkCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.accountLinkCard.SocmedType
import com.myxlultimate.component.organism.transactionSummary.RowValueType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_socmed_link_card.view.*

class SocmedLinkCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var name = ""
        set(value) {
            field = value

            if(name.isNotEmpty()) {
                nameView.text = value
            }

            isLinked = name.isNotEmpty()
        }

    // ----------------------------------------------------------------------------------

    var socmedType = SocmedType.FACEBOOK
        set(value) {
            field = value

            iconView.imageSource = resources.getDrawable(socmedType.imageDrawable,null)
            name = socmedType.socialMediaName
            actionButtonView.text = (resources.getString(R.string.molecule_account_link_card_link))
        }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: ((Boolean) -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(actionButtonView) { value?.let { it(!isLinked) } }
        }

    // ----------------------------------------------------------------------------------

    private var isLinked = false
        set(value) {
            field = value

            if (value) {
                actionButtonView.text =
                    (resources.getString(R.string.molecule_account_link_card_erase))
            }
        }

    // ----------------------------------------------------------------------------------

    var information = ""
    set(value) {
        field = value
        informationView.apply {
            text = value
            IsEmptyUtil.setVisibility(value,this)
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_socmed_link_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SocmedLinkCardAttr)
        typedArray.run {
            socmedType = SocmedType.values()[getInt(R.styleable.SocmedLinkCardAttr_socmedType, 0)]
            name = getString(R.styleable.SocmedLinkCardAttr_name)?:""
            information = getString(R.styleable.SocmedLinkCardAttr_information)?:""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(actionButtonView) { onActionButtonPress?.let { it(!isLinked) } }
    }

}