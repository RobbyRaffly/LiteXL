package com.myxlultimate.component.organism.dompetCard

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.dompetCard.enums.WidgetStyle
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import com.myxlultimate.component.util.setTextViewAppearance
import kotlinx.android.synthetic.main.organism_dompet_account_setting_card.view.*
import kotlinx.android.synthetic.main.organism_dompet_card_payment_widget.view.*
import kotlinx.android.synthetic.main.organism_dompet_card_payment_widget.view.containerButtonView
import kotlinx.android.synthetic.main.organism_dompet_card_payment_widget.view.iconView
import kotlinx.android.synthetic.main.organism_dompet_card_payment_widget.view.informationContainerView
import kotlinx.android.synthetic.main.organism_dompet_card_payment_widget.view.primaryButtonFullView
import kotlinx.android.synthetic.main.organism_dompet_card_payment_widget.view.primaryButtonView
import kotlinx.android.synthetic.main.organism_dompet_card_payment_widget.view.secondaryButtonView
import kotlinx.android.synthetic.main.organism_dompet_card_payment_widget.view.titleView

class DompetPaymentWidget @JvmOverloads constructor(
    context :Context,
    attrs:AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultIcon = R.drawable.ic_gopay

    // ----------------------------------------------------------------------------------

    data class Data(
        val widgetStyle: WidgetStyle? =  WidgetStyle.FULL,
        val title: String? = "",
        val subTitle: String? = "",
        val imageSourceType: ImageSourceType? = ImageSourceType.DRAWABLE,
        val imageSource: Any? = "",
        val imageSourceTypeIcon: ImageSourceType? = ImageSourceType.DRAWABLE,
        val imageSourceIcon: Any? = "",
        val ribbonLabel: String? = "",
        val ribbonLabelNew: String? = "",
        val isErrorSubTitle: Boolean? = false,
        val warningTitle: String?="",
        val buttonLabelPrimary: String?="",
        val buttonLabelSecondary: String?=""
    )


    var onPrimaryButtonClick : (() -> Unit)? = null

    // ------------------------------------------------------------------------------------

    var onSecondaryButtonClick : (() -> Unit)? = null

    // ------------------------------------------------------------------------------------

    var onPrimaryButtonFullClick : (() -> Unit)? = null

    // ------------------------------------------------------------------------------------

    var widgetStyle : WidgetStyle = WidgetStyle.FULL
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

    var buttonLabelPrimary = ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var buttonLabelSecondary = ""
        set(value) {
            field = value
            refreshView()
        }

    var ribbonLabel = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                ribbonLabelView.text = value
                layoutRibbon.visibility = View.VISIBLE
                viewGapRibbon.visibility = View.GONE
            } else {
                layoutRibbon.visibility = View.GONE
                viewGapRibbon.visibility = View.VISIBLE
            }

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var ribbonLabelNew = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {

                ribbonLabelViewNew.text = value
                layoutRibbonNew.visibility = View.VISIBLE
                viewGapRibbon.visibility = View.GONE
            } else {
                layoutRibbonNew.visibility = View.GONE
                viewGapRibbon.visibility = View.VISIBLE
            }
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    var subTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isErrorSubTitle = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            iconView.imageSourceType = imageSourceType
        }

    // ----------------------------------------------------------------------------------

    var imageSource : Any? = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSourceTypeIcon = ImageSourceType.BASE64
        set(value) {
            field = value
            rightIconView.imageSourceType = imageSourceTypeIcon
        }

    // ----------------------------------------------------------------------------------

    var imageSourceIcon : Any? = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var warningTitle = ""
        set(value) {
            field = value
            informationTitleView.text = warningTitle
            refreshView()
            if (value.isNullOrEmpty())
                informationContainerView.visibility = View.GONE
            else
                informationContainerView.visibility = View.VISIBLE
        }
    // ----------------------------------------------------------------------------------
    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(containerCardDompet, value)
        }
    // ----------------------------------------------------------------------------------


    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_dompet_card_payment_widget, this,true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DompetPaymentWidgetAttr)
        typedArray.run {
            imageSourceType = ImageSourceType.values()[typedArray.getInt(R.styleable.DompetPaymentWidgetAttr_imageSourceType, 2)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.DompetPaymentWidgetAttr_imageSource)
            } else {
                typedArray.getString(R.styleable.DompetPaymentWidgetAttr_imageSource)
            }
            imageSourceTypeIcon = ImageSourceType.values()[typedArray.getInt(R.styleable.DompetPaymentWidgetAttr_imageSourceTypeIcon, 2)]
            imageSourceIcon = if (imageSourceTypeIcon == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.DompetPaymentWidgetAttr_imageSourceIcon)
            } else {
                typedArray.getString(R.styleable.DompetPaymentWidgetAttr_imageSourceIcon)
            }
            ribbonLabel = getString(R.styleable.DompetPaymentWidgetAttr_ribbonLabel)?: ""
            buttonLabelPrimary = getString(R.styleable.DompetPaymentWidgetAttr_buttonLabelPrimary)?:""
            buttonLabelSecondary = getString(R.styleable.DompetPaymentWidgetAttr_buttonLabelSecondary)?:""
            ribbonLabelNew = getString(R.styleable.DompetPaymentWidgetAttr_ribbonLabelNew)?: ""
            widgetStyle = WidgetStyle.values()[typedArray.getInt(R.styleable.DompetPaymentWidgetAttr_widgetStyle, 0)]
            title = getString(R.styleable.DompetPaymentWidgetAttr_title) ?: ""
            subTitle = getString(R.styleable.DompetPaymentWidgetAttr_subtitle) ?: ""
            isErrorSubTitle = getBoolean(R.styleable.DompetPaymentWidgetAttr_isErrorSubTitle, false)
            warningTitle = getString(R.styleable.DompetPaymentWidgetAttr_warningTitle)?:""
        }
        typedArray.recycle()

        secondaryButtonView.setOnClickListener {
            onSecondaryButtonClick?.invoke()
        }

        primaryButtonView.setOnClickListener {
            onPrimaryButtonClick?.invoke()
        }

        primaryButtonFullView.setOnClickListener {
            onPrimaryButtonFullClick?.invoke()
        }

        TouchFeedbackUtil.attach(containerCardDompet, onPress)
    }

    // ----------------------------------------------------------------------------------

    fun refreshView(){
        when(widgetStyle){
            WidgetStyle.COMPACT -> {
                iconView.visibility = View.GONE
                rightIconView.visibility = View.GONE

                buttonTextView.visibility = View.VISIBLE
                smallTitleView()
            }

            WidgetStyle.MINIMALIS -> {
                iconView.visibility = View.GONE
                rightIconView.visibility = View.GONE
                smallTitleView()
            }

            else -> {}
        }

        titleView.text = title
        subTitleView.text = subTitle
        primaryButtonView.text = buttonLabelPrimary
        secondaryButtonView.text = buttonLabelSecondary
        primaryButtonFullView.text = buttonLabelPrimary
        subTitleView.setTextColor(
            if (isErrorSubTitle) ContextCompat.getColor(context, R.color.mud_palette_basic_red)
            else ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey)
        )
        IsEmptyUtil.setVisibility(subTitle, subTitleView)
        if (imageSource == "") {
            val typedValue = TypedValue()
            context.theme?.resolveAttribute(R.attr.emptyPackagePngIcon, typedValue, true)
            iconView.imageSource = typedValue.resourceId
        } else {
            iconView.imageSource = imageSource
        }

        if (imageSourceIcon == "") {
            val typedValue = TypedValue()
            context.theme?.resolveAttribute(R.attr.emptyPackagePngIcon, typedValue, true)
            rightIconView.imageSource = typedValue.resourceId
        } else {
            rightIconView.imageSource = imageSourceIcon
        }

        if (buttonLabelPrimary.isNotEmpty() && buttonLabelSecondary.isNullOrEmpty()) {
            primaryButtonFullView.visibility = View.VISIBLE
            containerButtonView.visibility = View.GONE
        } else if (buttonLabelPrimary.isNotEmpty() && buttonLabelSecondary.isNotEmpty()) {
            primaryButtonFullView.visibility = View.GONE
            containerButtonView.visibility = View.VISIBLE
        } else {
            primaryButtonFullView.visibility = View.GONE
            containerButtonView.visibility = View.GONE
        }
    }

    // ----------------------------------------------------------------------------------


    fun smallTitleView(){
        titleView.setTextViewAppearance(R.style.TextAppearance_MudComponents_Body3)
        subTitleView.setTextViewAppearance(R.style.TextAppearance_MudComponents_H5)
    }

}