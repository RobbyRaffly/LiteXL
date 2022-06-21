package com.myxlultimate.component.template.cardWidget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaSummaryStatusType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.template_card_widget.view.*

class CardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var cardMode = CardMode.NORMAL
        set(value) {
            field = value
            bottomView.visibility = if (value == CardMode.SMALL) View.GONE else View.VISIBLE
        }

    // ----------------------------------------------------------------------------------

    var flagIcon : String = resources.getString(R.string.xl_plane)
    set(value) {
        field = value
        statusFlagIcon.text = value
    }
    // ----------------------------------------------------------------------------------

    var customFlagText : String = ""
    set(value) {
        field = value
        statusLabelView.text = value
    }

    // ----------------------------------------------------------------------------------

    var customBackgroundFlag = R.color.mud_palette_basic_green
    set(value) {
        field = value
        statusFlagView.backgroundTintList =
            ContextCompat.getColorStateList(context, value)
    }
    // ----------------------------------------------------------------------------------

    var icon: Drawable? = null
        set(value) {
            field = value

            iconViewSmall.imageSource = value
            iconViewLarge.imageSource = value
            iconViewFullRight.imageSource = value
            iconViewMedium.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var iconMode = IconMode.NO_ICON
        set(value) {
            field = value

            iconViewSmall.visibility = if (value == IconMode.SMALL) View.VISIBLE else View.GONE
            iconViewLarge.visibility = if (value == IconMode.LARGE) View.VISIBLE else View.GONE
            iconViewMedium.visibility = if (value == IconMode.MEDIUM) View.VISIBLE else View.GONE
            iconViewFullRight.visibility =
                if (value == IconMode.FULL_RIGHT) View.VISIBLE else View.GONE

            topContainerView.apply {
                val pRight = if (value == IconMode.NO_ICON) paddingLeft else 0
                setPadding(paddingLeft, paddingTop, pRight, paddingBottom)
            }
        }

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value

            labelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var labelLight = ""
        set(value) {
            field = value
            labelViewLight.text = value
            labelViewLight.visibility = if (value.isNotEmpty()) View.VISIBLE else View.GONE
            labelView.visibility = if (value.isNotEmpty()) View.GONE else View.VISIBLE
        }
    // ----------------------------------------------------------------------------------

    var quotaSummaryStatusType = QuotaSummaryStatusType.NONE
        set(value) {
            field = value

            when (value) {
                QuotaSummaryStatusType.NONE -> {
                    statusFlagView.visibility = View.GONE
                }
                QuotaSummaryStatusType.PASS -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                        ContextCompat.getColorStateList(context, value.color)
                    statusLabelView.text =
                        context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_prio_pass)
                    statusFlagIcon.visibility = View.VISIBLE
                }
                QuotaSummaryStatusType.XL_PASS -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                        ContextCompat.getColorStateList(context, value.color)
                    statusLabelView.text =
                        context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_xl_pass)
                    statusFlagIcon.visibility = View.VISIBLE
                }
                QuotaSummaryStatusType.HAJJ -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                        ContextCompat.getColorStateList(context, value.color)
                    statusLabelView.text =
                        context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_hajj)
                    statusFlagIcon.visibility = View.VISIBLE
                }
                QuotaSummaryStatusType.BASIC_PRICE -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                        ContextCompat.getColorStateList(context, value.color)
                    statusLabelView.text =
                        context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_prio_basic_price)
                    statusFlagIcon.visibility = View.VISIBLE
                }
                QuotaSummaryStatusType.ROAMING_OFF -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                        ContextCompat.getColorStateList(context, value.color)
                    statusLabelView.text =
                        context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_roaming_off)
                    statusFlagIcon.visibility = View.VISIBLE
                }
                QuotaSummaryStatusType.SUSPENDED -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                            ContextCompat.getColorStateList(context, value.color)
                    statusLabelView.text =
                            context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_suspended)
                    statusFlagIcon.visibility = View.GONE
                }
                QuotaSummaryStatusType.CUSTOM -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                        ContextCompat.getColorStateList(context, customBackgroundFlag)
                    statusFlagIcon.text = flagIcon
                    statusLabelView.text = customFlagText

                }
                QuotaSummaryStatusType.FUP_LOW -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                        ContextCompat.getColorStateList(context, value.color)
                    statusLabelView.text = resources.getString(R.string.fup_low)
                    statusFlagIcon.text = resources.getString(R.string.xl_internet)
                }
                QuotaSummaryStatusType.FUP_EMPTY -> {
                    statusFlagView.visibility = View.VISIBLE
                    statusFlagView.backgroundTintList =
                        ContextCompat.getColorStateList(context, value.color)
                    statusLabelView.text = resources.getString(R.string.fup_empty)
                    statusFlagIcon.text = resources.getString(R.string.xl_internet)
                }
            }
        }

    var statusLabel = ""
        set(value) {
            field = value

            if (value.isNotEmpty())
                statusLabelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var actionButtonLabel = ""
        set(value) {
            field = value

            actionButtonLabelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var actionButtonEnabled = true
        set(value) {
            field = value
            configureActionLabel()
        }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(actionButtonView, {
                if(actionButtonEnabled)
                    value?.invoke()
            })
        }

    var hasRightArrow = true
        set(value) {
            field = value
            if (value) {
                arrowRightView.visibility = View.VISIBLE
            } else {
                arrowRightView.visibility = View.INVISIBLE
            }

        }

    // ----------------------------------------------------------------------------------

    var actionButtonColor = getColor(context,R.color.colorPrimary)
        set(value) {
            field = value
            configureActionLabel()
        }
    // ----------------------------------------------------------------------------------

    var removeUpperPart = false
        set(value) {
            field = value
            if (removeUpperPart) {
                topContainerView.setPadding(paddingLeft, 0, paddingRight, paddingBottom)
            }
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
            cardMainView.visibility = if (value) View.GONE else View.VISIBLE
            parentSkeletonlayout.visibility = if (value) View.VISIBLE else View.GONE
        }
    // ----------------------------------------------------------------------------------

    var showExclamationMark = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, cardExclamationMark)
        }
    // ----------------------------------------------------------------------------------

    var onExclamationMarkClick : (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(cardExclamationMark, value)
        }

    // ----------------------------------------------------------------------------------


    val viewCardMain: CardView by lazy {
        cardMainView
    }

    // ----------------------------------------------------------------------------------

    var showExclamationMarkEnd = false
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, viewExclamationMargin)
            IsEmptyUtil.setVisibility(value, cardExclamationMarkEnd)
        }

    // ----------------------------------------------------------------------------------

    var onExclamationMarkClickEnd : (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(cardExclamationMarkEnd, value)
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_card_widget, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardWidgetAttr)
        typedArray.run {
            cardMode = CardMode.values()[getInt(R.styleable.CardWidgetAttr_cwaCardMode, 0)]
            iconMode = IconMode.values()[getInt(R.styleable.CardWidgetAttr_cwaIconMode, 0)]
            icon = getDrawable(R.styleable.CardWidgetAttr_icon)
            label = getString(R.styleable.CardWidgetAttr_label) ?: ""
            actionButtonLabel = getString(R.styleable.CardWidgetAttr_actionButtonLabel) ?: ""
            hasRightArrow = getBoolean(R.styleable.CardWidgetAttr_hasRightArrow, true)
            quotaSummaryStatusType = QuotaSummaryStatusType.values()[getInt(
                R.styleable.CardWidgetAttr_quotaSummaryStatusType,
                0
            )]
            isShimmerOn = getBoolean(R.styleable.CardWidgetAttr_isShimmerOn, false)
            showExclamationMark = getBoolean(R.styleable.customAttr_showExclamationMark, false)
        }
        typedArray.recycle()
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        actionButtonColor = typedValue.data


        TouchFeedbackUtil.attach(actionButtonView, onActionButtonPress)
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

    private fun configureActionLabel(){
        actionButtonLabelView.setTextColor(
            if(actionButtonEnabled)
                actionButtonColor
            else
                resources.getColor(R.color.basicDarkGrey)
        )
    }

}