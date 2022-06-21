package com.myxlultimate.component.molecule.balanceWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.InformationMode
import com.myxlultimate.component.template.cardWidget.CardMode
import com.myxlultimate.component.template.cardWidget.IconMode
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_balance_widget.view.*

class BalanceWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var label = ""
        set(value) {
            field = value
            containerView.label = value
            labelSmallCardView.text = value
        }

    var buttonLabel = ""
        set(value) {
            field = value
            containerView.actionButtonLabel = value
            buttonSmallCardView.text = value
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

    var value = ""
        set(value) {
            field = value

            remainingView.text = value
            balanceSmallCardView.text = value
        }

    var icon: Int = R.drawable.ic_add_home
        set(value) {
            field = value
            containerView.icon = ContextCompat.getDrawable(context, value)
        }

    var iconMode: IconMode? = null
        set(value) {
            field = value
            value?.let {
                containerView.iconMode = it
            }
        }

    var isHideActionButton = false
        set(value) {
            field = value
            containerView.cardMode = if (isHideActionButton) CardMode.SMALL else CardMode.NORMAL

        }
    var isActionButtonEnabled = true
        set(value) {
            field = value
            containerView.actionButtonEnabled = value

        }

    // ----------------------------------------------------------------------------------

    var informationMode = InformationMode.DEFAULT
        set(value) {
            field = value

            when (value) {
                InformationMode.NONE -> {
                    informationView.visibility = View.GONE
                    informationSmallView.visibility = View.GONE
                    informationFlaggedContainer.visibility = View.GONE
                    informationFlaggedSmallContainer.visibility = View.GONE
                }
                InformationMode.DEFAULT -> {
                    informationView.visibility = View.VISIBLE
                    informationSmallView.visibility = View.VISIBLE
                    informationFlaggedContainer.visibility = View.GONE
                    informationFlaggedSmallContainer.visibility = View.GONE
                }
                InformationMode.WARNING -> {
                    informationView.visibility = View.GONE
                    informationSmallView.visibility = View.GONE
                    informationFlaggedContainer.visibility = View.VISIBLE
                    informationFlaggedSmallContainer.visibility = View.VISIBLE
                    informationFlaggedTextView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_yellow_dark
                        )
                    )
                    informationSmallFlaggedTextView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_yellow_dark
                        )
                    )
                    exclamationMarkView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_white
                        )
                    )
                    exclamationSmallMarkView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_white
                        )
                    )
                    informationFlaggedIconContainerView.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.mud_palette_yellow_normal)
                    informationFlaggedIconSmallContainerView.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.mud_palette_yellow_normal)
                }
                InformationMode.DANGER -> {
                    informationView.visibility = View.GONE
                    informationSmallView.visibility = View.GONE
                    informationFlaggedContainer.visibility = View.VISIBLE
                    informationFlaggedSmallContainer.visibility = View.VISIBLE
                    informationFlaggedTextView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_red
                        )
                    )
                    informationSmallFlaggedTextView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_red
                        )
                    )
                    exclamationMarkView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_red
                        )
                    )
                    exclamationSmallMarkView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_red
                        )
                    )
                    informationFlaggedIconContainerView.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.mud_palette_red_soft)
                    informationFlaggedIconSmallContainerView.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.mud_palette_red_soft)
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value

            informationView.text = value
            informationSmallView.text = value
            informationFlaggedTextView.text = value
            informationSmallFlaggedTextView.text = value
            if (information.isEmpty()) {
                informationView.visibility = View.GONE
                informationSmallView.visibility = View.GONE
            } else {
                this.informationMode = informationMode
            }
        }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onActionButtonPress = value
            buttonSmallCardView.setOnClickListener {
                value?.let {
                    it()
                }
            }
        }

    // -----------------------------------
    var isSmallBalanceCard = false
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var showExclamationMark = false
        set(value) {
            field = value
            containerView.showExclamationMark = value
            IsEmptyUtil.setVisibility(value, smallCardExclamationMark)
        }

    // ----------------------------------------------------------------------------------

    var onExclamationMarkClick: (() -> Unit)? = null
        set(value) {
            field = value
            containerView.onExclamationMarkClick = value
            TouchFeedbackUtil.attach(smallCardExclamationMark, value)
        }

    var onPressButtonCta: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(buttonCta, value)
        }

    fun refreshView() {
        if (isShimmerOn && isSmallBalanceCard) {
            skeletonLayout.visibility = View.VISIBLE
            smallCardView.visibility = View.GONE
            containerView.visibility = View.GONE
            shimmerSmall.visibility = View.VISIBLE
            shimmerDefault.visibility = View.GONE
            shimmerLayout.startShimmer()
        } else if (isShimmerOn && !isSmallBalanceCard) {
            skeletonLayout.visibility = View.VISIBLE
            smallCardView.visibility = View.GONE
            containerView.visibility = View.GONE
            shimmerSmall.visibility = View.GONE
            shimmerDefault.visibility = View.VISIBLE
            shimmerLayout.startShimmer()
        } else if (!isShimmerOn && isSmallBalanceCard) {
            skeletonLayout.visibility = View.GONE
            smallCardView.visibility = View.VISIBLE
            containerView.visibility = View.GONE
            shimmerLayout.stopShimmer()
        } else {
            skeletonLayout.visibility = View.GONE
            smallCardView.visibility = View.GONE
            containerView.visibility = View.VISIBLE
            shimmerLayout.stopShimmer()

        }
    }

    val viewCardMain by lazy {
        containerView.viewCardMain
    }

    val smallCardViewChild by lazy {
        smallCardView
    }

    val viewShimmerMain by lazy {
        skeletonLayout
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_balance_widget, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BalanceWidgetAttr)
        typedArray.run {
            containerView.label = getString(R.styleable.BalanceWidgetAttr_label) ?: ""
            containerView.icon = getDrawable(R.styleable.BalanceWidgetAttr_icon)
            containerView.actionButtonLabel =
                getString(R.styleable.BalanceWidgetAttr_actionButtonLabel) ?: ""
            informationMode = InformationMode.values()[getInt(
                R.styleable.BalanceWidgetAttr_informationMode,
                informationMode.ordinal
            )]
            showExclamationMark =
                getBoolean(R.styleable.BalanceWidgetAttr_showExclamationMark, false)
        }
        typedArray.recycle()
    }

}