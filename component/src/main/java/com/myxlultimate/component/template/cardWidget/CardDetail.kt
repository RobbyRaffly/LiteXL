package com.myxlultimate.component.template.cardWidget

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.template_card_detail.view.*

class CardDetail @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var isEnough = true
        set(value) {
            field = value

            if (!isEnough) {
                priceView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
            } else {
                val typedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(R.attr.colorBackgroundPrimary, typedValue, true)
                priceView.setTextColor(getColor(context, typedValue.resourceId))
            }
        }

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value

            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var validity = ""
        set(value) {
            field = value

            validityView.text = value
        }

    // ----------------------------------------------------------------------------------

    var price = ""
        set(value) {
            field = value

            priceView.text = value
        }

    // ----------------------------------------------------------------------------------

    var originalPrice = ""
        set(value) {
            field = value
            originalPriceView.apply {
                text = value
                if (text.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        foreground = context.getDrawable(R.drawable.strikethrough_foreground)
                    } else {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
                IsEmptyUtil.setVisibility(value,this)
            }
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value

            informationView.apply {
                text = value
                IsEmptyUtil.setVisibility(value,this)
            }
        }

    // ----------------------------------------------------------------------------------

    var hasNextButton = true
        set(value) {
            field = value

            nextButtonView.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.ASSET
        set(value) {
            field = value
            iconView.imageSourceType = value
        }

    var image: Any? = null
        set(value) {
            field = value
            iconView.imageSource = value
            if (value == "") {
                iconView.visibility = View.GONE
            } else {
                iconView.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    var onNextPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(nextButtonView, value)
            TouchFeedbackUtil.attach(containerView,value)
        }

    // ----------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

    var hasSeeDetail : Boolean = true
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value,seeDetailView)
        }

    // ----------------------------------------------------------------------------------

    var isShimmerOn : Boolean = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.startShimmer()
                originalView.visibility = View.GONE
                shimmerLayout.visibility = View.VISIBLE
            } else {
                shimmerLayout.stopShimmer()
                originalView.visibility = View.VISIBLE
                shimmerLayout.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_card_detail, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TemplateCardDetailAttr)
        typedArray.run {
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.TemplateCardDetailAttr_imageSourceType, 0)]
            image = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.TemplateCardDetailAttr_iconImage) ?: ""
            } else {
                getString(R.styleable.TemplateCardDetailAttr_iconImage) ?:""
            }
            name = getString(R.styleable.TemplateCardDetailAttr_name) ?: ""
            information = getString(R.styleable.TemplateCardDetailAttr_information)?:""
            validity = getString(R.styleable.TemplateCardDetailAttr_validity) ?: ""
            price = getString(R.styleable.TemplateCardDetailAttr_priceText)?:""
            originalPrice = getString(R.styleable.TemplateCardDetailAttr_originalPriceText)?:""
            hasNextButton = getBoolean(R.styleable.TemplateCardDetailAttr_hasNextButton, true)
            hasSeeDetail = getBoolean(R.styleable.TemplateCardDetailAttr_hasSeeDetail,true)
            isShimmerOn = getBoolean(R.styleable.TemplateCardDetailAttr_isShimmerOn,false)
        }

//        TouchFeedbackUtil.attach(containerView, onCardPress)
    }
}