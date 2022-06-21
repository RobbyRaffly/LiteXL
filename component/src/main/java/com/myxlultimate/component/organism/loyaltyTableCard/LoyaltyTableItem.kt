package com.myxlultimate.component.organism.loyaltyTableCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_loyalty_tier_status_detail_item.view.*

class LoyaltyTableItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String = "",
        val information: String = "",
        val iconCode: String = "",
        val startHexColor: String = "",
        val endHexColor: String = ""
    )

    private val defaultStartColor = ""
    private val defaultEndColor = ""

    var title = ""
        set(value) {
            field = value
            tierTitleView.text = value
        }

    var information = ""
        set(value) {
            field = value
            transactionInfoView.text = value
        }

    var iconCode = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value,iconWrapperView)
            loyaltyIcon.imageSource = value
        }

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


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_tier_status_detail_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyTableItem)
        typedArray.run {
            title = getString(R.styleable.LoyaltyTableItem_title) ?: ""
            information = getString(R.styleable.LoyaltyTableItem_information) ?: ""
            iconCode = getString(R.styleable.LoyaltyTableItem_iconCode) ?: ""
            startHexColor = getString(R.styleable.LoyaltyTableItem_startColor) ?: defaultStartColor
            endHexColor = getString(R.styleable.LoyaltyTableItem_endColor) ?: defaultEndColor
        }
        typedArray.recycle()
    }

    private fun setUpColor() {
        if (startHexColor.isNotEmpty() && endHexColor.isNotEmpty()) {
            DrawableUtil.CreateGradientBackground(context, startHexColor, endHexColor, 0f)?.let {
                containerView.background = it
            }
            tierTitleView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
            transactionInfoView.setTextColor(getColor(context, R.color.mud_palette_basic_white))
        } else {
            tierTitleView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
            transactionInfoView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
        }
    }


}