package com.myxlultimate.component.organism.addonCard

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_addon_card.view.*
import kotlinx.android.synthetic.main.organism_transaction_total_price_footer.view.*

class AddonCard @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs){

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var expRemain = ""
        set(value) {
            field = value
            expRemainView.text = value
    }

    // ----------------------------------------------------------------------------------

    var afterPrice = 0
        set(value) {
            field = value
            afterPriceView.text =
                context.getString(R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(value.toLong(), true)
                )
        }

    // ----------------------------------------------------------------------------------

    var beforePrice = 0
        set(value) {
            field = value
            beforePriceView.apply {
                text =
                    if (value > 0)
                        context.getString(R.string.indonesian_rupiah_balance_remaining,
                            ConverterUtil.convertDelimitedNumber(value.toLong(), true)
                        )
                    else ""
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

    var image = ""
        set(value) {
            field = value
            iconView.imageSource = value
        }


    // ----------------------------------------------------------------------------------

    var subtitle = ""
        set(value) {
            field = value
            subtitleView.text = value
            IsEmptyUtil.setVisibility(value,subtitleView)
        }
    // ----------------------------------------------------------------------------------

    var hasDetailButton = false
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, seeDetailView)
        }

    var onPressedDetailButton: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(seeDetailView, value)
            TouchFeedbackUtil.attach(nextButtonView, value)
        }


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_addon_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.addonCardAttr)
        typedArray.run {
            title = getString(R.styleable.addonCardAttr_title)?: ""
            expRemain = getString(R.styleable.addonCardAttr_expRemain)?: ""
            afterPrice = getInt(R.styleable.addonCardAttr_afterPrice, 0)
            beforePrice = getInt(R.styleable.addonCardAttr_beforePrice, 0)
            hasDetailButton = getBoolean(R.styleable.addonCardAttr_hasDetailButton, false)
            image = getString(R.styleable.addonCardAttr_iconImage)?:""
            subtitle = getString(R.styleable.addonCardAttr_subtitle)?:""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(seeDetailView, onPressedDetailButton)
        TouchFeedbackUtil.attach(nextButtonView, onPressedDetailButton)
    }
}