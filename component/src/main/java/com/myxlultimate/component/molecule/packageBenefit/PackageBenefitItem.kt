package com.myxlultimate.component.molecule.packageBenefit

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.MoleculePackageBenefitItemBinding
import com.myxlultimate.component.enums.QuotaType
import com.myxlultimate.component.molecule.packageBenefit.enums.BenefitMode
import com.myxlultimate.component.molecule.packageBenefit.enums.BenefitMode.WITHCTA
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil

class PackageBenefitItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    data class Data(
        val name: String,
        val iconImageSourceType: ImageSourceType = ImageSourceType.BASE64,
        val iconImage: Any? = "",
        val information: String,
        val quotaType: QuotaType = QuotaType.DATA,
        val amount: Long,
        val amountString: String = "",
        val isUnlimited: Boolean,
        val isShimmerOn: Boolean = false,
        val labelCta: String = "",
        val bigTitleCta: String = "",
        val benefitMode: BenefitMode = BenefitMode.WITHOUTCTA,
        val onCtaPress: ((Int) -> Unit)? = null,
        val hasBorderTop: Boolean = false,
        val hasBorderBottom: Boolean = false,
        val hasGapTop: Boolean = false,
        val hasGapBottom: Boolean = false,
        val isImageSize32: Boolean = false,
        val use16DpIcon: Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private var binding: MoleculePackageBenefitItemBinding? = null

    var quotaType = QuotaType.DATA
        set(value) {
            field = value

            this.amount = amount
        }

    var labelCta = ""
        set(value) {
            field = value
            binding?.titleCta?.text = value
        }

    var bigLabelCta = ""
        set(value) {
            field = value
            binding?.bigTitleCta?.text = value
        }
    // ----------------------------------------------------------------------------------

    var benefitMode = BenefitMode.WITHOUTCTA
        set(value) {
            field = value
            binding?.apply {
                titleCta.isVisible = benefitMode == WITHCTA
                bigTitleCta.isVisible = benefitMode == WITHCTA
                IsEmptyUtil.setVisibility(labelCta, titleCta)
                IsEmptyUtil.setVisibility(bigLabelCta, bigTitleCta)
            }
        }

    // ----------------------------------------------------------------------------------
    var onCtaPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.apply {
                TouchFeedbackUtil.attach(titleCta, value)
                TouchFeedbackUtil.attach(bigTitleCta, value)
            }
        }

    // ----------------------------------------------------------------------------------
    var name = ""
        set(value) {
            field = value

            binding?.nameView?.text = value
        }

    // ----------------------------------------------------------------------------------

    var iconImageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var iconImage: Any? = ""
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isImageSize32 = false
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            binding?.informationView?.isVisible = value.isNotEmpty()
            binding?.informationView?.text = value

            if(value.isEmpty()) binding?.nameView?.updatePadding(top = ConverterUtil.dpToPx(context, 6F).toInt())
        }

    // ----------------------------------------------------------------------------------

    var msisdn = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var use16DpIcon: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var amount: Long = 0
        set(value) {
            field = value
            if (value < 1) {
                binding?.priceView?.text = msisdn.ifEmpty { "" }
            } else {
                binding?.priceView?.text = getPriceViewText(value)
            }
        }

    var amountString: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                binding?.priceView?.text = value
            }
        }

    var isUnlimited: Boolean = false
        set(value) {
            field = value

            binding?.apply {
                unlimitedFlagView.isVisible = value
                priceView.isVisible = !value
            }
        }

    var isRp: Boolean = false
        set(value) {
            field = value

            binding?.apply {
                priceView.isVisible = value

                if (value) {
                    priceView.text = "Rp${amount}"
                }
            }
        }

    var isTitleOnly: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding?.priceView?.text = ""
            }
        }

    var isShimmerOn: Boolean = false
        set(value) {
            field = value

            binding?.apply {
                shimmerLayout.isVisible = value
                contentView.isVisible = !value
            }
        }

    var hasBorderTop: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    var hasBorderBottom: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    var hasGapTop: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    var hasGapBottom: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    var useListContent: Boolean = false
        set(value) {
            field = value
            binding?.listContent?.isVisible = value
        }

    val listContent: RecyclerView? by lazy { binding?.listContent }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    private fun getPriceViewText(value: Long) = when (quotaType) {
        QuotaType.DATA -> {
            val data = ConverterUtil.convertDataUnit(value.toFloat())
            context.getString(R.string.quota_data_value, data.first, data.second)
        }
        QuotaType.TEXT -> {
            resources.getString(R.string.quota_text_value, value)
        }
        QuotaType.VOICE -> {
            val voice = ConverterUtil.convertVoice(value.toInt())
            resources.getQuantityString(R.plurals.quota_voice_value, voice, voice)
        }
        QuotaType.FAMILY_SLOT -> {
            resources.getQuantityString(
                R.plurals.quota_family_package_value,
                value.toInt(),
                value.toInt()
            )
        }
        QuotaType.DEVICE -> {
            resources.getQuantityString(
                R.plurals.quota_device_value,
                value.toInt(),
                value.toInt()
            )
        }
        QuotaType.CUSTOM -> {
            val voice = ConverterUtil.convertVoice(value.toInt())
            resources.getQuantityString(R.plurals.quota_custom_value, voice, voice)
        }
        QuotaType.MONETARY -> {
            value.toString()
        }
        QuotaType.PRICE, QuotaType.BALANCE-> {
            resources.getString(
                R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value, true)
            )
        }
    }

    private fun refreshView() {
        binding?.apply {
            if (hasGapTop) {
                if (hasBorderTop) {
                    lineTopView.visibility = View.VISIBLE
                } else {
                    lineTopView.visibility = View.INVISIBLE
                }
            } else {
                lineTopView.visibility = View.GONE
            }

            if (hasGapBottom) {
                if (hasBorderBottom) {
                    lineBottomView.visibility = View.VISIBLE
                } else {
                    lineBottomView.visibility = View.INVISIBLE
                }
            } else {
                lineBottomView.visibility = View.GONE
            }

            iconView.apply {
                imageSourceType = iconImageSourceType
                imageSource = iconImage
                visibility = if (iconImage != "") View.VISIBLE else View.GONE
                layoutParams.width = when {
                    isImageSize32 -> ConverterUtil.dpToPx(context, 32f).toInt()
                    use16DpIcon -> ConverterUtil.dpToPx(context, 16f).toInt()
                    else -> ConverterUtil.dpToPx(context, 24f).toInt()
                }
                layoutParams.height = when {
                    isImageSize32 -> ConverterUtil.dpToPx(context, 32f).toInt()
                    use16DpIcon -> ConverterUtil.dpToPx(context, 16f).toInt()
                    else -> ConverterUtil.dpToPx(context, 24f).toInt()
                }
            }
            if (msisdn.isNotEmpty()) {
                priceView.text = msisdn
            }

            if (use16DpIcon) {
                priceView.visibility = View.GONE
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    nameView.setTextAppearance(context, R.style.TextAppearance_MudComponents_H6)
                } else {
                    nameView.setTextAppearance(R.style.TextAppearance_MudComponents_H6)
                }
                nameView.updatePadding(bottom = ConverterUtil.dpToPx(context, 8F).toInt())
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        binding =
            MoleculePackageBenefitItemBinding.inflate(LayoutInflater.from(context), this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PackageBenefitItemAttr)
        typedArray.run {
            name = typedArray.getString(R.styleable.PackageBenefitItemAttr_name) ?: ""
            iconImageSourceType = ImageSourceType.values()[typedArray.getInt(
                R.styleable.PackageBenefitItemAttr_imageSourceType,
                3
            )]
            iconImage = if (iconImageSourceType == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.PackageBenefitItemAttr_imageSource)
            } else {
                typedArray.getString(R.styleable.PackageBenefitItemAttr_imageSource)
            }
            information = typedArray.getString(R.styleable.PackageBenefitItemAttr_information) ?: ""
            msisdn = typedArray.getString(R.styleable.PackageBenefitItemAttr_msisdn) ?: ""
            labelCta = typedArray.getString(R.styleable.PackageBenefitItemAttr_labelCta) ?: ""
            bigLabelCta = typedArray.getString(R.styleable.PackageBenefitItemAttr_bigLabelCta) ?: ""
            benefitMode =
                BenefitMode.values()[getInt(R.styleable.PackageBenefitItemAttr_benefitMode, 0)]
            quotaType = QuotaType.values()[getInt(R.styleable.PackageBenefitItemAttr_quotaType, 0)]
            amount = getInt(R.styleable.PackageBenefitItemAttr_amount, 0).toLong()
            isUnlimited = getBoolean(R.styleable.PackageBenefitItemAttr_isUnlimited, false)
            isShimmerOn = getBoolean(R.styleable.PackageBenefitItemAttr_isShimmerOn, false)
            isImageSize32 = getBoolean(R.styleable.PackageBenefitItemAttr_isImageSize32, false)
        }

        binding?.apply {
            TouchFeedbackUtil.attach(false, titleCta, onCtaPress)
            TouchFeedbackUtil.attach(false, bigTitleCta, onCtaPress)
        }
        typedArray.recycle()
    }
}
