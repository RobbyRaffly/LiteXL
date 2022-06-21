package com.myxlultimate.component.organism.packageCard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.common.internal.ResourceUtils
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem
import com.myxlultimate.component.organism.packageCard.adapters.ChinListRecycleViewAdapter
import com.myxlultimate.component.organism.packageCard.enums.*
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.*
import kotlinx.android.synthetic.main.organism_bonus_card_product_item.view.*
import kotlinx.android.synthetic.main.organism_package_card_option.view.*
import kotlinx.android.synthetic.main.organism_package_card_option.view.containerView
import kotlinx.android.synthetic.main.organism_package_card_option.view.iconView
import kotlinx.android.synthetic.main.organism_package_card_option.view.informationView
import kotlinx.android.synthetic.main.organism_package_card_option.view.line
import kotlinx.android.synthetic.main.organism_package_card_option.view.nameView
import kotlinx.android.synthetic.main.organism_package_card_option.view.nextButtonView
import kotlinx.android.synthetic.main.organism_package_card_option.view.originalPriceView
import kotlinx.android.synthetic.main.organism_package_card_option.view.priceView
import kotlinx.android.synthetic.main.organism_package_card_option.view.radioButtonSetterView
import kotlinx.android.synthetic.main.organism_package_card_option.view.shimmerLayout
import kotlinx.android.synthetic.main.organism_package_card_option.view.validityView
import kotlinx.android.synthetic.main.template_card_detail.view.*

class OptionPackageCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultStartColor = "#c40d42"
    private val defaultEndColor = "#18448A"

    private val chinListAdapter by lazy { ChinListRecycleViewAdapter() }

    data class addOnShimer(
        val isShimmerAddOn: Boolean = false
    )

    data class Data(
        val name: String,
        val validity: String,
        val price: Long,
        val originalPrice: Long,
        val information: String,
        val hasNextButton: Boolean,
        val hasPromo: Boolean,
        val image: String,
        val availability: AvailabilityMode? = AvailabilityMode.NONE,
        val ribbonTitle: String = "",
        val isShimmerOn: Boolean = false,
        val isDisabled: Boolean = false,
        val isLock: Boolean = true,
        val bottomTitle: String = "",
        val optionTextSizeMode: OptionTextSizeMode = OptionTextSizeMode.HUGE,
        val bottomTitleColor: Int = R.color.mud_palette_primary_blue,
        val tierLogo: String = "",
        val transactionStatus: TransactionStatus? = TransactionStatus.NONE,
        val isForHistory: Boolean = false,
        val upperRightIcon: String = "",
        val isBackground: Boolean = false,
        val colorBackground: String = "#FFFFFF",
        val colorTextCard: String = "",
        val colorTextSubCard: String = "",
        val loyaltyRibbonTitle: String = "",
        val loyaltySmallIcon: String = "",
        val startLoyaltyRibbonHexColor: String = "#c40d42",
        val endLoyaltyRibbonHexColor: String = "#18448A",
        val useSmallTitle: Boolean = false,
        val hasChinView: Boolean = false,
        val chinListItems: MutableList<PackageBenefitItem.Data> = mutableListOf(),
        val backgroundUrl: String = "",
        val setRibbonColor: String = "",
        val cardBackgroundMode: String = "",
        val ribbonMode: String = "",
        val eventStatus: EventStatus = EventStatus.ACTIVE
    )

    var showZeroValue = true

    var eventStatus = EventStatus.ACTIVE
        set(value) {
            field = value

            isDisabled = when (value) {
                EventStatus.INACTIVE -> true
                EventStatus.ACTIVE -> false
            }
        }

    // ----------------------------------------------------------------------------------

    var optionMode = OptionMode.SINGLE
        set(value) {
            field = value
            if ((value == OptionMode.MULTIPLE || value == OptionMode.MULTIPLE_SMALL || optionMode == OptionMode.SELECT_SMALL) && information == "") {
                topTitleView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            borderContainerView.setBackgroundResource(
                if ((value == OptionMode.MULTIPLE || value == OptionMode.MULTIPLE_SMALL || optionMode == OptionMode.SELECT_SMALL) && isActive)
                    R.drawable.top_up_nominal_option_background
                else 0
            )

            val layoutParamsRatio = containerView.layoutParams
            if (value == OptionMode.MULTIPLE_SMALL || optionMode == OptionMode.SELECT_SMALL) {
                layoutParamsRatio.width =
                    resources.getDimension(R.dimen.packageCardSquareWidth).toInt()
                containerView.layoutParams = layoutParamsRatio
                layoutCardSmall.visibility = View.VISIBLE
                layoutCard.visibility = View.GONE
            } else {
                layoutParamsRatio.width = ViewGroup.LayoutParams.MATCH_PARENT
                containerView.layoutParams = layoutParamsRatio
                layoutCardSmall.visibility = View.GONE
                layoutCard.visibility = View.VISIBLE
            }
        }


    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            field = value
            borderContainerView.setBackgroundResource(
                if ((optionMode == OptionMode.MULTIPLE || optionMode == OptionMode.MULTIPLE_SMALL || optionMode == OptionMode.SELECT_SMALL) && isActive)
                    R.drawable.top_up_nominal_option_background
                else 0
            )
            checkedView.visibility =
                if (optionMode == OptionMode.MULTIPLE || optionMode == OptionMode.MULTIPLE_SMALL) View.VISIBLE else View.GONE
            checkedViewSmall.visibility =
                if (optionMode == OptionMode.MULTIPLE || optionMode == OptionMode.MULTIPLE_SMALL) View.VISIBLE else View.GONE

            radioButtonSmallView.visibility =
                if (optionMode == OptionMode.SELECT_SMALL) View.VISIBLE else View.GONE
            radioButtonSetterView.visibility = if (isActive) View.VISIBLE else View.GONE

            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.icCheckedSquare, typedValue, true)

            checkedView.setImageResource(
                if ((optionMode == OptionMode.MULTIPLE || optionMode == OptionMode.MULTIPLE_SMALL || optionMode == OptionMode.SELECT_SMALL) && isActive)
                    typedValue.resourceId
                else R.drawable.ic_on_square
            )
            onChange?.let { it(value) }

            checkedViewSmall.setImageResource(
                if ((optionMode == OptionMode.MULTIPLE || optionMode == OptionMode.MULTIPLE_SMALL || optionMode == OptionMode.SELECT_SMALL) && isActive)
                    R.drawable.ic_checked_square_prepaid // temporary use drawable, because if implement with theme, erro in main app
                else R.drawable.ic_on_square
            )



            onChange?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value

            nameView.text = value
            nameBenefitLoyaltyView.text = value
            nameViewSmall.text = value
        }

    // ----------------------------------------------------------------------------------

    var validity = ""
        set(value) {
            field = value

            validityView.text = value
            validityViewSmall.text = value
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value
            priceView.text =
                if (value < 1 && !showZeroValue) ""
                else context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )

            historyPrice.text =
                if (value < 1 && !showZeroValue) ""
                else context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )

            priceViewSmall.text =
                if (value < 1 && !showZeroValue) ""
                else context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
        }

    // ----------------------------------------------------------------------------------


    // ----------------------------------------------------------------------------------

    var originalPrice: Long = 0
        set(value) {
//            if (value > 0 && field > 0) price = field
            field = value

            if (originalPrice > 0 && price < 1) {
                priceView.text =
                    context.getString(
                        R.string.indonesian_rupiah_balance_remaining,
                        ConverterUtil.convertDelimitedNumber(value, true)
                    )

                priceViewSmall.text =
                    context.getString(
                        R.string.indonesian_rupiah_balance_remaining,
                        ConverterUtil.convertDelimitedNumber(value, true)
                    )
            }

            originalPriceView.apply {
                text =
                    if (value < 1 || price <= 0) ""
                    else context.getString(
                        R.string.indonesian_rupiah_balance_remaining,
                        ConverterUtil.convertDelimitedNumber(value, true)
                    )
                if (text.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                    } else {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            }
            originalPriceViewSmall.apply {
                text =
                    if (value < 1 || price <= 0) ""
                    else context.getString(
                        R.string.indonesian_rupiah_balance_remaining,
                        ConverterUtil.convertDelimitedNumber(value, true)
                    )
                if (text.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                    } else {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            informationView.apply {
                text = value
            }
            informationBenefitLoyaltyView.apply {
                text = value
            }
            setOptionTextSizeMode()
        }

    // ----------------------------------------------------------------------------------

    var subtitle = ""
        set(value) {
            field = value
            subtitleView.text = value
            IsEmptyUtil.setVisibility(value, subtitleView)
        }

    // ----------------------------------------------------------------------------------

    var hasNextButton = true
        set(value) {
            field = value
            nextButtonView.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var hasTextNextButton = true
        set(value) {
            field = value

            nextButtonText.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var image =
        "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"
        set(value) {
            field = value
            iconView.apply {
                imageSource = value
                IsEmptyUtil.setVisibility(value, this)
            }
            iconViewSmallCard.apply {
                imageSource = value
                IsEmptyUtil.setVisibility(value, this)
            }
        }

    // ----------------------------------------------------------------------------------

    var hasPromo = true
        set(value) {
            field = value

            layoutPromo.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var ribbonTitle = ""
        set(value) {
            field = value

            if (value.isNotEmpty()) {
                ribbonLabel.text = value
                layoutRibbon.visibility = View.VISIBLE
                viewGapRibbon.visibility = View.GONE
            } else {
                layoutRibbon.visibility = View.GONE
                viewGapRibbon.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    var loyaltyRibbonTitle = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, loyaltyLayoutRibbonView)
            loyaltyRibbonLabelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var loyaltyIcon = ""
        set(value) {
            field = value
            loyaltyIconView.imageSource = value
            IsEmptyUtil.setVisibility(value, iconWrapperView)
            if (value.isEmpty()) {
                loyaltyRibbonLabelView.updatePadding(left = (24 * resources.displayMetrics.density).toInt())
            } else loyaltyRibbonLabelView.updatePadding(left = (4 * resources.displayMetrics.density).toInt())

        }

    // ----------------------------------------------------------------------------------

    var onNextPress: (() -> Unit)? = null
        set(value) {
            field = value

            if (!isDisabled || !isLock) {
                TouchFeedbackUtil.attach(nextButtonView, value)
                TouchFeedbackUtil.attach(textDetail, value)
            } else {
                TouchFeedbackUtil.detach(nextButtonView)
                TouchFeedbackUtil.detach(textDetail)
            }
        }

    // ----------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value

            if (!isDisabled || !isLock) {
                TouchFeedbackUtil.attach(containerView, value)
            } else {
                TouchFeedbackUtil.detach(containerView)
            }
        }

    // ----------------------------------------------------------------------------------
    var availability = AvailabilityMode.NONE
        set(value) {
            field = value
            availabilityView.visibility =
                if (value == AvailabilityMode.NONE) View.GONE else View.VISIBLE

            when (value) {
                AvailabilityMode.AVAILABLE -> {
                    informationView.visibility = View.GONE
                    availabilityView.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.mud_palette_turquoise_soft)
                    availabilityLabelView.setTextColor(
                        getColor(
                            context,
                            R.color.mud_palette_turquoise_dark
                        )
                    )
                    availabilityLabelView.text =
                        resources.getString(R.string.organism_package_card_option_availability)
                }
                AvailabilityMode.RUNOUTSOON -> {
                    informationView.visibility = View.GONE
                    availabilityView.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.mud_palette_red_soft)
                    availabilityLabelView.setTextColor(
                        getColor(
                            context,
                            R.color.mud_palette_red_dark
                        )
                    )
                    availabilityLabelView.text =
                        resources.getString(R.string.organism_package_card_option_almost_run_out)
                }
                AvailabilityMode.RUNOUT -> {
                    informationView.visibility = View.GONE
                    availabilityView.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.mud_palette_red_soft)
                    availabilityLabelView.setTextColor(
                        getColor(
                            context,
                            R.color.mud_palette_red_dark
                        )
                    )
                    availabilityLabelView.text =
                        resources.getString(R.string.organism_package_card_option_run_out)
                }
                else -> {
                    availabilityView.visibility = View.GONE
                }
            }
        }
    // ----------------------------------------------------------------------------------

    var isShimmerOn: Boolean = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.startShimmer()
                borderContainerView.visibility = View.GONE
                shimmerLayout.visibility = View.VISIBLE
            } else {
                shimmerLayout.stopShimmer()
                borderContainerView.visibility = View.VISIBLE
                shimmerLayout.visibility = View.GONE
            }
        }

    var isShimmerAddOn: Boolean = false
        set(value) {
            field = value
            if (value) {
                shimmerAddOnLayout.startShimmer()
                borderContainerView.visibility = View.GONE
                shimmerLayout.visibility = View.GONE
                shimmerAddOnLayout.visibility = View.VISIBLE
            } else {
                shimmerAddOnLayout.stopShimmer()
                shimmerAddOnLayout.visibility = View.GONE
                if (isShimmerOn) {
                    shimmerLayout.visibility = View.VISIBLE
                    borderContainerView.visibility = View.GONE
                } else {
                    borderContainerView.visibility = View.VISIBLE
                    shimmerLayout.visibility = View.GONE
                }
            }
        }
    // ----------------------------------------------------------------------------------

    var isDisabled = false
        set(value) {
            field = value
            layoutRibbon.alpha = if (!value) 1F else .5F
            layoutCard.alpha = if (!value) 1F else .5F
            this.onCardPress = onCardPress
            this.onNextPress = onNextPress
        }

    // ----------------------------------------------------------------------------------

    var upperRightIcon = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, upperRightCornerIconView)
            upperRightCornerIconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var isBackground = false
        set(value) {
            field = value
            setBackground()
        }

    // ----------------------------------------------------------------------------------

    var colorBackground = ""
        set(value) {
            field = value
            setBackground()
        }

    // ----------------------------------------------------------------------------------

    var colorTextCard = ""
        set(value) {
            field = value
            setBackground()
        }


    // ----------------------------------------------------------------------------------

    var colorTextSubCard = ""
        set(value) {
            field = value
            setBackground()
        }

    // ----------------------------------------------------------------------------------

    var isLock = false
        set(value) {
            field = value
            this.onCardPress = onCardPress
            this.onNextPress = onNextPress
            hasNextButton = false
            hasTextNextButton = false
            lockIconView.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var optionTextSizeMode = OptionTextSizeMode.HUGE
        set(value) {
            field = value
            setOptionTextSizeMode()
        }

    // ----------------------------------------------------------------------------------

    var bottomTitle = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, bottomInformationView)
            priceView.visibility = if (bottomTitle.isNotEmpty()) View.GONE else View.VISIBLE
            bottomInformationView.text = value
            textDetail.text = value
        }

    // ----------------------------------------------------------------------------------

    var bottomTitleColor = R.color.mud_palette_basic_dark_grey
        set(value) {
            field = value
            bottomInformationView.setTextColor(getColor(context, value))
            textDetail.setTextColor(getColor(context, value))
        }

    // ----------------------------------------------------------------------------------

    var tierLogo = ""
        set(value) {
            field = value
            tierLogoView.apply {
                imageSource = value
            }
        }


    // ----------------------------------------------------------------------------------

    var transactionStatus = TransactionStatus.NONE
        set(value) {
            field = value
            transactionStatusContainer.visibility =
                if (value == TransactionStatus.NONE) View.GONE else View.VISIBLE

            when (value) {
                TransactionStatus.FAILED -> {
                    textTransactionStatus.text =
                        resources.getString(R.string.organism_package_card_option_transaction_status_failed)
                    transactionStatusIcon.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_add_home,
                            null
                        )
                    )
                }
                TransactionStatus.SUCCESS -> {
                    textTransactionStatus.text =
                        resources.getString(R.string.organism_package_card_option_transaction_status_success)
                    transactionStatusIcon.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_akun_axis_home_new,
                            null
                        )
                    )

                }
                else -> {
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var isForHistory = true
        set(value) {
            field = value

            setOptionTextSizeMode()
        }

    // ----------------------------------------------------------------------------------

    var startHexColor = defaultStartColor
        set(value) {
            field = value
            setUpColor()
        }


    var endHexColor = defaultEndColor
        set(value) {
            field = value
            setUpColor()
        }

//    ----------------------------------------------------------------------------------

    var useSmallTitle: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

//    ----------------------------------------------------------------------------------

    var hasChinView: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

//    ----------------------------------------------------------------------------------

    var chinListItem = mutableListOf<PackageBenefitItem.Data>()
        set(value) {
            field = value
            chinListAdapter.items = value
        }

//    ----------------------------------------------------------------------------------

    var backgroundUrl = ""
        set(value) {
            field = value
            refreshView()
        }

//    ----------------------------------------------------------------------------------

    var setRibbonColor = ""
        set(value) {
            field = value
            refreshView()
        }

    //    ----------------------------------------------------------------------------------
    var cardBackgorundMode = ""
        set(value) {
            field = value
            refreshView()
        }

//    ----------------------------------------------------------------------------------

    var ribbonMode = ""
        set(value) {
            field = value
            refreshView()
        }

    //    ----------------------------------------------------------------------------------
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_package_card_option, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PackageCardOptionAttr)
        typedArray.run {
            image = getString(R.styleable.PackageCardOptionAttr_iconImage) ?: ""
            name = getString(R.styleable.PackageCardOptionAttr_name) ?: ""
            information = getString(R.styleable.PackageCardOptionAttr_information) ?: ""
            validity = getString(R.styleable.PackageCardOptionAttr_validity) ?: ""
            showZeroValue = getBoolean(R.styleable.PackageCardOptionAttr_showZeroValue, true)
            price = getInt(R.styleable.PackageCardOptionAttr_price, 0).toLong()
            originalPrice = getInt(R.styleable.PackageCardOptionAttr_originalPrice, 0).toLong()
            hasNextButton = getBoolean(R.styleable.PackageCardOptionAttr_hasNextButton, true)
            ribbonTitle = getString(R.styleable.PackageCardOptionAttr_ribbonLabel) ?: ""
            hasTextNextButton =
                getBoolean(R.styleable.PackageCardOptionAttr_hasTextNextButton, true)
            hasPromo = getBoolean(R.styleable.PackageCardOptionAttr_hasPromo, false)
            optionMode =
                OptionMode.values()[getInt(R.styleable.PackageCardOptionAttr_optionMode, 0)]
            isActive = getBoolean(R.styleable.PackageCardOptionAttr_showZeroValue, false)
            availability =
                AvailabilityMode.values()[getInt(R.styleable.PackageCardOptionAttr_availability, 3)]
            isShimmerOn = getBoolean(R.styleable.PackageCardOptionAttr_isShimmerOn, false)
            subtitle = getString(R.styleable.PackageCardOptionAttr_subtitle) ?: ""
            ribbonTitle = getString(R.styleable.PackageCardOptionAttr_ribbonLabel) ?: ""
            upperRightIcon = getString(R.styleable.PackageCardOptionAttr_upperRightIcon) ?: ""
            isDisabled = getBoolean(R.styleable.PackageCardOptionAttr_isDisabled, false)
            isBackground = getBoolean(R.styleable.PackageCardOptionAttr_isBackground, false)
            colorBackground =
                getString(R.styleable.PackageCardOptionAttr_colorBackground) ?: ""
            colorTextCard =
                getString(R.styleable.PackageCardOptionAttr_colorTextCard) ?: ""
            colorTextSubCard =
                getString(R.styleable.PackageCardOptionAttr_colorTextSubCard) ?: ""
            isLock = getBoolean(R.styleable.PackageCardOptionAttr_isLock, false)
            optionTextSizeMode = OptionTextSizeMode.values()[getInt(
                R.styleable.PackageCardOptionAttr_optionTextSizeMode,
                0
            )]
            isForHistory = getBoolean(R.styleable.PackageCardOptionAttr_isForHistory, false)
            bottomTitle = getString(R.styleable.PackageCardOptionAttr_bottomTitle) ?: ""
            bottomTitleColor = getResourceId(
                R.styleable.PackageCardOptionAttr_bottomTitleColor,
                R.color.mud_palette_basic_dark_grey
            )
            tierLogo = getString(R.styleable.PackageCardOptionAttr_tierLogo) ?: ""
            transactionStatus = TransactionStatus.values()[getInt(
                R.styleable.PackageCardOptionAttr_transactionStatus,
                2
            )]
            loyaltyIcon = getString(R.styleable.PackageCardOptionAttr_loyaltySmallIcon) ?: ""
            loyaltyRibbonTitle =
                getString(R.styleable.PackageCardOptionAttr_loyaltyRibbonTitle) ?: ""
            startHexColor =
                getString(R.styleable.PackageCardOptionAttr_startColor) ?: defaultStartColor
            endHexColor = getString(R.styleable.PackageCardOptionAttr_endColor) ?: defaultEndColor
            useSmallTitle = getBoolean(R.styleable.PackageCardOptionAttr_useSmallTitle, false)
        }
        setBackground()

//        if (optionMode == OptionMode.SINGLE) {
//            TouchFeedbackUtil.attach(containerView, onCardPress)
//        }

        if (optionMode == OptionMode.MULTIPLE || optionMode == OptionMode.MULTIPLE_SMALL || optionMode == OptionMode.SELECT_SMALL) {
            TouchFeedbackUtil.attach(containerView) {
                isActive = !isActive
            }
        }
    }

    private fun setOptionTextSizeMode() {
        when (optionTextSizeMode) {
            OptionTextSizeMode.SMALL -> {
                nameView.visibility = View.GONE
                nameBenefitLoyaltyView.visibility = View.VISIBLE
                informationView.visibility = View.GONE
                informationBenefitLoyaltyView.visibility = View.VISIBLE
                IsEmptyUtil.setVisibility(information, informationBenefitLoyaltyView)
            }
            OptionTextSizeMode.HUGE -> {
                nameView.visibility = View.VISIBLE
                nameBenefitLoyaltyView.visibility = View.GONE
                informationView.visibility = View.GONE
                informationBenefitLoyaltyView.visibility = View.VISIBLE
                informationView.visibility = View.VISIBLE
                informationBenefitLoyaltyView.visibility = View.GONE
                historyPrice.visibility = View.GONE
                IsEmptyUtil.setVisibility(information, informationView)

                if (isForHistory) {
                    informationBenefitLoyaltyView.visibility = View.VISIBLE
                    informationView.visibility = View.GONE
                    priceView.visibility = View.GONE
                    historyPrice.visibility = View.VISIBLE
                    nameView.visibility = View.GONE
                    nameBenefitLoyaltyView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpColor() {
        DrawableUtil.CreateShapeGradientBackground(context, startHexColor, endHexColor, 16f)?.let {
            loyaltyLayoutRibbonView.background = it
        }
    }

    private fun refreshView() {
        if (useSmallTitle) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                nameView.setTextAppearance(context, R.style.TextAppearance_MudComponents_H6)
                informationView.setTextAppearance(context, R.style.TextAppearance_MudComponents_H4)
            } else {
                nameView.setTextAppearance(R.style.TextAppearance_MudComponents_H6)
                informationView.setTextAppearance(R.style.TextAppearance_MudComponents_H4)
            }
            nameView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
            validityView.visibility = View.GONE
            validityTopGravity.visibility = View.VISIBLE
            hasNextButton = true
        }

        if (hasChinView) {
            validityTopGravity.visibility = View.GONE
            chinViewContainer.visibility = View.VISIBLE
            chinListRV.layoutManager = LinearLayoutManager(context)
            chinListRV.adapter = chinListAdapter.also { it.items = chinListItem }
            hasNextButton = true
        }


        when (BackgroundColorMode(cardBackgorundMode)) {
            BackgroundColorMode.LIGHT -> {
                if (cardBackgorundMode == BackgroundColorMode.LIGHT.name) {
                    if (backgroundUrl.isNotEmpty()) Glide.with(context).load(backgroundUrl)
                        .centerCrop().into(darkModeBackground)
                    nameView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                    informationView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                    line.setBackgroundColor(getColor(context, R.color.mud_palette_basic_black))
                    originalPriceView.setTextColor(
                        getColor(
                            context,
                            R.color.mud_palette_basic_black
                        )
                    )

                    if (Build.VERSION.SDK_INT >= 23) {
                        originalPriceView.foreground = null
                    } else {
                        originalPriceView.paintFlags =
                            originalPriceView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }
                    val customText = HtmlCompat.fromHtml(
                        "<strike style='color:white'> <span style='color:white'>  ${originalPriceView.text}<span> </strike>",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    originalPriceView.text = customText
                }
            }
            BackgroundColorMode.DARK -> {
                if (backgroundUrl.isNotEmpty()) Glide.with(context).load(backgroundUrl).centerCrop()
                    .into(darkModeBackground)
                nameView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
                informationView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
                line.setBackgroundColor(getColor(context, R.color.mud_palette_basic_white))
                originalPriceView.setTextColor(getColor(context, R.color.mud_palette_basic_white))

                if (Build.VERSION.SDK_INT >= 23) {
                    originalPriceView.foreground = null
                } else {
                    originalPriceView.paintFlags =
                        originalPriceView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                val customText = HtmlCompat.fromHtml(
                    "<strike style='color:white'> <span style='color:white'>  ${originalPriceView.text}<span> </strike>",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                originalPriceView.text = customText

                val typedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(R.attr.darkModeTextColor, typedValue, true)
                priceView.setTextColor(getColor(context, typedValue.resourceId))
            }
            BackgroundColorMode.CUSTOM -> {
            }
        }

        if (setRibbonColor.isNotEmpty()) {
            ColorUtil.parseColor(setRibbonColor, { colorInt ->
                layoutRibbon.backgroundTintList = ColorStateList.valueOf(colorInt)
            })
        }

        when (BackgroundColorMode.invoke(ribbonMode)) {
            BackgroundColorMode.LIGHT -> {
                if (ribbonMode == BackgroundColorMode.LIGHT.name) {
                    ribbonLabel.setTextColor(getColor(context, R.color.mud_palette_basic_black))
                }
            }
            BackgroundColorMode.DARK -> {
                ribbonLabel.setTextColor(getColor(context, R.color.mud_palette_basic_white))
            }
            BackgroundColorMode.CUSTOM -> {
            }
        }

    }


    private fun setBackground() {
        if (isBackground && colorBackground != "") {
            ColorUtil.parseColor(colorBackground, {
                containerView.setCardBackgroundColor(it)
            })
            ribbonLabel.setTextColor(getColor(context, R.color.mud_palette_primary_blue))
            layoutRibbon.background = ContextCompat.getDrawable(
                context, R.drawable.rounded_ribbon_white
            )
            backgroundSwoosh.visibility = View.VISIBLE
            iconView.background = ContextCompat.getDrawable(
                context, R.drawable.circle_background
            )

            var colorText = getColor(context, R.color.basicWhite)
            ColorUtil.parseColor(colorTextCard, {
                colorText = it
            })

            nameViewSmall.setTextColor(colorText)
            textDetail.setTextColor(colorText)
            priceViewSmall.setTextColor(colorText)
            validityViewSmall.setTextColor(colorText)
            nextButtonText.setTextColor(colorText)
            textTransactionStatus.setTextColor(colorText)
            priceView.setTextColor(colorText)
            historyPrice.setTextColor(colorText)
            bottomInformationView.setTextColor(colorText)
            textPromo.setTextColor(colorText)
            lockIconView.setTextColor(colorText)
            validityView.setTextColor(colorText)
            availabilityLabelView.setTextColor(colorText)
            informationBenefitLoyaltyView.setTextColor(colorText)
            informationView.setTextColor(colorText)
            nameBenefitLoyaltyView.setTextColor(colorText)
            nameView.setTextColor(colorText)

            var colorTextSub = getColor(context, R.color.mud_palette_basic_light_grey)
            ColorUtil.parseColor(colorTextSubCard, {
                colorTextSub = it
            })
            subtitleView.setTextColor(colorTextSub)
            originalPriceView.setTextColor(colorTextSub)
            originalPriceViewSmall.setTextColor(colorTextSub)

            line.setBackgroundColor(colorText)
        }
    }

}
