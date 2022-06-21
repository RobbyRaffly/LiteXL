package com.myxlultimate.component.organism.loginOptionCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_login_option_card.view.*

class LoginOptionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var icon = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var subtitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        iconView.text = icon
        titleView.text = title
        subtitleView.text = subtitle
        IsEmptyUtil.setVisibility(subtitle,subtitleView)
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_login_option_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginOptionCardAttr)
        typedArray.run {
            icon = getString(R.styleable.LoginOptionCardAttr_icon) ?: ""
            title = getString(R.styleable.LoginOptionCardAttr_title) ?: ""
            subtitle = getString(R.styleable.LoginOptionCardAttr_subtitle) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView, onPress)
    }

}