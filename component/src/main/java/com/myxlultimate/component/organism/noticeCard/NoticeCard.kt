package com.myxlultimate.component.organism.noticeCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_notice_card.view.*

class NoticeCard@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private var mode = Mode.SUCCESS

    // ----------------------------------------------------------------------------------

    var onClickListener: (() -> Unit)? = null
    set(value) {
        field = value
        TouchFeedbackUtil.attach(NoticeCardView, value)
    }

    // ----------------------------------------------------------------------------------

    var hasArrow = true
    set(value) {
        field = value
        NoticeCardRightIconView.visibility = if(hasArrow) View.VISIBLE else View.GONE
        if(!hasArrow) {
            TouchFeedbackUtil.detach(NoticeCardView)
        }

    }

    // ----------------------------------------------------------------------------------

    var title = ""
    set(value) {
        field = value
        NoticeCardTitleView.setText(value as String)
    }

    // ----------------------------------------------------------------------------------

    var cardMode = ""
    set(value) {
        field = value
        mode = mode.build(value)
        mode.setBackgroundColor(context, NoticeCardView)
        mode.setComponentColor(NoticeCardRightIconView,NoticeCardTitleView,NoticeCardLeftIconView)
    }
    // ----------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------
    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_notice_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.noticeCardAttr)
        typedArray.run {
            cardMode = getString(R.styleable.noticeCardAttr_mode)?:""
            title = getString(R.styleable.noticeCardAttr_title)?:""
            hasArrow = getBoolean(R.styleable.noticeCardAttr_hasArrow,true)
        }
        typedArray.recycle()

        if(hasArrow) TouchFeedbackUtil.attach(NoticeCardView, onClickListener)
    }
}