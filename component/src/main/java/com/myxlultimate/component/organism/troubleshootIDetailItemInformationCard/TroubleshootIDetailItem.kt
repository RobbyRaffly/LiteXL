package com.myxlultimate.component.organism.troubleshootIDetailItemInformationCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_troubleshoot_detail_item.view.*

class TroubleshootIDetailItem@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val leftTitle : String,
        val rightTitle : String
    )
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var leftTitle = ""
        set(value) {
            field = value
            leftTextView.text = value
        }
    // ----------------------------------------------------------------------------------

    var rightTitle = ""
        set(value) {
            field = value
            rightTextView.text = value
        }
    // ----------------------------------------------------------------------------------


    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(rightTextView, value)
        }

    // ----------------------------------------------------------------------------------


// ----------------------------------------------------------------------------------
    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_troubleshoot_detail_item, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TroubleshootIDetailItem)
        typedArray.run {
            leftTitle = getString(R.styleable.TroubleshootIDetailItem_leftText)?:""
            rightTitle = getString(R.styleable.TroubleshootIDetailItem_rightText)?:""
        }
        typedArray.recycle()

    }
}