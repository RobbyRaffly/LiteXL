package com.myxlultimate.component.template.fullModal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_status_payment_card.view.*
import kotlinx.android.synthetic.main.template_full_modal.view.*

class FullModal @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    var imageSourceType = ImageSourceType.ASSET
        set(value) {
            field = value
            imageView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource : Any? = ""
        set(value) {
            field = value

            imageView.imageSource = value
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

    var buttonTextTitle = ""
        set(value) {
            field = value

            textButtonView.text = value
            IsEmptyUtil.setVisibility(value, textButtonView)
        }

    // ----------------------------------------------------------------------------------

    var primaryButtonText = ""
        set(value) {
            field = value

            primaryButtonView.text = value
        }

    // ----------------------------------------------------------------------------------

    var secondaryButtonText = ""
        set(value) {
            field = value

            secondaryButtonView.text = value
            IsEmptyUtil.setVisibility(value, secondaryButtonView)
        }

    // ----------------------------------------------------------------------------------

    var onTextButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(textButtonView, value)
        }

    // ----------------------------------------------------------------------------------

    var onPrimaryButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(primaryButtonView, value)
        }

    // ----------------------------------------------------------------------------------

    var onSecondaryButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(secondaryButtonView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_full_modal, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.fullModalAttr)
        attrs.let {
            imageSourceType = ImageSourceType.values()[typedArray.getInt(R.styleable.fullModalAttr_imageSourceType, 0)]
            if (imageSourceType == ImageSourceType.DRAWABLE) {
                imageSource = typedArray.getDrawable(R.styleable.fullModalAttr_imageSource)
            } else {
                imageSource = typedArray.getString(R.styleable.fullModalAttr_imageSource)
            }
            title = typedArray.getString(R.styleable.fullModalAttr_title) ?: ""
            subtitle = typedArray.getString(R.styleable.fullModalAttr_subtitle) ?: ""
            instruction = typedArray.getString(R.styleable.fullModalAttr_instruction) ?: ""
            primaryButtonText = typedArray.getString(R.styleable.fullModalAttr_buttonText) ?: ""
            secondaryButtonText = typedArray.getString(R.styleable.fullModalAttr_secondaryButtonText) ?: ""
            buttonTextTitle = typedArray.getString(R.styleable.fullModalAttr_buttonTextTitle) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(primaryButtonView, onPrimaryButtonPress)
        TouchFeedbackUtil.attach(secondaryButtonView, onSecondaryButtonPress)
        TouchFeedbackUtil.attach(textButtonView, onTextButtonPress)
    }

}