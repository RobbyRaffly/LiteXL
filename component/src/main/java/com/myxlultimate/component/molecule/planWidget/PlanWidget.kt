package com.myxlultimate.component.molecule.planWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaSummaryStatusType
import com.myxlultimate.component.template.cardWidget.CardMode
import com.myxlultimate.component.template.cardWidget.IconMode
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_plan_widget.view.*

class PlanWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var cardMode = CardMode.NORMAL
        set(value) {
            field = value
            refreshView()
        }
    var label = ""
        set(value) {
            field = value
            refreshView()
        }

    var buttonLabel = ""
        set(value) {
            field = value
            refreshView()
        }

    var isButtonDisable = false
        set(value) {
            field = value
            refreshView()
        }

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    var iconImage = ContextCompat.getDrawable(context, R.drawable.ic_home)
        set(value) {
            field = value
            refreshView()
        }

    var information = ""
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
            containerView.onActionButtonPress = value
        }

    var hasWarning = false
        set(value) {
            field = value
            refreshView()
        }


    var bottomLabel = ""
        set(value) {
            field = value
            refreshView()
        }

    var bottomInformation = ""
        set(value) {
            field = value
            refreshView()
        }

    var bottomInformationError = false
        set(value) {
            field = value
            refreshView()
        }

    var bottomTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    var isBottomTitleDisable = false
        set(value) {
            field = value
            refreshView()
        }

    var bottomCtaLabel = ""
        set(value) {
            field = value
            refreshView()
        }


    var onBottomCtaClick: (() -> Unit)? = null
        set(value) {
            field = value
            bottomCta.setOnClickListener {
                if (value != null) {
                    value()
                }
            }
        }

    val viewCardMain by lazy {
        containerView.viewCardMain
    }

    var iconMode: IconMode? = null
        set(value) {
            field = value
            value?.let {
                containerView.iconMode = it

                if (value == IconMode.NO_ICON){
                    iconView.visibility = View.GONE
                }
            }
        }

    var isShowIcon = true
        set(value) {
            field = value

            if (value){
                iconView.visibility = View.VISIBLE
            }else{
                iconView.visibility = View.GONE
            }
        }

    var buttonLabelCta = ""
        set(value) {
            field = value

            buttonCta.text = value
        }

    var isVisibleButtonCta = false
        set(value) {
            field = value

            buttonCta.visibility = if (value) View.VISIBLE else View.GONE
        }

    var onPressButtonCta: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(buttonCta, value)
        }

    var quotaSummaryStatusType = QuotaSummaryStatusType.NONE
        set(value) {
            field = value

            containerView.quotaSummaryStatusType = value
        }

    var flagIcon : String = resources.getString(R.string.xl_plane)
        set(value) {
            field = value

            containerView.flagIcon = value
        }

    var customFlagText = ""
        set(value) {
            field = value

            containerView.customFlagText = value
        }

    var customBackgroundFlag = R.color.mud_palette_basic_green
        set(value) {
            field = value

            containerView.customBackgroundFlag = value
        }

    var showExclamationMarkEnd = false
        set(value) {
            field = value

            containerView.showExclamationMarkEnd = value
        }

    var onExclamationMarkClickEnd : (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onExclamationMarkClickEnd = value
        }


    private fun refreshView() {
        informationView.apply {
            visibility = if (information.isNotEmpty()) View.VISIBLE else View.GONE
            text = information
        }
        titleView.apply {
            visibility = if (title.isNotEmpty()) View.VISIBLE else View.GONE
            text = title
        }
        bottomContainerView.visibility = if (bottomLabel.isNotEmpty()) View.VISIBLE else View.GONE
        bottomInformationWithWarning.apply {
            visibility = if (hasWarning) View.VISIBLE else View.GONE
            title = bottomInformation
            colorTextError = bottomInformationError

            imageWarning = ContextCompat.getDrawable(context,
                if (bottomInformationError){
                    R.drawable.ic_icon_system_warning_red
                }else{
                    R.drawable.ic_icon_system_warning
                }
            )
        }
        bottomInformationWithoutWarning.apply {
            visibility = if (hasWarning) View.GONE else View.VISIBLE
            text = bottomInformation
        }
        bottomCta.apply {
            visibility = if (bottomCtaLabel.isNotEmpty()) View.VISIBLE else View.GONE
            isEnabled = !isButtonDisable
            text = bottomCtaLabel
        }
        bottomTitleView.apply {
            setTextColor(
                if (isBottomTitleDisable) ContextCompat.getColor(
                    context,
                    R.color.mud_palette_basic_medium_grey
                ) else ContextCompat.getColor(context, R.color.mud_palette_basic_black)
            )
            text = bottomTitle
        }
        containerView.apply {
            label = this@PlanWidget.label
            actionButtonLabel = buttonLabel
        }
        iconView.imageSourceType = ImageSourceType.DRAWABLE
        iconView.imageSource = iconImage
        bottomLabelView.text = bottomLabel
        containerView.cardMode = cardMode

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_plan_widget, this, true)


        bottomCta.setOnClickListener {
            onBottomCtaClick
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlanWidgetAttr)
        typedArray.run {
            containerView.label = getString(R.styleable.PlanWidgetAttr_label) ?: ""
            iconImage = getDrawable(R.styleable.PlanWidgetAttr_icon)
            containerView.actionButtonLabel =
                getString(R.styleable.PlanWidgetAttr_actionButtonLabel) ?: ""
            bottomLabel = getString(R.styleable.PlanWidgetAttr_bottomLabel) ?: ""
            bottomTitle = getString(R.styleable.PlanWidgetAttr_bottomTitle) ?: ""
            bottomInformation = getString(R.styleable.PlanWidgetAttr_bottomInformation) ?: ""
            hasWarning = getBoolean(R.styleable.PlanWidgetAttr_hasWarning, false)
            isButtonDisable = getBoolean(R.styleable.PlanWidgetAttr_isButtonDisable, false)
            isBottomTitleDisable =
                getBoolean(R.styleable.PlanWidgetAttr_isBottomTitleDisable, false)
        }
        typedArray.recycle()
    }

}