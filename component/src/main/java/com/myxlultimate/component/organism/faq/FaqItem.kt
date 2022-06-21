package com.myxlultimate.component.organism.faq

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_faq_item.view.*
import kotlinx.android.synthetic.main.organism_faq_item.view.shimmerLayout

class FaqItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val question: String,
        val hasBottomLine: Boolean,
        val isShimmerOn: Boolean? = false,
        val hasRightArrow : Boolean? = true
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var hasRightArrow = true
    set(value) {
        field = value
       refreshView()
    }
    // ----------------------------------------------------------------------------------

    var question = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(this, value)
        }


    // ----------------------------------------------------------------------------------

    var hasBottomLine = true
        set(value) {
            field = value
           refreshView()
        }

    // ----------------------------------------------------------------------------------


    var isShimmerOn = false
        set(value) {
            field = value
            if(value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
            mainView.visibility = if(value) View.GONE else View.VISIBLE
            shimmerLayout.visibility = if(value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        questionView.text = question
        IsEmptyUtil.setVisibility(hasBottomLine, lineView)
        iconView.visibility = if(hasRightArrow) View.VISIBLE else View.GONE
        if(hasRightArrow) {
            TouchFeedbackUtil.attach(this, onPress)
        } else {
            TouchFeedbackUtil.detach(this)
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_faq_item, this, true)

        orientation = VERTICAL

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FaqItemAttr)
        typedArray.run {
            question = getString(R.styleable.FaqItemAttr_question) ?: ""
            hasBottomLine = getBoolean(R.styleable.FaqItemAttr_hasBottomLine,true)
            isShimmerOn = getBoolean(R.styleable.FaqItemAttr_isShimmerOn,false)
            hasRightArrow = getBoolean(R.styleable.FaqItemAttr_hasRightArrow,true)
        }
        typedArray.recycle()

    }
}