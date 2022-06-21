package com.myxlultimate.component.molecule.quotaDetail

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismQuotaDetailRowSummaryBinding
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil

class QuotaDetailRowSummaryWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val binding = OrganismQuotaDetailRowSummaryBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    // ----------------------------------------------------------------------------------

    data class Data(
        val title: String? = "",
        val subTitle: String? = "",
        val imageSourceType: ImageSourceType? = ImageSourceType.DRAWABLE,
        val imageSource: Any? = "",
        val imageSourceTypeIcon: ImageSourceType? = ImageSourceType.DRAWABLE,
        val imageSourceIcon: Any? = ""
    )

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var subTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            binding.iconView.imageSourceType = imageSourceType
        }

    // ----------------------------------------------------------------------------------

    var imageSource: Any? = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSourceTypeIcon = ImageSourceType.BASE64
        set(value) {
            field = value
            binding.rightIconView.imageSourceType = imageSourceTypeIcon
        }

    // ----------------------------------------------------------------------------------

    var imageSourceIcon: Any? = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DompetPaymentWidgetAttr)
        typedArray.run {
            imageSourceType = ImageSourceType.values()[typedArray.getInt(
                R.styleable.DompetPaymentWidgetAttr_imageSourceType,
                2
            )]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.DompetPaymentWidgetAttr_imageSource)
            } else {
                typedArray.getString(R.styleable.DompetPaymentWidgetAttr_imageSource)
            }
            imageSourceTypeIcon = ImageSourceType.values()[typedArray.getInt(
                R.styleable.DompetPaymentWidgetAttr_imageSourceTypeIcon,
                2
            )]
            imageSourceIcon = if (imageSourceTypeIcon == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.DompetPaymentWidgetAttr_imageSourceIcon)
            } else {
                typedArray.getString(R.styleable.DompetPaymentWidgetAttr_imageSourceIcon)
            }
            title = getString(R.styleable.DompetPaymentWidgetAttr_title) ?: ""
            subTitle = getString(R.styleable.DompetPaymentWidgetAttr_subtitle) ?: ""
        }
        typedArray.recycle()

    }

    // ----------------------------------------------------------------------------------

    fun refreshView() {
        binding.titleView.text = title
        binding.subTitleView.text = subTitle
        IsEmptyUtil.setVisibility(subTitle, binding.subTitleView)
        if (imageSource == "") {
            val typedValue = TypedValue()
            context.theme?.resolveAttribute(R.attr.emptyPackagePngIcon, typedValue, true)
            binding.iconView.imageSource = typedValue.resourceId
        } else {
            binding.iconView.imageSource = imageSource
        }

        if (imageSourceIcon == "") {
            val typedValue = TypedValue()
            context.theme?.resolveAttribute(R.attr.emptyPackagePngIcon, typedValue, true)
            binding.rightIconView.imageSource = typedValue.resourceId
        } else {
            binding.rightIconView.imageSource = imageSourceIcon
        }
    }
}