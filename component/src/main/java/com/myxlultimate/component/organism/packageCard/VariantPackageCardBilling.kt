package com.myxlultimate.component.organism.packageCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_package_card_billing_variant.view.*

class VariantPackageCardBilling @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultStartColor = "#c40d42"
    private val defaultEndColor = "#18448A"

    // ----------------------------------------------------------------------------------
    var showLoyaltyBottomView = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, loyaltyBottomLayoutView)
        }

    // ----------------------------------------------------------------------------------

    var loyaltyBottomTitle = ""
        set(value) {
            field = value
            loyaltyBottomTitleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var startHexColor = defaultStartColor
        set(value) {
            field = value
            setUpColor()
        }

    // ----------------------------------------------------------------------------------

    var endHexColor = defaultEndColor
        set(value) {
            field = value
            setUpColor()
        }

    // ----------------------------------------------------------------------------------

    var titleItemFirst = ""
        set(value) {
            field = value
            titleFirst.text = value
        }

    // ----------------------------------------------------------------------------------

    var titleItemSecond = ""
        set(value) {
            field = value
            titleSecond.text = value
        }

    // ----------------------------------------------------------------------------------

    var titleItemThird = ""
        set(value) {
            field = value
            textItemFirst.text = value
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_package_card_billing_variant, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VariantPackageCardAttr)
        typedArray.run {
            showLoyaltyBottomView =
                typedArray.getBoolean(R.styleable.VariantPackageCardAttr_showLoyaltyBottom, false)
            loyaltyBottomTitle =
                typedArray.getString(R.styleable.VariantPackageCardAttr_loyaltyBottomTitle) ?: ""
            titleItemFirst =
                typedArray.getString(R.styleable.VariantPackageCardAttr_loyaltyBottomTitle) ?: ""
            titleItemSecond = typedArray.getString(R.styleable.VariantPackageCardAttr_loyaltyBottomTitle) ?: ""
            titleItemThird =
                typedArray.getString(R.styleable.VariantPackageCardAttr_loyaltyBottomTitle) ?: ""
            startHexColor =
                typedArray.getString(R.styleable.VariantPackageCardAttr_startColor) ?: startHexColor
            endHexColor =
                typedArray.getString(R.styleable.VariantPackageCardAttr_endColor) ?: endHexColor
        }
        typedArray.recycle()
    }

    private fun setUpColor() {
        DrawableUtil.CreateGradientBackground(context, startHexColor, endHexColor, 16f)?.let {
            loyaltyBottomLayoutView.background = it
        }
    }
}