package com.myxlultimate.component.organism.dompetCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismDompetAccountSettingCardBinding
import com.myxlultimate.component.token.imageView.ImageSourceType

class DompetAccountSettingCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding = OrganismDompetAccountSettingCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var onPrimaryButtonClick: (() -> Unit)? = null

    // ------------------------------------------------------------------------------------

    var onSecondaryButtonClick: (() -> Unit)? = null

    // ------------------------------------------------------------------------------------

    var onPrimaryButtonFullClick: (() -> Unit)? = null

    // ------------------------------------------------------------------------------------

    var onSecondaryButtonFullClick: (() -> Unit)? = null

    // ------------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var topSubTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var bottomSubtitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            binding.iconView.imageSourceType = imageSourceType
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var imageSource: Any? = ""
        set(value) {
            field = value
            binding.iconView.imageSource = imageSource
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var setPrimaryButtonText = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var setSecondaryButtonText = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var setSecondaryButtonFullText = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var warningTitle = ""
        set(value) {
            field = value
            refreshView()

            if (value.isNullOrEmpty())
                binding.informationContainerView.visibility = View.GONE
            else
                binding.informationContainerView.visibility = View.VISIBLE
        }

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DompetAccountSettingCardAttr)

        typedArray.run {
            title = getString(R.styleable.DompetAccountSettingCardAttr_title) ?: ""
            topSubTitle = getString(R.styleable.DompetAccountSettingCardAttr_topSubtitle) ?: ""
            bottomSubtitle =
                getString(R.styleable.DompetAccountSettingCardAttr_bottomSubtitle) ?: ""
            imageSourceType = ImageSourceType.values()[typedArray.getInt(
                R.styleable.DompetAccountSettingCardAttr_imageSourceType,
                2
            )]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.DompetAccountSettingCardAttr_imageSource)
            } else {
                typedArray.getString(R.styleable.DompetAccountSettingCardAttr_imageSource)
            }
            setPrimaryButtonText =
                getString(R.styleable.DompetAccountSettingCardAttr_setPrimaryButtonText) ?: ""
            setSecondaryButtonText =
                getString(R.styleable.DompetAccountSettingCardAttr_setSecondaryButtonText) ?: ""
            setSecondaryButtonFullText =
                getString(R.styleable.DompetAccountSettingCardAttr_setSecondaryButtonFullText) ?: ""
            warningTitle = getString(R.styleable.DompetAccountSettingCardAttr_warningTitle) ?: ""
        }
        typedArray.recycle()

        binding.apply {
            secondaryButtonView.setOnClickListener {
                onSecondaryButtonClick?.invoke()
            }

            primaryButtonView.setOnClickListener {
                onPrimaryButtonClick?.invoke()
            }

            primaryButtonFullView.setOnClickListener {
                onPrimaryButtonFullClick?.invoke()
            }

            secondaryButtonFullView.setOnClickListener {
                onSecondaryButtonFullClick?.invoke()
            }
        }
    }

    fun refreshView() {
        binding.apply {
            titleView.text = title
            subtitleThinView.text = topSubTitle
            subTitleBoldView.text = bottomSubtitle
            InformationTitleView.text = warningTitle
            primaryButtonView.text = setPrimaryButtonText
            secondaryButtonView.text = setSecondaryButtonText
            primaryButtonFullView.text = setPrimaryButtonText
            secondaryButtonFullView.text = setSecondaryButtonFullText

            if (setPrimaryButtonText.isNotEmpty() && setSecondaryButtonText.isNullOrEmpty()) {
                primaryButtonFullView.visibility = View.VISIBLE
                containerButtonView.visibility = View.GONE
            } else if (setPrimaryButtonText.isNotEmpty() && setSecondaryButtonText.isNotEmpty()) {
                primaryButtonFullView.visibility = View.GONE
                containerButtonView.visibility = View.VISIBLE
            } else {
                primaryButtonFullView.visibility = View.GONE
                containerButtonView.visibility = View.GONE
            }

            secondaryButtonFullView.visibility =
                if (setSecondaryButtonFullText.isNotEmpty()) View.VISIBLE else View.GONE

        }
    }


}