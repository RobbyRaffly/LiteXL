package com.myxlultimate.component.organism.hybridModeWidgets

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_hybrid_subscription_widget.view.*

class HybridSubsDashboardWidget(
    context: Context,
    attr : AttributeSet? = null
) : LinearLayout(context, attr) {


    var topLabel = resources.getString(R.string.hybrid_top_label)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var topTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var topSubtitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var midLabel = resources.getString(R.string.hybrid_mid_label)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var midTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var midSubtitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var bottomCtaText = resources.getString(R.string.hybrid_bottom_cta_text)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var bottomCtaAction : (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var topButtonAction : (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var warningText = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_hybrid_subscription_widget, this, true)
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.HybridSubsDashboardWidget)
        typedArray.run {

        }
        typedArray.recycle()

        containerView.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun refreshView(){
        topTitleView.text = topTitle
        topLabelView.text = topLabel
        topSubtitleView.text = topSubtitle
        midLabelView.text = midLabel
        midTitleView.text = midTitle
        midSubtitleView.text = midSubtitle
        bottomCtaView.text = bottomCtaText
        warningTextView.text = warningText

        if (warningText.isEmpty()) {
            warningTextViewContainer.visibility = View.INVISIBLE
            midSubtitleView.visibility = View.VISIBLE
            topRightIconView.visibility = View.VISIBLE
            topButtonView.visibility = View.GONE
            topTitleView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                endToEnd = ConstraintSet.PARENT_ID
            }
        } else {
            warningTextViewContainer.visibility = View.VISIBLE
            midSubtitleView.visibility = View.INVISIBLE
            topRightIconView.visibility = View.INVISIBLE
            topButtonView.visibility = View.VISIBLE
            topTitleView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                endToStart = topButtonView.id
            }
        }

        TouchFeedbackUtil.attach(bottomCtaView, bottomCtaAction)
        topButtonView.setOnClickListener {
            topButtonAction?.invoke()
        }
    }

}