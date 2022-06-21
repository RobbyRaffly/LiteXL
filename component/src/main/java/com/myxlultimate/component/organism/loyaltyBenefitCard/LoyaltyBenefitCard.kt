package com.myxlultimate.component.organism.loyaltyBenefitCard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.loyaltyBenefitCard.adapter.TieringIconAdapter
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.token.imageView.ImageView
import com.myxlultimate.component.util.ColorUtil
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.ListUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_addon_card.view.*
import kotlinx.android.synthetic.main.organism_flat_quick_menu_group.view.*
import kotlinx.android.synthetic.main.organism_loyalty_benefit_card.view.*
import kotlinx.android.synthetic.main.organism_loyalty_benefit_card.view.iconView
import kotlinx.android.synthetic.main.organism_loyalty_benefit_card.view.titleView

class LoyaltyBenefitCard  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultStartColor = "#c40d42"
    private val defaultEndColor = "#18448A"
    private val defaultCircleBg = "#ffffff"

    private var tieringIconAdapter: TieringIconAdapter? = null

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

    var icon = ""
        set(value) {
            field = value
            refreshView()
        }

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    var bonusAmountString = ""
        set(value) {
            field = value
            refreshView()
        }

    var onDetailPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(detailCta, value)
        }

    var isLock: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    var circleBg: String = defaultCircleBg
        set(value) {
            field = value
            refreshView()
        }

    var iconList: List<ImageView> = listOf()
        set(value) {
            field = value
            refreshView()
        }

    var fontColor: Int = ContextCompat.getColor(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value
            refreshView()
        }

    var ctaColor: Int = ContextCompat.getColor(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value
            refreshView()
        }

    var hasLevel: Boolean = true
        set(value) {
            field = value
            refreshView()
        }

    var lineViewColor: Int = ContextCompat.getColor(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value
            refreshView()
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_benefit_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyBenefitCardAttr)
        typedArray.run {
            startHexColor = getString(R.styleable.LoyaltyBenefitCardAttr_startColor) ?: defaultStartColor
            endHexColor = getString(R.styleable.LoyaltyBenefitCardAttr_endColor) ?: defaultEndColor
            title = getString(R.styleable.LoyaltyBenefitCardAttr_title) ?: ""
            bonusAmountString =
                getString(R.styleable.LoyaltyBenefitCardAttr_bonusAmountString) ?: ""
            isLock = getBoolean(R.styleable.LoyaltyBenefitCardAttr_isLock, false)
            icon = getString(R.styleable.LoyaltyBenefitCardAttr_icon) ?: ""
        }
        setListeners()

        tieringIconAdapter = TieringIconAdapter()
        tieringIconRv.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 4, true))
            adapter = tieringIconAdapter
        }

        typedArray.recycle()
    }

    private fun setUpColor() {
        DrawableUtil.CreateGradientBackground(context, startHexColor, endHexColor)?.let {
            containerLoyaltyBenefitCardView.background = it
        }
    }

    private fun refreshView() {
        titleView.text = title
        bonusAmountView.text = bonusAmountString
        lockIconView.visibility = if (isLock) View.VISIBLE else View.GONE
        iconView.imageSource = icon
        ColorUtil.parseColor(circleBg, {
            iconWrapperView.backgroundTintList = ColorStateList.valueOf(it)
        })
        tieringIconAdapter?.items = iconList
        iconGroupView.visibility = if (iconList.isEmpty() || hasLevel) View.GONE else View.VISIBLE
        titleView.setTextColor(fontColor)
        bonusAmountView.setTextColor(fontColor)
        bonusText.setTextColor(fontColor)
        increaseText.setTextColor(fontColor)
        detailCta.setTextColor(ctaColor)
        lineView.setBackgroundColor(lineViewColor)

        if (hasLevel) {
            bonusText.visibility = View.VISIBLE
            bonusAmountView.visibility = View.VISIBLE

            increaseText.visibility = View.GONE
        } else {
            bonusText.visibility = View.GONE
            bonusAmountView.visibility = View.GONE

            increaseText.visibility = View.VISIBLE

        }
    }

    private fun setListeners() {
        TouchFeedbackUtil.attach(detailCta) {
            onDetailPress
        }
    }

}