package com.myxlultimate.component.organism.dashboardWidget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_info_message.view.*


class InfoMessageWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var textInfo: String = ""
        set(value) {
            field = value
            refreshView()
        }

    var backgroundColorInfo = R.color.redSoft
        set(value) {
            field = value
            refreshView()
        }

    var textColorInfo = R.color.basicRed
        set(value) {
            field = value
            refreshView()
        }

    var isShowView = true
        set(value) {
            field = value

            if (!value) {
                backgroundView.animate()
                    .translationY(0f)
                    .alpha(0.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            backgroundView.visibility = View.GONE
                            backgroundView.animation = null
                        }
                    })

            } else {
                backgroundView.animate()
                    .translationY(0f)
                    .alpha(1.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            backgroundView.visibility = View.VISIBLE
                            backgroundView.animation = null
                        }
                    })
            }
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_info_message, this, true)

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.InfoMessage)
        typedArray.run {
            textInfo = getString(R.styleable.InfoMessage_label) ?: ""
            backgroundColorInfo = getResourceId(
                R.styleable.InfoMessage_backgroundColor,
                R.color.redSoft
            )
            textColorInfo = getResourceId(
                R.styleable.InfoMessage_textColor,
                R.color.basicRed
            )
        }
        typedArray.recycle()

        refreshView()
    }

    fun refreshView() {
        textInfoView.text = textInfo

        backgroundView.setBackgroundColor(
            ContextCompat.getColor(context, backgroundColorInfo)
        )

        textInfoView.setTextColor(
            ContextCompat.getColor(context, textColorInfo)
        )
    }
}