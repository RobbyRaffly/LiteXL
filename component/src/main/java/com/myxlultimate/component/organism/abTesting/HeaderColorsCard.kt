package com.myxlultimate.component.organism.abTesting

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.abTesting.enums.BackgroundHeaderColorCardMode
import com.myxlultimate.component.organism.abTesting.enums.HeaderColorCardMode
import com.myxlultimate.component.organism.dashboardWidget.*
import com.myxlultimate.component.template.cardWidget.CardMode
import com.myxlultimate.component.util.ColorUtil
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_colors_header.view.*

class HeaderColorsCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val loyaltyIcon: String,
        val loyaltyLevelTitle: String,
        val startColor: String,
        val endColor: String,
        val information: String,
        val minSpending: Int,
        val maxSpending: Int,
        val headerColorCardMode: HeaderColorCardMode = HeaderColorCardMode.DASHBOARD_MESSAGE
    )
    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * On Click Listener
     */
    var onPressHeader: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(headerColorView, value)
        }

    /**
     * Variable
     */
    var title: String = ""
        set(value) {
            field = value

            updateView()
        }

    var backgroundColorHeader: Int = R.color.prioGold
        set(value) {
            field = value

            setupBackgroundHeader()
        }

    var startBackgroundColorHeader: Int = Color.parseColor("#0e90d5")
        set(value) {
            field = value

            setupBackgroundHeader()
        }

    var endBackgroundColorHeader: Int = Color.parseColor("#275aab")
        set(value) {
            field = value

            setupBackgroundHeader()
        }

    var isHideHeader = false
        set(value) {
            field = value

            headerColorView.visibility = if (value) View.GONE else View.VISIBLE
            viewShowLineMargin.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var loyaltyIcon = resources.getString(R.string.xl_loyalty_blue_icon_base64)
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var loyaltyLevelTitle = resources.getString(R.string.xl_loyalty_tier_silver_title)
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var minSpending = 0

    // ----------------------------------------------------------------------------------

    var maxSpending = 1

    // ----------------------------------------------------------------------------------

    var currentSpending = 0
        set(value) {
            field = value
            setUpProgress()
        }

    // ----------------------------------------------------------------------------------

    var headerColorCardMode = HeaderColorCardMode.DASHBOARD_MESSAGE
        set(value) {
            field = value

            dashboardMessageView.visibility =
                if (value == HeaderColorCardMode.DASHBOARD_MESSAGE) View.VISIBLE else View.GONE
            loyaltyTierView.visibility =
                if (value == HeaderColorCardMode.LOYALTI_TIERING) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var backgroundHeaderColorCardMode = BackgroundHeaderColorCardMode.SOLID
        set(value) {
            field = value

            setupBackgroundHeader()
        }


    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_colors_header, this, true)


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderColorsCard)
        typedArray.run {
            title = getString(R.styleable.HeaderColorsCard_title) ?: ""

            loyaltyIcon = getString(R.styleable.HeaderColorsCard_loyaltyIcon) ?: ""
            loyaltyLevelTitle = getString(R.styleable.HeaderColorsCard_loyaltyLevelTitle) ?: ""
            information = getString(R.styleable.HeaderColorsCard_information) ?: ""
            ColorUtil.parseColor(getString(R.styleable.HeaderColorsCard_startBackgroundColorHeader) ?: "#FFFFFF", {
                startBackgroundColorHeader = it
            })
            ColorUtil.parseColor(getString(R.styleable.HeaderColorsCard_endBackgroundColorHeader) ?: "#FFFFFF", {
                endBackgroundColorHeader = it
            })
            information = getString(R.styleable.HeaderColorsCard_information) ?: ""
            price = getInt(R.styleable.HeaderColorsCard_price, 0).toLong()
            minSpending = getInt(R.styleable.HeaderColorsCard_minSpending, 0)
            maxSpending = getInt(R.styleable.HeaderColorsCard_maxSpending, 1)
            currentSpending = getInt(R.styleable.HeaderColorsCard_currentSpending, 0)

            headerColorCardMode = HeaderColorCardMode.values()[getInt(
                R.styleable.HeaderColorsCard_headerColorCardMode,
                0 // DASHBOARD_MESSAGE
            )]

            backgroundHeaderColorCardMode = BackgroundHeaderColorCardMode.values()[getInt(
                R.styleable.HeaderColorsCard_backgroundHeaderColorCardMode,
                0 // SOLID
            )]
        }
        typedArray.recycle()

        updateView()
        TouchFeedbackUtil.attach(headerColorView, onPressHeader)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (itemContainerView == null) {
            super.addView(child, index, params)
        } else {
            var cardView: CardView? = null
            var shimmerView: CardView? = null
            when (child) {
                is BalanceDashboardWidget -> {
                    cardView = child.viewCardMain
                    shimmerView = child.viewShimmerMain

                    val cardViewOther = child.smallCardViewChild
                    cardViewOther.cardElevation = 0F
                    val layoutParams = cardViewOther.layoutParams as MarginLayoutParams
                    layoutParams.setMargins(0)
                    cardViewOther.requestLayout()
                }
                is QuotaSummaryDashboardWidget -> {
                    cardView = child.viewCardMain
                    shimmerView = child.viewShimmerMain
                }
                is NoQuotaDashboardWidget -> {
                    cardView = child.viewCardMain
                }
                is PlanDashboardWidget -> {
                    cardView = child.viewCardMain
                    shimmerView = child.viewCardShimmer
                    child.cardMode = CardMode.SMALL
                }
                is ContractDashboardWidget -> {
                    cardView = child.viewCardMain
                    shimmerView = child.viewCardShimmer
                }
                is NoBalanceDashboardWidget -> {
                    cardView = child.viewCardMain
                }
            }

            if (cardView != null) {
                cardView.cardElevation = 0F
                val layoutParams = cardView.layoutParams as MarginLayoutParams
                layoutParams.setMargins(0)
                cardView.requestLayout()
            }
            if (shimmerView != null) {
                shimmerView.cardElevation = 0F
                val layoutParams = shimmerView.layoutParams as MarginLayoutParams
                layoutParams.setMargins(0)
                shimmerView.requestLayout()
            }

            itemContainerView.addView(child, index, params)
        }
    }

    fun updateView() {
        titleView.text = title
    }

    private fun setRefreshView() {
        loyaltyLevelIconView.imageSource = loyaltyIcon
        loyaltyLevelTitleView.text = loyaltyLevelTitle
        informationView.text = information

        loyaltySpendMaxView.text = context.getString(
            R.string.indonesian_rupiah_balance_remaining,
            ConverterUtil.convertToShortenedDelimitedNumber(price, true)
        )
    }

    private fun setUpProgress() {
        if (maxSpending < 1) {
            progressView.progress = 0
            return
        }
        val progress =
            ((currentSpending - minSpending).toFloat() / (maxSpending - minSpending).toFloat()) * 100 // to percentage
        progressView.progress = progress.toInt()
    }

    private fun setupBackgroundHeader() {
        if (backgroundHeaderColorCardMode == BackgroundHeaderColorCardMode.SOLID) {
            headerColorView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    backgroundColorHeader
                )
            )
        } else if (backgroundHeaderColorCardMode == BackgroundHeaderColorCardMode.GRADIENT) {
            val gd = GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT, intArrayOf(
                    startBackgroundColorHeader,
                    endBackgroundColorHeader
                )
            )
            gd.cornerRadius = 0f

            headerColorView.background = gd
        }
    }

}