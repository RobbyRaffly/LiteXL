package com.myxlultimate.component.organism.voucherCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_family_plan_member_type_card.view.*

class FamilyPlanMemberTypeCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs){

    data class Data(
        val name: String,
        val expired: Long,
        val iconImage: String
    )

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            tvTitle.text = value
        }

    // ----------------------------------------------------------------------------------

    var errorMessage = ""
        set(value) {
            field = value
            tvError.text = value
            IsEmptyUtil.setVisibility(value, tvError)
        }

    // ----------------------------------------------------------------------------------

    var actionButtonLabel = ""
        set(value) {
            field = value
            tvButton.text = value
            IsEmptyUtil.setVisibility(value, tvButton)
        }

//    ------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------
    var onActionClick: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(false, tvButton, value)
        }

    // ----------------------------------------------------------------------------------
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_plan_member_type_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FamilyPlanMemberTypeCard)
        typedArray.run {
            title = getString(R.styleable.FamilyPlanMemberTypeCard_title) ?: ""
            errorMessage = getString(R.styleable.FamilyPlanMemberTypeCard_errorMessage)?: ""
            actionButtonLabel = getString(R.styleable.FamilyPlanMemberTypeCard_actionButtonLabel)?:""
        }
        typedArray.recycle()
    }

}