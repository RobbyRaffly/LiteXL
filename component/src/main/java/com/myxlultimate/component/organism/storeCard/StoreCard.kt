package com.myxlultimate.component.organism.storeCard

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismStoreCardBinding
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.organism.storeCard.enums.SecondaryColorMode
import com.myxlultimate.component.organism.storeCard.enums.SizeMode
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ColorUtil
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil

class StoreCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val binding = OrganismStoreCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    data class Data(
        val backgroundImage: String?,
        val backgroundColorMode: BackgroundColorMode = BackgroundColorMode.LIGHT,
        val sizeMode: SizeMode = SizeMode.LARGE,
        val category: String,
        val categoryColor: String,
        val title: String,
        val price: Long = 0,
        val originalPrice: Long = 0,
        val backgroundImageBase64: String = "",
        val hasRedDot: Boolean = false,
        val iconImage: String = "",
        val subtitle: String? = "",
        val validity: String? = "",
        val ribbonTitle: String? = "",
        val isBackground: Boolean = false,
        val colorBackground: String = "#FFFFFF",
        val colorTextCard: String = "",
        val colorTextSubCard: String = "",
        val description: String = ""
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var backgroundImage: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.apply {
                    backgroundView.imageSourceType = ImageSourceType.URL
                    backgroundView.imageSource = it
                    squareThumbnailImageView.imageSource = it
                    squareImageView.imageSource = it
                    rectangleImageView.imageSource = it
                    circleImageView.imageSource = it
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var isFun = false

    // ----------------------------------------------------------------------------------

    var iconImage = ""
        set(value) {
            field = value
            value.let {
                binding.apply {
                    smallIconView.imageSource = it
                    chipSmallIconView.imageSource = it
                    optionPackageCard.image = it
                    squareThumbnailIconView.imageSource = it
                    rightIconView.imageSource = it
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var backgroundImageBase64 = ""
        set(value) {
            field = value
            val isBackgroundImageEmpty = backgroundImage?.isEmpty() ?: true
            if (value.isNotEmpty() && isBackgroundImageEmpty && binding.backgroundView.imageSource != value) {
                binding.apply {
                    backgroundView.imageSourceType = ImageSourceType.BASE64
                    backgroundView.imageSource = value
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var backgroundColorMode = BackgroundColorMode.LIGHT
        set(value) {
            field = value

            binding.apply {
                val textColor = ContextCompat.getColor(context, value.defaultTextColor)
                nameView.setTextColor(textColor)
                nameSmallView.setTextColor(textColor)
                priceView.setTextColor(textColor)
                priceContextual.setTextColor(textColor)

                if (categoryColor == "") {
                    categoryView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            SecondaryColorMode.values()[value.ordinal].defaultColor
                        )
                    )
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var ribbonTitle = ""
        set(value) {
            field = value
            binding.optionPackageCard.ribbonTitle = value
        }

    // ----------------------------------------------------------------------------------

    var isBackground = false
        set(value) {
            field = value
            binding.optionPackageCard.isBackground = value
        }

    // ----------------------------------------------------------------------------------

    var colorBackground = ""
        set(value) {
            field = value
            binding.optionPackageCard.colorBackground = value
        }

    // ----------------------------------------------------------------------------------

    var colorTextCard = ""
        set(value) {
            field = value
            binding.optionPackageCard.colorTextCard = value
        }

    // ----------------------------------------------------------------------------------

    var colorTextSubCard = ""
        set(value) {
            field = value
            binding.optionPackageCard.colorTextSubCard = value
        }

    // ----------------------------------------------------------------------------------

    var validity = ""
        set(value) {
            field = value
            binding.optionPackageCard.validity = value
        }

    // ----------------------------------------------------------------------------------

    var subtitle = ""
        set(value) {
            field = value
            binding.optionPackageCard.subtitle = value
            binding.squareThumbnailInformationView.text = value
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            binding.informationView.text = value

            IsEmptyUtil.setVisibility(value, binding.informationView)

        }

    // ----------------------------------------------------------------------------------

    var sizeMode = SizeMode.LARGE
        set(value) {
            field = value

            when (value) {
                SizeMode.SMALL -> {
                    price = 0
                    originalPrice = 0
                    hasPrice = false
                    // set ratio
                    binding.apply {
                        var layoutParamsRatio =
                            containerView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.dimensionRatio = "H,3:1"
                        containerView.layoutParams = layoutParamsRatio
                        layoutParamsRatio =
                            rightGapView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.dimensionRatio = "H,1:1"
                        rightGapView.layoutParams = layoutParamsRatio
                        iconImage = ""
                        optionPackageCard.visibility = View.GONE
                        cobaView.visibility = View.VISIBLE
                        smallVerticalView.visibility = View.GONE
                        funAlbumLayoutView.visibility = View.GONE
                    }
                }
                SizeMode.LARGE -> {
                    binding.apply {
                        // set ratio
                        var layoutParamsRatio =
                            containerView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.dimensionRatio = "H,8:5"
                        containerView.layoutParams = layoutParamsRatio
                        layoutParamsRatio =
                            rightGapView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.dimensionRatio = "H,2:1"
                        rightGapView.layoutParams = layoutParamsRatio
                        iconImage = ""
                        optionPackageCard.visibility = View.GONE
                        cobaView.visibility = View.VISIBLE
                        smallVerticalView.visibility = View.GONE
                        funAlbumLayoutView.visibility = View.GONE
                    }
                }
                SizeMode.LARGE_LONG -> {
                    binding.apply {
                        // set ratio
                        var layoutParamsRatio =
                            containerView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.dimensionRatio = "H,8:3.2"
                        containerView.layoutParams = layoutParamsRatio
                        layoutParamsRatio =
                            rightGapView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.dimensionRatio = "H,3:2"
                        rightGapView.layoutParams = layoutParamsRatio

                        val typedValue = TypedValue()
                        context.theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
                        val color = ContextCompat.getColor(context, typedValue.resourceId)
                        backgroundView.setBackgroundColor(color)

                        iconImage = ""
                        priceContextualView.visibility = View.VISIBLE
                        priceContainerView.visibility = View.GONE
                        optionPackageCard.visibility = View.GONE
                        cobaView.visibility = View.VISIBLE
                        smallVerticalView.visibility = View.GONE
                        funAlbumLayoutView.visibility = View.GONE
                    }
                }
                SizeMode.RECTANGLE -> {
                    binding.apply {
                        val width: Int = Resources.getSystem().displayMetrics.widthPixels
                        backgroundImage = ""
                        backgroundImageBase64 = ""
                        iconImage = iconImage
                        val layoutParamsRatio =
                            containerView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.width =
                            width / 2 - resources.getDimension(R.dimen.mud_dimens_24dp)
                                .toInt() - resources.getDimension(R.dimen.mud_dimens_6dp).toInt()
                        layoutParamsRatio.height =
                            resources.getDimension(R.dimen.storeCardRectHeight).toInt()
                        containerView.layoutParams = layoutParamsRatio
                        rightGapView.visibility = View.GONE
                        priceView.visibility = View.GONE
                        textWrapper.setPadding(
                            resources.getDimension(R.dimen.mud_dimens_16dp).toInt(),
                            resources.getDimension(R.dimen.mud_dimens_16dp).toInt(),
                            resources.getDimension(R.dimen.mud_dimens_16dp).toInt(),
                            resources.getDimension(R.dimen.mud_dimens_16dp).toInt()
                        )
                        nameSquareView.setPadding(
                            0,
                            resources.getDimension(R.dimen.mud_dimens_36dp).toInt(),
                            0,
                            0
                        )
                        redDotCountLayout.backgroundTintList =
                            ContextCompat.getColorStateList(context, R.color.mud_palette_basic_red)
                        optionPackageCard.visibility = View.GONE
                        cobaView.visibility = View.VISIBLE
                        smallVerticalView.visibility = View.GONE
                        funAlbumLayoutView.visibility = View.GONE
                    }
                }
                SizeMode.SQUARE -> {
                    binding.apply {
                        // set ratio
//                    backgroundImage = ""
                        iconImage = if (isFun) iconImage else ""
                        val layoutParamsRatio =
                            containerView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.width =
                            resources.getDimension(R.dimen.storeCardSquareWidth).toInt()
                        layoutParamsRatio.height =
                            resources.getDimension(R.dimen.storeCardSquareHeight).toInt()
                        containerView.layoutParams = layoutParamsRatio
                        rightGapView.visibility = View.GONE
                        priceView.visibility = View.GONE
                        setColorTextSquareMode()
                        optionPackageCard.visibility = View.GONE
                        cobaView.visibility = View.VISIBLE
                        smallVerticalView.visibility = View.GONE
                        funAlbumLayoutView.visibility = View.GONE
                    }
                }
                SizeMode.WITH_RIBBON -> {
                    binding.apply {
                        optionPackageCard.hasNextButton = true
                        optionPackageCard.visibility = View.VISIBLE
                        cobaView.visibility = View.GONE
                        optionPackageCard.hasTextNextButton = false
                        smallVerticalView.visibility = View.GONE
                        funAlbumLayoutView.visibility = View.GONE
                    }
                }
                SizeMode.SMALL_LONG -> {
                    binding.apply {
                        val width: Int = Resources.getSystem().displayMetrics.widthPixels
                        backgroundImage = ""
                        backgroundImageBase64 = ""
                        iconImage = iconImage
                        val layoutParamsRatio =
                            chipContainerView.layoutParams as ConstraintLayout.LayoutParams
                        layoutParamsRatio.width =
                            width / 2 - resources.getDimension(R.dimen.mud_dimens_24dp)
                                .toInt() - resources.getDimension(R.dimen.mud_dimens_6dp).toInt()

                        chipContainerView.layoutParams = layoutParamsRatio

                        redDotCountLayout.backgroundTintList =
                            ContextCompat.getColorStateList(context, R.color.mud_palette_basic_red)

                        optionPackageCard.visibility = View.GONE
                        cobaView.visibility = View.GONE
                        chipView.visibility = View.VISIBLE
                        smallVerticalView.visibility = View.GONE
                        funAlbumLayoutView.visibility = View.GONE
                    }
                }
                SizeMode.SQUARE_THUMBNAIL_1 -> {
                    setUpThumbnail(SizeMode.SQUARE_THUMBNAIL_1)
                }
                SizeMode.SQUARE_THUMBNAIL_2 -> {
                    setUpThumbnail(SizeMode.SQUARE_THUMBNAIL_2)
                }
                SizeMode.SQUARE_THUMBNAIL_3 -> {
                    setUpThumbnail(SizeMode.SQUARE_THUMBNAIL_3)
                }
                SizeMode.SQUARE_THUMBNAIL_4 -> {
                    setUpThumbnail(SizeMode.SQUARE_THUMBNAIL_4)
                }
                SizeMode.POSTER -> {
                    setUpAlbum(SizeMode.POSTER)
                }
                SizeMode.ALBUM -> {
                    setUpAlbum(SizeMode.ALBUM)
                }
                SizeMode.SMALL_VERTICAL -> {
                    setUpSmallVerticalCard(SizeMode.SMALL_VERTICAL)
                }
                SizeMode.INFO_CARD -> {
                    setupInfoCard()
                }
            }

            binding.apply {
                nameView.visibility = if (value == SizeMode.LARGE) View.VISIBLE else View.GONE
                nameSmallView.visibility =
                    if (value == SizeMode.SMALL || value == SizeMode.LARGE_LONG || value == SizeMode.INFO_CARD) View.VISIBLE else View.GONE
                categoryView.visibility =
                    if (value == SizeMode.SMALL || value == SizeMode.LARGE) View.VISIBLE else View.GONE
                nameSquareView.visibility =
                    if ((value == SizeMode.SQUARE && !isFun) || value == SizeMode.RECTANGLE) View.VISIBLE else View.GONE
                nameFunSquareView.visibility =
                    if (value == SizeMode.SQUARE && isFun) View.VISIBLE else View.GONE
                squarePriceView.visibility =
                    if (value == SizeMode.SQUARE) View.VISIBLE else View.GONE
                categoryNameView.visibility =
                    if (value == SizeMode.SQUARE) View.VISIBLE else View.GONE
                smallIconView.visibility =
                    if (value == SizeMode.RECTANGLE || (value == SizeMode.SQUARE && isFun)) View.VISIBLE else View.GONE
                rightIconView.visibility = if(value == SizeMode.INFO_CARD) View.VISIBLE else View.GONE
                chipView.visibility = if (value == SizeMode.SMALL_LONG) View.VISIBLE else View.GONE
            }
        }

    // ----------------------------------------------------------------------------------

    var category = ""
        set(value) {
            field = value

            binding.categoryView.text = value
            binding.categoryNameView.text = value
            if (sizeMode == SizeMode.SMALL || sizeMode == SizeMode.LARGE || sizeMode == SizeMode.INFO_CARD) {
                IsEmptyUtil.setVisibility(category, binding.categoryView)
            }
        }

    // ----------------------------------------------------------------------------------

    var categoryColor = ""
        set(value) {
            field = value

            ColorUtil.parseColor(
                value,
                onParsed = {
                    binding.categoryView.setTextColor(it)
                },
                onError = {
                    binding.categoryView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            SecondaryColorMode.values()[backgroundColorMode.ordinal].defaultColor
                        )
                    )
                }
            )
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value

            binding.apply {
                nameView.text = value
                nameFunSquareView.text = value
                nameSmallView.text = value
                nameSquareView.text = value
                optionPackageCard.name = value
                chipNameSmallView.text = value
                squareThumbnailTitleView.text = value
                albumTitleView.text = value
                albumTitleView.visibility = if (value.isNotEmpty()) View.VISIBLE else View.GONE
                smallVerticalTextView.text = value
            }
        }

    // ----------------------------------------------------------------------------------

    var price: Long = 0
        set(value) {
            field = value

            binding.apply {
                priceView.text = context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
                squarePriceView.text = context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
                priceContextual.text = context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
                optionPackageCard.price = value
            }

            hasPrice = price > 0
        }

    // ----------------------------------------------------------------------------------

    var originalPrice: Long = 0
        set(value) {
            if (value > 0 && price <= 0) {
                price = value
            }
            field = value

            binding.optionPackageCard.originalPrice = value

            binding.originalPriceView.apply {
                text = context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )

                visibility = if (value > 0 && price != value) View.VISIBLE else View.GONE
                if (text.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                    } else {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            }

            binding.originalPriceContextual.apply {
                text = context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value, true)
                )
                visibility = if (value > 0 && price != value) View.VISIBLE else View.GONE
            }

            if (sizeMode == SizeMode.SQUARE) {
                setColorTextSquareMode()
            } else {
                setColorOriginalPriceTextContextual()
            }
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(binding.containerView, value)
            binding.optionPackageCard.onCardPress = {
                value?.let { it() }
            }
            TouchFeedbackUtil.attach(binding.squareThumbnailCard, value)
        }

    // ----------------------------------------------------------------------------------

    private var hasPrice = true
        set(value) {
            field = value
            binding.apply {
                priceContainerView.visibility =
                    if (value && sizeMode != SizeMode.LARGE_LONG) View.VISIBLE else View.GONE
                priceContextual.visibility =
                    if (value && sizeMode == SizeMode.LARGE_LONG) View.VISIBLE else View.GONE
                nameView.maxLines = if (value) 2 else 3
                nameSquareView.maxLines = if (value) 2 else 3
                nameSmallView.maxLines = if (value) 2 else 3
            }
        }

    // ----------------------------------------------------------------------------------

    var hasRedDot = false
        set(value) {
            field = value
            if (sizeMode == SizeMode.SMALL_LONG) {
                binding.chipRedDotCountLayout.visibility = if (value) View.VISIBLE else View.GONE
            } else {
                binding.redDotCountLayout.visibility = if (value) View.VISIBLE else View.GONE
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
       val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StoreCardBannerAttr)
        typedArray.run {
            backgroundImage = getString(R.styleable.StoreCardBannerAttr_backgroundImage) ?: ""
            backgroundImageBase64 =
                getString(R.styleable.StoreCardBannerAttr_backgroundImageBase) ?: ""
            backgroundColorMode = BackgroundColorMode.values()[getInt(
                R.styleable.StoreCardBannerAttr_backgroundColorMode,
                0
            )]
            category = getString(R.styleable.StoreCardBannerAttr_category) ?: ""
            categoryColor = getString(R.styleable.StoreCardBannerAttr_categoryColor) ?: ""
            title = getString(R.styleable.StoreCardBannerAttr_title) ?: ""
            price = getInt(R.styleable.StoreCardBannerAttr_price, 0).toLong()
            originalPrice = getInt(R.styleable.StoreCardBannerAttr_originalPrice, 0).toLong()
            iconImage = getString(R.styleable.StoreCardBannerAttr_iconImage) ?: ""
            sizeMode = SizeMode.values()[getInt(R.styleable.StoreCardBannerAttr_sizeModeCard, 0)]
            hasRedDot = getBoolean(R.styleable.StoreCardBannerAttr_hasRedDot, false)
            subtitle = getString(R.styleable.StoreCardBannerAttr_subtitle) ?: ""
            validity = getString(R.styleable.StoreCardBannerAttr_validity) ?: ""
            ribbonTitle = getString(R.styleable.StoreCardBannerAttr_ribbonLabel) ?: ""
            isBackground = getBoolean(R.styleable.StoreCardBannerAttr_isBackground, false)
            colorBackground = getString(R.styleable.StoreCardBannerAttr_colorBackground) ?: ""
            colorTextCard = getString(R.styleable.StoreCardBannerAttr_colorTextCard) ?: ""
            colorTextSubCard = getString(R.styleable.StoreCardBannerAttr_colorTextSubCard) ?: ""
        }

        if (price == 0L && originalPrice == 0L && !hasPrice) {
            binding.nameView.maxLines = 3
            binding.nameSquareView.maxLines = 3
        } else {
            binding.nameView.maxLines = 2
            binding.nameSquareView.maxLines = 2
        }

        TouchFeedbackUtil.attach(binding.containerView, onPress)
        TouchFeedbackUtil.attach(binding.chipContainerView, onPress)
    }

    private fun setColorTextSquareMode() {
        if (backgroundImage.isNullOrEmpty()) {
            setColorOriginalPriceTextContextual()
            binding.originalPriceView.apply {
                setTextColor(getColor(context, R.color.mud_palette_blue_grey))
                if (Build.VERSION.SDK_INT >= 23) {
                    foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                } else {
                    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
        } else {
            binding.nameSquareView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
            binding.originalPriceView.apply {
                setTextColor(getColor(context, R.color.mud_palette_basic_white))
                if (Build.VERSION.SDK_INT >= 23) {
                    foreground = context.getDrawable(R.drawable.strikethrough_foreground_white)
                } else {
                    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
            binding.squarePriceView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
        }
    }

    private fun setColorOriginalPriceTextContextual() {
        binding.originalPriceContextual.apply {
            if (text.isNotEmpty()) {
                if (backgroundColorMode == BackgroundColorMode.LIGHT) {
                    val typedValue = TypedValue()
                    context.theme.resolveAttribute(R.attr.colorBackgroundPrimary, typedValue, true)
                    val color = ContextCompat.getColor(context, typedValue.resourceId)
                    setTextColor(color)
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                    } else {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                } else {
                    setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_white))
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground = context.getDrawable(R.drawable.strikethrough_foreground_white)
                    } else {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            }
        }
    }

    private fun setUpThumbnail(sizeMode: SizeMode) {
        binding.apply {
            optionPackageCard.visibility = View.GONE
            cobaView.visibility = View.GONE
            smallVerticalView.visibility = View.GONE
            funAlbumLayoutView.visibility = View.GONE
            squareThumbnailCard.visibility = View.VISIBLE
            squareThumbnailImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            when (sizeMode) {
                SizeMode.SQUARE_THUMBNAIL_1 -> {
                    squareThumbnailTitleView.visibility = View.VISIBLE
                    squareThumbnailInformationView.visibility = View.GONE
                    squareThumbnailArrowView.visibility = View.GONE
                    squareThumbnailPlayButtonImageView.visibility = View.GONE
                }
                SizeMode.SQUARE_THUMBNAIL_2 -> {
                    squareThumbnailTitleView.visibility = View.VISIBLE
                    squareThumbnailInformationView.visibility = View.GONE
                    squareThumbnailArrowView.visibility = View.GONE
                    squareThumbnailPlayButtonImageView.visibility = View.VISIBLE
                }
                SizeMode.SQUARE_THUMBNAIL_3 -> {
                    squareThumbnailTitleView.visibility = View.VISIBLE
                    squareThumbnailInformationView.visibility = View.GONE
                    squareThumbnailArrowView.visibility = View.VISIBLE
                    squareThumbnailPlayButtonImageView.visibility = View.VISIBLE
                    squareThumbnailPlayButtonImageView.apply {
                        maxHeight = ConverterUtil.pxToDp(context, 188f).toInt()
                    }
                }
                SizeMode.SQUARE_THUMBNAIL_4 -> {
                    squareThumbnailTitleView.visibility = View.VISIBLE
                    squareThumbnailInformationView.visibility = View.VISIBLE
                    squareThumbnailArrowView.visibility = View.GONE
                    squareThumbnailPlayButtonImageView.visibility = View.GONE

                    squareThumbnailPlayButtonImageView.apply {
                        maxHeight = ConverterUtil.pxToDp(context, 188f).toInt()
                    }
                }
                else -> {
                }
            }
        }
    }

    private fun setUpAlbum(sizeMode: SizeMode) {
        binding.apply {
            optionPackageCard.visibility = View.GONE
            cobaView.visibility = View.GONE
            squareThumbnailCard.visibility = View.GONE
            smallVerticalView.visibility = View.GONE
            funAlbumLayoutView.visibility = View.VISIBLE
            when (sizeMode) {
                SizeMode.ALBUM -> {
                    squareImageView.visibility = View.VISIBLE
                    rectangleImageView.visibility = View.GONE
                }
                SizeMode.POSTER -> {
                    squareImageView.visibility = View.GONE
                    rectangleImageView.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        }
    }

    private fun setUpSmallVerticalCard(sizeMode: SizeMode) {
        binding.apply {
            optionPackageCard.visibility = View.GONE
            cobaView.visibility = View.GONE
            squareThumbnailCard.visibility = View.GONE
            smallVerticalView.visibility = View.VISIBLE
            funAlbumLayoutView.visibility = View.GONE
        }
    }

    private fun setupInfoCard() {
        price = 0
        originalPrice = 0
        hasPrice = false
        // set ratio

        iconImage = ""

        binding.apply {
            val layoutParamsRatio = containerView.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsRatio.dimensionRatio = "H,3:1"
            containerView.layoutParams = layoutParamsRatio
            containerView.setPadding(0, 24, 0, 24)

            optionPackageCard.visibility = View.GONE
            cobaView.visibility = View.VISIBLE
            smallVerticalView.visibility = View.GONE
            funAlbumLayoutView.visibility = View.GONE
            rightGapView.visibility = View.GONE
        }
    }
}