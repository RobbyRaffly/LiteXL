package com.myxlultimate.component.template.halfModal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.template_half_modal.view.*
import java.lang.reflect.Array.getInt

class HalfModal @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var halfModalMode = HalfModalMode.NORMAL
    set(value) {
        field = value
        if(halfModalMode == HalfModalMode.SMALLBUTTON) {
            ButtonWrapperSmallButtonMode.visibility = View.VISIBLE
            ButtonWrapper.visibility = View.GONE
        } else {
            ButtonWrapperSmallButtonMode.visibility = View.GONE
            ButtonWrapper.visibility = View.VISIBLE
        }
    }

    // ---------------------------------------------------------------------------------

    var imageSource: Any? = null
        set(value) {
            field = value

            imageView.imageSource = value
            IsEmptyUtil.setVisibility(value, imageView)
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.ASSET
        set(value) {
            field = value
            imageView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value

            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var subtitle = ""
        set(value) {
            field = value

            subtitleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var instruction = ""
        set(value) {
            field = value

            instructionView.text = value
            IsEmptyUtil.setVisibility(value, instructionView)
        }

    // ----------------------------------------------------------------------------------

    var primaryButtonText = ""
        set(value) {
            field = value

            primaryButtonView.text = value
            smallPrimaryButtonView.text = value
            smallPrimaryLeftButtonView.text = value
        }

    // ----------------------------------------------------------------------------------

    var secondaryButtonText = ""
        set(value) {
            field = value

            secondaryButtonView.text = value
            secondaryTopButtonView.text = value
            smallSecondaryButtonView.text = value
            IsEmptyUtil.setVisibility(value, secondaryButtonView)
            IsEmptyUtil.setVisibility(value, smallSecondaryButtonView)
        }

    // ----------------------------------------------------------------------------------

    var onPrimaryButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(primaryButtonView, value)
            TouchFeedbackUtil.attach(smallPrimaryButtonView, value)
            TouchFeedbackUtil.attach(smallPrimaryLeftButtonView, value)
            TouchFeedbackUtil.attach(smallPrimaryLeftButtonView, value)
        }

    // ----------------------------------------------------------------------------------

    var onSecondaryButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(secondaryButtonView, value)
            TouchFeedbackUtil.attach(smallSecondaryButtonView, value)
            TouchFeedbackUtil.attach(secondaryTopButtonView, value)
        }

    // ----------------------------------------------------------------------------------

    var isSwitchButton = false
        set(value) {
            field = value
            if(value) {
                smallPrimaryLeftButtonView.visibility = View.VISIBLE
                smallPrimaryButtonView.visibility = View.GONE
                secondaryTopButtonView.visibility = View.VISIBLE
                secondaryButtonView.visibility = View.GONE
            } else {
                smallPrimaryLeftButtonView.visibility = View.GONE
                smallPrimaryButtonView.visibility = View.VISIBLE
                secondaryTopButtonView.visibility = View.GONE
                secondaryButtonView.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    fun getSubtitleTextView(): TextView? {
        return subtitleView
    }

    fun getTitleTextView(): TextView? {
        return titleView
    }

    fun getSecondaryButtonView(): Button? {
        return secondaryButtonView
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_half_modal, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.halfModalAttr)
        typedArray.run {
            title = getString(R.styleable.halfModalAttr_title) ?: ""
            subtitle = getString(R.styleable.halfModalAttr_subtitle) ?: ""
            instruction = getString(R.styleable.halfModalAttr_instruction) ?: ""
            primaryButtonText = getString(R.styleable.halfModalAttr_buttonText) ?: ""
            secondaryButtonText = getString(R.styleable.halfModalAttr_secondaryButtonText) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.halfModalAttr_imageSourceType, 0)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.halfModalAttr_imageSource)
            } else {
                getString(R.styleable.halfModalAttr_imageSource) ?: ""
            }
            halfModalMode = HalfModalMode.values()[getInt(R.styleable.halfModalAttr_halfModalMode,0)]
            isSwitchButton = getBoolean(R.styleable.halfModalAttr_isSwitchButton, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(primaryButtonView, onPrimaryButtonPress)
        TouchFeedbackUtil.attach(secondaryButtonView, onSecondaryButtonPress)

        TouchFeedbackUtil.attach(smallPrimaryButtonView, onPrimaryButtonPress)
        TouchFeedbackUtil.attach(smallSecondaryButtonView, onSecondaryButtonPress)
    }

    // ----------------------------------------------------------------------------------
}