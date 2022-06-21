package com.myxlultimate.component.template.segmentWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_fun_segment.view.*

class FunSegment @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    var buttonTitle = ""
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

    var leftTopIcon = ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------


    var showLineView = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    var onButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(buttonView, value)
        }

    // ----------------------------------------------------------------------------------


    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
            shimmerView.visibility = if (value) View.VISIBLE else View.GONE
            labelTextView.visibility = if (value) View.GONE else View.VISIBLE
            labelSubTextView.visibility = if (value) View.GONE else View.VISIBLE
            buttonView.visibility = if (value) View.GONE else View.VISIBLE
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    init {

        LayoutInflater.from(context).inflate(R.layout.organism_fun_segment, this, true)

        setBackgroundResource(R.color.mud_palette_basic_white)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StoreSegment,
            0, 0
        ).apply {

            try {
                getString(R.styleable.StoreSegment_label)?.let {
                    title = it
                }

                getString(R.styleable.StoreSegment_action)?.let {
                    buttonTitle = it
                }
            } finally {
                recycle()
            }
        }

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        labelTextView.text = title
        labelSubTextView.text = subtitle
        leftTopIconView.imageSource = leftTopIcon
        labelSubTextView.visibility = if (subtitle.isNotEmpty()) View.VISIBLE else View.GONE
        buttonView.text = buttonTitle
        buttonView.visibility = if (buttonTitle.isNotEmpty()) View.VISIBLE else View.GONE
        lineView.visibility = if (showLineView) View.VISIBLE else View.GONE
        leftTopIconView.visibility = if(leftTopIcon.isNotEmpty()) View.VISIBLE else View.GONE
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (mainView == null) {
            super.addView(child, index, params)
        } else {
            mainView.addView(child, index, params)
        }
    }

}
