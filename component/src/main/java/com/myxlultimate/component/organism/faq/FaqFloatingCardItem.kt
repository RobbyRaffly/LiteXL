package com.myxlultimate.component.organism.faq

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_faq_floating_card.view.*

class FaqFloatingCardItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String
    )

    var title = ""
        set(value) {
            field = value
            tvTitle.text = value
        }

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(this, value)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_faq_floating_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FaqFloatingCardItem)
        typedArray.run {
            title = getString(R.styleable.FaqFloatingCardItem_title) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(this, onPress)
    }

}