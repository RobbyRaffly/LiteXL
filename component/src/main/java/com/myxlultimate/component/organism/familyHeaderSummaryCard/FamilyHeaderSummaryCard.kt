package com.myxlultimate.component.organism.familyHeaderSummaryCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_family_header_summary_card.view.*

class FamilyHeaderSummaryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            tvTitle.text = value
        }

    // ----------------------------------------------------------------------------------

    var quotaValue = ""
        set(value) {
            field = value
            tvQuotaValue.text = value
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            tvDescription.text = value
        }

    // ----------------------------------------------------------------------------------

    var isDisableViewDetail = false
        set(value) {
            field = value
            tvBtnDetail.alpha = if (value) 0.5f else 1f
            llNextButton.alpha = if (value) 0.5f else 1f
        }

    // ----------------------------------------------------------------------------------

    var buttonDetailLabel = ""
        set(value) {
            field = value
            tvBtnDetail.text = value
        }

    // ----------------------------------------------------------------------------------

    var onButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(clBaseBtnDetail, value)
        }

    // ----------------------------------------------------------------------------------


    var isShimmerOn = false
        set(value) {
            field = value
            if(value){
                shimmerLayout.apply {
                    startShimmer()
                    visibility = View.VISIBLE
                }
                clBaseConstraint.visibility = View.GONE
            }
            else{
                shimmerLayout.apply {
                    stopShimmer()
                    visibility = View.GONE
                }
                clBaseConstraint.visibility = View.VISIBLE

            }
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_header_summary_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FamilyHeaderSummaryCard)
        typedArray.run {
            title = getString(R.styleable.FamilyHeaderSummaryCard_title) ?: ""
            quotaValue = getString(R.styleable.FamilyHeaderSummaryCard_quotaValue) ?: ""
            description = getString(R.styleable.FamilyHeaderSummaryCard_description) ?: ""
            buttonDetailLabel = getString(R.styleable.FamilyHeaderSummaryCard_buttonDetailLabel) ?: ""
            isShimmerOn = getBoolean(R.styleable.FamilyHeaderSummaryCard_isShimmerOn, false)
            isDisableViewDetail = getBoolean(R.styleable.FamilyHeaderSummaryCard_isDisableViewDetail, false)
        }
        typedArray.recycle()
        TouchFeedbackUtil.attach(clBaseBtnDetail, onButtonPress)

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}