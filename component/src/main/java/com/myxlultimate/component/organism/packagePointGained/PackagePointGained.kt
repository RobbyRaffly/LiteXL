package com.myxlultimate.component.organism.packagePointGained

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_package_point_gained.view.*

class PackagePointGained @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isShimmerOn = false
    set(value) {
        field = value
        if(value) {
            shimmerLayout.startShimmer()
        } else {
            shimmerLayout.stopShimmer()
        }
        packageLayout.visibility = if(value) View.GONE else View.VISIBLE
        shimmerLayout.visibility = if(value) View.VISIBLE else View.GONE
    }

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            iconView.imageSourceType
        }

    var iconImage: Any? = null
        set(value) {
            field = value
            if (value == null) {
                iconView.imageSourceType = ImageSourceType.DRAWABLE
                val pointTypedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(R.attr.pointLoyaltyImage, pointTypedValue, true)
                iconView.imageSource = AppCompatResources.getDrawable(context, pointTypedValue.resourceId)
            } else {
                iconView.imageSourceType = imageSourceType
                iconView.imageSource = value
            }
        }

    var amount = 0
        set(value) {
            field = value
            if(value > 0) {
                priceView.text =
                    if (amountFormat.isEmpty())
                        resources.getString(
                            R.string.organism_transaction_with_balance_point_gained_amount,
                            ConverterUtil.convertDelimitedNumber(amount.toLong(), true)
                        )
                    else
                        String.format(amountFormat, amount)
            }
        }

    var amountFormat = ""
        set(value){
            field = value
            priceView.text =
                    if(amountFormat.isEmpty())
                        resources.getString(R.string.organism_transaction_with_balance_point_gained_amount,ConverterUtil.convertDelimitedNumber(amount.toLong(),true))
                    else
                        String.format(amountFormat, amount)
        }

    var title = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                priceView.text = value
            }
        }


    var isCenter = false
    set(value) {
        field = value
        if(value) {
            priceView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            priceView.gravity = Gravity.CENTER
        } else {
            priceView.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            priceView.gravity = Gravity.START
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_package_point_gained, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.packagePointGainedAttr)
        typedArray.run {
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.packagePointGainedAttr_imageSourceType, 2)]
            iconImage = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.packagePointGainedAttr_iconImage) ?: AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_point_prepaid
                )
            } else {
                getString(R.styleable.packagePointGainedAttr_iconImage) ?: ""
            }
            amount = getInt(R.styleable.packagePointGainedAttr_amount, 0)

            title = getString(R.styleable.packagePointGainedAttr_title) ?: ""
            isCenter = getBoolean(R.styleable.packagePointGainedAttr_isCentered,false)
            isShimmerOn = getBoolean(R.styleable.packagePointGainedAttr_isShimmerOn,false)
        }
        typedArray.recycle()
    }
}