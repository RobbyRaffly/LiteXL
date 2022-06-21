package com.myxlultimate.component.organism.noticeCard

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_notice_top_round_card.view.*

class NoticeTopRoundCard@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var setNoticeBackgroundColor : Int? = null
        set(value) {
            field = value
            value?.let {
                noticeTopRoundCard.backgroundTintList = ColorStateList.valueOf(it)
            }
        }

    // ----------------------------------------------------------------------------------

    var title = ""
    set(value) {
        field = value
        titleText.text = value
    }

    // ----------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------
    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_notice_top_round_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.noticeCardAttr)
        typedArray.run {
            title = getString(R.styleable.noticeCardAttr_title)?:""
        }
        typedArray.recycle()
    }



}