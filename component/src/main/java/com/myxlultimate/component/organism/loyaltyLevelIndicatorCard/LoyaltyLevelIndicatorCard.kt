package com.myxlultimate.component.organism.loyaltyLevelIndicatorCard

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.loyaltyLevelIndicatorCard.enum.LoyaltyLevelInformationFlagStatus
import com.myxlultimate.component.organism.loyaltyLevelIndicatorCard.enum.LoyaltyLevelInformationViewStatus
import com.myxlultimate.component.util.ColorUtil
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_level_indicator_card.view.*


class LoyaltyLevelIndicatorCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val informationViewStatus: LoyaltyLevelInformationViewStatus,
        val loyaltyIcon: String,
        val loyaltyLevelTitle: String,
        val statusLabel: String,
        val flagStatus: LoyaltyLevelInformationFlagStatus,
        val startColor: String,
        val endColor: String,
        val information: String,
        val price: Long,
        val hasBottomView: Boolean,
        val actionButtonLabel: String,
        val warningTitle: String,
        val minSpending: Int,
        val maxSpending: Int,
        val isDashboard: Boolean = false,
        val loyaltyTitle: String = "",
        val startBold: Int = 0,
        val endBold: Int = 0
    )

    var informationViewStatus = LoyaltyLevelInformationViewStatus.NONE
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var loyaltyBigIcon = resources.getString(R.string.xl_loyalty_blue_icon_base64)
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var loyaltySmallIcon = resources.getString(R.string.xl_loyalty_silver_icon_base64)
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

    var statusLabel = ""
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var flagStatus = LoyaltyLevelInformationFlagStatus.NORMAL
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var startColor = "#FF000000"
        set(value) {
            field = value
            setRefreshView()
            setDrawableGradient()
        }

    // ----------------------------------------------------------------------------------

    var endColor = "#FFFFFF"
        set(value) {
            field = value
            setRefreshView()
            setDrawableGradient()
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            setRefreshView()
            setDrawableGradient()
        }

    var startBold = 0
        set(value) {
            field = value
            setRefreshView()
        }


    var endBold = 0
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

    var hasBottomView = false
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var actionButtonLabel = ""
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var warningTitle = ""
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

    var isDashboard = false
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var loyaltyTitle = ""
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var onBottomClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(bottomView, value)
        }

    
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var statusShowPrice: Boolean = false
        set(value) {
            field = value
            setRefreshView()
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_level_indicator_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.LoyaltyLevelIndicatorCard)
        typedArray.run {
            informationViewStatus = LoyaltyLevelInformationViewStatus.values()[getInt(
                R.styleable.LoyaltyLevelIndicatorCard_loyaltyLevelInformationViewStatus,
                2 // NONE
            )]
            loyaltyBigIcon = getString(R.styleable.LoyaltyLevelIndicatorCard_loyaltyBigIcon) ?: ""
            loyaltySmallIcon =
                getString(R.styleable.LoyaltyLevelIndicatorCard_loyaltySmallIcon) ?: ""
            loyaltyLevelTitle =
                getString(R.styleable.LoyaltyLevelIndicatorCard_loyaltyLevelTitle) ?: ""
            statusLabel = getString(R.styleable.LoyaltyLevelIndicatorCard_statusLabel) ?: ""
            flagStatus = LoyaltyLevelInformationFlagStatus.values()[getInt(
                R.styleable.LoyaltyLevelIndicatorCard_loyaltyLevelInformationViewStatus,
                2 // NONE
            )]
            information = getString(R.styleable.LoyaltyLevelIndicatorCard_information) ?: ""
            price = getInt(R.styleable.LoyaltyLevelIndicatorCard_price, 0).toLong()
            hasBottomView = getBoolean(R.styleable.LoyaltyLevelIndicatorCard_hasBottomView, false)
            actionButtonLabel =
                getString(R.styleable.LoyaltyLevelIndicatorCard_actionButtonLabel) ?: ""
            warningTitle = getString(R.styleable.LoyaltyLevelIndicatorCard_warningTitle) ?: ""
            startColor = getString(R.styleable.LoyaltyLevelIndicatorCard_startColor) ?: "#FF000000"
            endColor = getString(R.styleable.LoyaltyLevelIndicatorCard_endColor) ?: "#FFFFFF"
            minSpending = getInt(R.styleable.LoyaltyLevelIndicatorCard_minSpending, 0)
            maxSpending = getInt(R.styleable.LoyaltyLevelIndicatorCard_maxSpending, 1)
            currentSpending = getInt(R.styleable.LoyaltyLevelIndicatorCard_currentSpending, 0)
            isDashboard = getBoolean(R.styleable.LoyaltyLevelIndicatorCard_isDashboard, false)
            loyaltyTitle = getString(R.styleable.LoyaltyLevelIndicatorCard_loyaltyTitle) ?: ""
            statusShowPrice = getBoolean(R.styleable.LoyaltyLevelIndicatorCard_statusPrice, false)
        }
        typedArray.recycle()

    }

    private fun setUpLoyaltyLevelInformationViewStatus() {
        informationLayoutView.visibility = View.VISIBLE
        when (informationViewStatus) {
            LoyaltyLevelInformationViewStatus.NORMAL -> {
                ivWarning.imageSource = getDrawable(context, R.drawable.ic_icon_system_warning)
                tvWarning.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
            }
            LoyaltyLevelInformationViewStatus.WARNING -> {
                ivWarning.imageSource = getDrawable(context, R.drawable.ic_icon_system_warning_red)
                tvWarning.setTextColor(getColor(context, R.color.mud_palette_basic_red))
            }
            LoyaltyLevelInformationViewStatus.NONE -> {
                informationLayoutView.visibility = View.GONE
            }
        }
    }

    private fun setRefreshView() {
        loyaltyLevelIconView.imageSource = loyaltyBigIcon
        loyaltyLevelProgressIcon.imageSource = loyaltySmallIcon
        loyaltyLevelTitleView.text = loyaltyLevelTitle
        loyaltyLevelTitleViewDashboard.text = loyaltyLevelTitle
        statusLabelView.text = statusLabel
        informationView.text = information
        informationView.setBold(information, startBold, endBold)
        priceView.text = context.getString(
            R.string.indonesian_rupiah_balance_remaining,
            ConverterUtil.convertToShortenedDelimitedNumber(price, true)
        )
        priceView.visibility = if (price <= 0.toLong()) View.GONE else View.VISIBLE
        bottomView.visibility = if (hasBottomView) View.VISIBLE else View.GONE
        actionButtonLabelView.text = actionButtonLabel
        setUpLoyaltyLevelInformationViewStatus()
        setUpFlagStatus()
        tvWarning.text = warningTitle
        informationLayoutView.visibility =
            if (warningTitle.isNotEmpty()) View.VISIBLE else View.GONE

        if (!isDashboard) {
            statusFlagView.visibility = View.VISIBLE
            loyaltySpendMaxView.visibility = View.GONE
            loyaltyMyTrxView.visibility = View.GONE
            myRewardsLogo.visibility = View.GONE
            loyaltyLevelTitleViewDashboard.visibility = View.GONE
            loyaltyLevelTitleView.visibility = View.VISIBLE
        } else {
            priceView.visibility = View.GONE
            statusFlagView.visibility = View.GONE
            loyaltySpendMaxView.visibility = View.VISIBLE
            loyaltyMyTrxView.visibility = View.VISIBLE
            myRewardsLogo.visibility = View.VISIBLE
            loyaltyMyTrxView.text = loyaltyTitle
            loyaltyLevelTitleViewDashboard.visibility = View.VISIBLE
            loyaltyLevelTitleView.visibility = View.GONE
            loyaltySpendMaxView.text = context.getString(
                R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertToShortenedDelimitedNumber(price, true)
            )
        }

        if (statusShowPrice) {
            loyaltySpendMaxView.visibility = View.GONE
        } else {
            loyaltySpendMaxView.visibility = View.VISIBLE
        }
    }

    private fun setUpFlagStatus() {
        statusFlagView.visibility = View.VISIBLE
        lockIconView.visibility = View.GONE

        when (flagStatus) {
            LoyaltyLevelInformationFlagStatus.SUCCESS -> {
                statusFlagView.background =
                    ContextCompat.getDrawable(context, R.drawable.button_background_gradation_blue)
                statusFlagView.backgroundTintList = null
            }
            LoyaltyLevelInformationFlagStatus.NORMAL, LoyaltyLevelInformationFlagStatus.NOW -> {
                statusFlagView.backgroundTintList =
                    ColorStateList.valueOf(getColor(context, R.color.mud_palette_basic_green))
            }
            LoyaltyLevelInformationFlagStatus.LOCK -> {
                lockIconView.visibility = View.VISIBLE
                statusFlagView.backgroundTintList =
                    ColorStateList.valueOf(getColor(context, R.color.mud_palette_basic_medium_grey))
                //Color Old
                // R.color.mud_palette_basic_dark_grey))
            }
            LoyaltyLevelInformationFlagStatus.NONE -> {
                statusFlagView.visibility = View.GONE
            }
        }
    }

    private fun TextView.setBold(
        charSequence: CharSequence,
        start: Int,
        end: Int
    ) {
        if (charSequence.isNotEmpty() && charSequence.length > start && charSequence.length > end && end > start) {

            val ssBuilder = SpannableStringBuilder(charSequence)
            val boldSpan: ClickableSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = getColor(
                        context,
                        R.color.mud_palette_basic_black
                    )
                    ds.isUnderlineText = false
                    ds.typeface = Typeface.DEFAULT_BOLD
                }

                override fun onClick(widget: View) {
//                    TODO("Not yet implemented")
                }
            }

            charSequence.let {
                ssBuilder.setSpan(
                    boldSpan,
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            this.text = ssBuilder

            this.movementMethod = LinkMovementMethod.getInstance()
            this.highlightColor = Color.TRANSPARENT
        }
    }

    private fun setDrawableGradient() {
        val backgroundColors = IntArray(3)
        backgroundColors[0] = getColor(context, R.color.mud_palette_basic_light_grey)
        backgroundColors[1] = getColor(context, R.color.mud_palette_basic_light_grey)
        backgroundColors[2] = getColor(context, R.color.mud_palette_basic_light_grey)

        val listColor = IntArray(2)
        ColorUtil.parseColor(startColor, {
            listColor[0] = it
        })
        ColorUtil.parseColor(endColor, {
            listColor[1] = it
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val layout = progressView
            val layerDrawable = layout.progressDrawable as LayerDrawable
            val scaleDrawable = layerDrawable
                .findDrawableByLayerId(R.id.progress) as ScaleDrawable
            val gradientDrawable = scaleDrawable.drawable as GradientDrawable
            gradientDrawable.colors = listColor
        } else {
            progressView.apply {
                progressDrawable = LayerDrawable(
                    arrayOf(
                        ScaleDrawable(
                            GradientDrawable(
                                GradientDrawable.Orientation.LEFT_RIGHT,
                                backgroundColors
                            ).apply {
                                cornerRadius = ConverterUtil.dpToPx(context, 20f)
                                setGradientCenter(0f, .75f)
                            },
                            Gravity.START,
                            100f,
                            100f
                        ),
                        ScaleDrawable(
                            GradientDrawable(
                                GradientDrawable.Orientation.LEFT_RIGHT,
                                listColor
                            ).apply {
                                cornerRadius = ConverterUtil.dpToPx(context, 20f)
                                setGradientCenter(0f, .75f)
                            },
                            Gravity.END,
                            100f,
                            100f
                        )
                    )
                )
            }
        }
        progressView.progressDrawable.mutate()
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
}