package com.myxlultimate.component.organism.benefitComparisonFullCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem
import com.myxlultimate.component.organism.benefitComparisonFullCard.adapter.BenefitComparisonFullAdapter
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_benefit_comparison_full_card.view.*

class BenefitComparisonFullCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var imageTitle = ""
        set(value) {
            field = value
            refreshView()
        }

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

    var price = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var priceProRate = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isSingleCard = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isImportantSubTitle = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var items = mutableListOf<PackageBenefitItem.Data>()
        set(value) {
            field = value
            benefitComparisonFullAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    private val benefitComparisonFullAdapter by lazy {
        BenefitComparisonFullAdapter()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        ivBenefitImage.imageSource = imageTitle
        tvTitle.text = title
        tvSubTitle.text = subTitle
        tvPrice.text = price
        tvDescription.text = description
        tvProRatePrice.text = priceProRate

        IsEmptyUtil.setVisibility(subTitle, tvSubTitle)
        IsEmptyUtil.setVisibility(priceProRate, tvProRatePrice)
        if (priceProRate.isNotEmpty()) {
            tvDescription.visibility = View.GONE
        } else {
            IsEmptyUtil.setVisibility(description, tvDescription)
        }

        if (isSingleCard) {
            view.visibility = View.GONE
            rvBenefitList.visibility = View.GONE
            cvBaseCardView.radius = ConverterUtil.dpToPx(context, 16F)
        } else {
            view.visibility = View.VISIBLE
            rvBenefitList.visibility = View.VISIBLE
            cvBaseCardView.radius = ConverterUtil.dpToPx(context, 8F)
        }

        tvSubTitle.setTextColor(
            ContextCompat.getColor(
                context,
                if (isImportantSubTitle) R.color.mud_palette_basic_black else R.color.mud_palette_basic_dark_grey
            )
        )
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_benefit_comparison_full_card, this, true)


        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BenefitComparisonFullCard)
        typedArray.run {
            imageTitle = getString(R.styleable.BenefitComparisonFullCard_imageTitle) ?: ""
            title = getString(R.styleable.BenefitComparisonFullCard_title) ?: ""
            subTitle = getString(R.styleable.BenefitComparisonFullCard_subtitle) ?: ""
            price = getString(R.styleable.BenefitComparisonFullCard_benefitPrice) ?: ""
            priceProRate = getString(R.styleable.BenefitComparisonFullCard_proRatePrice) ?: ""
            description = getString(R.styleable.BenefitComparisonFullCard_description) ?: ""
            isSingleCard = getBoolean(R.styleable.BenefitComparisonFullCard_isSingleCard, false)
            isImportantSubTitle = getBoolean(R.styleable.BenefitComparisonFullCard_isImportantSubTitle, false)
        }

        rvBenefitList.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 12))
            adapter = benefitComparisonFullAdapter.also {
                it.items = items
            }
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}