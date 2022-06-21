package com.myxlultimate.component.organism.bizOnItem.bizOnLanding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaType
import com.myxlultimate.component.molecule.quotaSummary.QuotaSummaryItem
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_biz_on_quota_summary_card.view.*

class BizOnQuotaSummaryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    val quotaSummaryItemData: QuotaSummaryItem by lazy { findViewById<QuotaSummaryItem>(R.id.quotaSummaryItemData) }
    val quotaSummaryItemVoice: QuotaSummaryItem by lazy { findViewById<QuotaSummaryItem>(R.id.quotaSummaryItemVoice) }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var actionLabel = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var dataValue = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var voiceValue = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isDataUnlimited = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isVoiceUnlimited = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isBottomView = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var labelTitleBottom = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var labelDescBottom = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onPressBottomView: (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_on_quota_summary_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BizOnQuotaSummaryCard)
        typedArray.run {
            title = getString(R.styleable.BizOnQuotaSummaryCard_title) ?: ""
            actionLabel = getString(R.styleable.BizOnQuotaSummaryCard_actionLabel) ?: ""
            dataValue = getInt(R.styleable.BizOnQuotaSummaryCard_dataValue, 0)
            voiceValue = getInt(R.styleable.BizOnQuotaSummaryCard_voiceValue, 0)
            isDataUnlimited = getBoolean(R.styleable.BizOnQuotaSummaryCard_isDataUnlimited, false)
            isVoiceUnlimited = getBoolean(R.styleable.BizOnQuotaSummaryCard_isVoiceUnlimited, false)
            isBottomView = getBoolean(R.styleable.BizOnQuotaSummaryCard_isBottomView, false)
            labelTitleBottom = getString(R.styleable.BizOnQuotaSummaryCard_labelTitleBottom) ?: resources.getString(R.string.xl_bizon_landing_title_bottom)
            labelDescBottom = getString(R.styleable.BizOnQuotaSummaryCard_labelDescBottom) ?: resources.getString(R.string.xl_bizon_landing_desc_bottom)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        tvTitle.text = title
        tvAction.text = actionLabel
        quotaSummaryItemData.apply {
            quotaType = QuotaType.DATA
            remaining = dataValue
            isUnlimited = isDataUnlimited
        }
        quotaSummaryItemVoice.apply {
            quotaType = QuotaType.VOICE
            remaining = voiceValue
            isUnlimited = isVoiceUnlimited
        }
        if (isBottomView){
            bottomView.visibility = View.VISIBLE
        } else {
            bottomView.visibility = View.GONE
        }

        TouchFeedbackUtil.attach(tvAction, onPress)
        TouchFeedbackUtil.attach(bottomView, onPressBottomView)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}