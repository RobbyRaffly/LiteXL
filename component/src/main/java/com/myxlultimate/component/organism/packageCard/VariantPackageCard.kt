package com.myxlultimate.component.organism.packageCard

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.forEach
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.packageVarianBenefit.PackageVariantBenefitGroup
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_package_card_variant.view.*

class VariantPackageCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultStartColor = "#c40d42"
    private val defaultEndColor = "#18448A"

    var items = mutableListOf<PackageVariantBenefitGroup.Data>()
        set(value) {
            if (field.size > 0) return
            field = value
            if(value.size==0){
                visibility = View.GONE
                return
            }
            visibility = View.VISIBLE
            tabSwitchView.visibility = View.GONE

            value.forEachIndexed { index, data ->
                if (childCounter >= 3) return
                if (childCounter >= 1) tabSwitchView.visibility = View.VISIBLE

                val child = PackageVariantBenefitGroup(context)
                child.name = data.name
                child.benefits = data.benefits
                if (childCounter > 0) child.visibility = View.GONE
                variantPackageContainerView.addView(child)

                when (index) {
                    0 -> {
                        firstLabel = data.name
                    }
                    1 -> {
                        secondLabel = data.name
                    }
                    else -> thirdLabel = data.name
                }

                childCounter++
            }
        }

    // ----------------------------------------------------------------------------------

    var setActiveIndex: Int = 0
        set(value) {
            field = value

            tabSwitchView.activeIndex = value
            switchTab(value)
        }

    // ----------------------------------------------------------------------------------

    var firstLabel: String = ""
        set(value) {
            field = value

            tabSwitchView.firstLabel = value
        }

    // ----------------------------------------------------------------------------------

    var secondLabel: String = ""
        set(value) {
            field = value

            tabSwitchView.secondLabel = value
        }


    // ----------------------------------------------------------------------------------

    var thirdLabel: String = ""
        set(value) {
            field = value

            tabSwitchView.thirdLabel = value
        }


    var isShimmerOn: Boolean = false
        set(value) {
            field = value
            tabSwitchView.isShimmerOn = value
            if(value){
                tabSwitchView.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    var showLoyaltyBottomView = false
    set(value) {
        field = value
        IsEmptyUtil.setVisibility(value,loyaltyBottomLayoutView)
    }

    // ----------------------------------------------------------------------------------

    var loyaltyBottomIcon = ""
    set(value) {
        field = value
        loyaltyBottomIconView.imageSource = value
        IsEmptyUtil.setVisibility(value,iconWrapperView)
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

    var onBottomLoyaltyView  : (() -> Unit)? = null
    set(value) {
        field = value
        TouchFeedbackUtil.attach(loyaltyBottomLayoutView,value)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var onTabChange: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var childCounter = 0

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_package_card_variant, this, true)

        tabSwitchView.onChange = { switchTab(it) }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VariantPackageCardAttr)
        typedArray.run {
            isShimmerOn = typedArray.getBoolean(R.styleable.VariantPackageCardAttr_isShimmerOn, false)
            showLoyaltyBottomView = typedArray.getBoolean(R.styleable.VariantPackageCardAttr_showLoyaltyBottom,false)
            loyaltyBottomIcon = typedArray.getString(R.styleable.VariantPackageCardAttr_loyaltyBottomIcon)?:""
            loyaltyBottomTitle = typedArray.getString(R.styleable.VariantPackageCardAttr_loyaltyBottomTitle)?:""
            startHexColor = typedArray.getString(R.styleable.VariantPackageCardAttr_startColor)?:startHexColor
            endHexColor = typedArray.getString(R.styleable.VariantPackageCardAttr_endColor)?:endHexColor
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (variantPackageContainerView == null || child !is PackageVariantBenefitGroup || childCounter >= 2) {
            super.addView(child, index, params)
        } else {
            if (childCounter > 0) {
                child.visibility = View.GONE
                tabSwitchView.visibility = View.VISIBLE

                secondLabel = child.name
            } else {
                firstLabel = child.name
            }

            variantPackageContainerView.addView(child, index, params)
            childCounter++
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun switchTab(index: Int) {
        val firstChild = variantPackageContainerView.getChildAt(0) as PackageVariantBenefitGroup
        val secondChild = variantPackageContainerView.getChildAt(1) as PackageVariantBenefitGroup
        val thirdChild : PackageVariantBenefitGroup ? = if (items.size > 2) variantPackageContainerView.getChildAt(2) as PackageVariantBenefitGroup else null



        firstChild.visibility = if (index == 0) View.VISIBLE else View.GONE
        secondChild.visibility = if (index == 1) View.VISIBLE else View.GONE
        thirdChild?.visibility = if (index == 2) View.VISIBLE else View.GONE

        onTabChange?.let { it(index) }
    }

    private fun setUpColor() {
        DrawableUtil.CreateGradientBackground(context,startHexColor,endHexColor,16f)?.let {
            loyaltyBottomLayoutView.background = it
        }
    }
}