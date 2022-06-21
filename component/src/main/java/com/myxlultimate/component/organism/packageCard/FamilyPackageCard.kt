package com.myxlultimate.component.organism.packageCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.enums.SizeMode
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_bonus_coupon_card.view.*
import kotlinx.android.synthetic.main.organism_package_card_family.view.*
import kotlinx.android.synthetic.main.organism_package_card_family.view.containerView
import kotlinx.android.synthetic.main.organism_package_card_family.view.informationView
import kotlinx.android.synthetic.main.organism_package_card_family.view.rightImageView

class FamilyPackageCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val backgroundImage: String,
        var sizeMode: SizeMode = SizeMode.FULL,
        val name: String,
        val description: String,
        val showLabelPromo: Boolean,
        val label: String,
        val backgroundImageCardUrl: String,
        val backgroundMode: BackgroundColorMode,
        val rightImage : String = "",
        val showTopLabel : Boolean = false,
        val rightBase64Icon : String = "",
        val topLabelEndColor: String? = null,
        val topLabelStartColor: String? = null
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var backgroundImage = ""
        set(value) {
            field = value

            backgroundView.imageSource = value
        }

    // ----------------------------------------------------------------------------------


    var rightImage = ""
        set(value) {
            field = value
            setUpRightImage()
        }

    // ----------------------------------------------------------------------------------

    var rightBase64Icon = ""
    set(value) {
        field = value
        setUpRightImage()
    }

    // ----------------------------------------------------------------------------------

    var sizeMode = SizeMode.FULL
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var backgroundMode = BackgroundColorMode.LIGHT
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var backgroundImageCardUrl = ""
        set(value) {
            field = value

            backgroundImageCard.apply {
                imageSource = value
                IsEmptyUtil.setVisibility(value,this)
            }
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    var showLabelPromo = false
        set(value) {
            field = value
            refreshLabel()
        }

    var label = "Promo"
        set(value) {
            field = value
            refreshLabel()
        }
    
    var topLabelStartColor: String? = null
        set(value) {
            field = value
            setUpBackgroundRibbon()
        }


    var topLabelEndColor: String? = null
        set(value) {
            field = value
            setUpBackgroundRibbon()
        }

    // ----------------------------------------------------------------------------------

    var showTopLabel = false
    set(value) {
        field = value
        refreshLabel()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshLabel() {
        promoLabelView.title = label
        ribbonLabel.text = label
        when {
            showLabelPromo -> {
                promoLabelView.visibility = View.VISIBLE
                layoutRibbon.visibility = View.GONE
            }
            showTopLabel -> {
                promoLabelView.visibility = View.GONE
                layoutRibbon.visibility = View.VISIBLE
                val topPadding = ConverterUtil.dpToPx(context, 32F)
                val startPadding = ConverterUtil.dpToPx(context, 24f)
                val bottomPadding = ConverterUtil.dpToPx(context, 16f)
                dynamicView.setPadding(
                    startPadding.toInt(),
                    topPadding.toInt(),
                    0,
                    bottomPadding.toInt()
                )
            }
            else -> {
                layoutRibbon.visibility = View.GONE
                promoLabelView.visibility = View.GONE
                val topPadding = ConverterUtil.dpToPx(context, 16f)
                val startPadding = ConverterUtil.dpToPx(context, 24f)
                val bottomPadding = ConverterUtil.dpToPx(context, 16f)
                dynamicView.setPadding(
                    startPadding.toInt(),
                    topPadding.toInt(),
                    0,
                    bottomPadding.toInt()
                )
            }
        }
    }

    private fun refreshView() {
        nameView.apply {
            text = name
            setTextColor(
                ContextCompat.getColor(
                    context,
                    backgroundMode.defaultTextColor
                )
            )
        }

        nameSmallView.apply {
            text = name
            setTextColor(
                ContextCompat.getColor(
                    context,
                    backgroundMode.defaultTextColor
                )
            )
        }
        informationView.apply {
            text = description
            setTextColor(
                ContextCompat.getColor(
                    context,
                    backgroundMode.defaultTextColor
                )
            )
        }

        setMode()
    }

    private fun setMode() {
        //            val height = CardSizeMode.values()[value.ordinal].height

        var layoutParams = containerView.layoutParams
//            layoutParams.height = height
//            if (value == SizeMode.COMPACT) layoutParams.width = (height * 2.75).toInt()
        containerView.layoutParams = layoutParams

//            layoutParams = rightGapView.layoutParams
//            layoutParams.width = CardSizeMode.values()[value.ordinal].rightGap
//            rightGapView.layoutParams = layoutParams

        layoutParams = backgroundView.layoutParams
//            layoutParams.height = height
//            layoutParams.width = height
        backgroundView.layoutParams = layoutParams

        nameView.visibility = if (sizeMode == SizeMode.FULL) View.VISIBLE else View.GONE
        informationView.visibility = if (sizeMode == SizeMode.FULL) View.VISIBLE else View.GONE
        nameSmallView.visibility = if (sizeMode == SizeMode.COMPACT) View.VISIBLE else View.GONE

        if(sizeMode == SizeMode.COMPACT){

            var layoutParamsRatio = containerView.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsRatio.dimensionRatio = "H,3:1"
            containerView.layoutParams = layoutParamsRatio
            layoutParamsRatio = rightGapView.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsRatio.dimensionRatio = "H,1:1"
            rightGapView.layoutParams = layoutParamsRatio
        }
        else {
            var layoutParamsRatio = containerView.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsRatio.dimensionRatio = "H,33:12"
            containerView.layoutParams = layoutParamsRatio
            layoutParamsRatio = rightGapView.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsRatio.dimensionRatio = "H,2:1.7"
            rightGapView.layoutParams = layoutParamsRatio
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_package_card_family, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PackageCardFamilyAttr)
        typedArray.run {
            backgroundImage = getString(R.styleable.PackageCardFamilyAttr_backgroundImage) ?: ""
            backgroundImageCardUrl = getString(R.styleable.PackageCardFamilyAttr_backgroundImageCardUrl) ?: ""
            name = getString(R.styleable.PackageCardFamilyAttr_name) ?: ""
            description = getString(R.styleable.PackageCardFamilyAttr_description) ?: ""
            sizeMode = SizeMode.values()[getInt(R.styleable.PackageCardFamilyAttr_sizeMode, 0)]
            showLabelPromo = getBoolean(R.styleable.PackageCardFamilyAttr_showLabelPromo, false)
            label = getString(R.styleable.PackageCardFamilyAttr_label) ?: ""
            backgroundMode = BackgroundColorMode.values()[getInt(R.styleable.PackageCardFamilyAttr_backgroundMode, 0)]
            rightImage = getString(R.styleable.PackageCardFamilyAttr_rightImage)?:""
            showTopLabel = getBoolean(R.styleable.PackageCardFamilyAttr_showTopLabel,false)
            rightBase64Icon = getString(R.styleable.PackageCardFamilyAttr_rightBase64Icon)?:""
        }

        setUpBackgroundRibbon()
        TouchFeedbackUtil.attach(containerView, onPress)
    }


    private fun setUpRightImage() {
        if(rightImage.isNotEmpty()) {
            rightImageView.imageSourceType = ImageSourceType.URL
            rightImageView.imageSource = rightImage
        } else if (rightBase64Icon.isNotEmpty()) {
            rightImageView.imageSourceType = ImageSourceType.BASE64
            rightImageView.imageSource = rightBase64Icon
        }
        rightImageView.visibility = if(rightImage.isNotEmpty() || rightBase64Icon.isNotEmpty()) View.VISIBLE else View.GONE
    }


    private fun setUpBackgroundRibbon() {
        // initial set default
        ribbonLabel.background = ContextCompat.getDrawable(context, R.drawable.rounded_ribbon)

        if (!topLabelEndColor.isNullOrEmpty() && !topLabelStartColor.isNullOrEmpty()) {
            DrawableUtil.CreateShapeGradientBackground(
                context,
                topLabelStartColor!!,
                topLabelEndColor!!,
                16f
            )?.let {
                ribbonLabel.background = it
            }
        }
    }
}