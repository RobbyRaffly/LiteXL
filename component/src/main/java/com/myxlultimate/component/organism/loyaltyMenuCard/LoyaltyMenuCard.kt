package com.myxlultimate.component.organism.loyaltyMenuCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_menu_card.view.*
import kotlinx.android.synthetic.main.organism_loyalty_menu_card.view.shimmerLayout

class LoyaltyMenuCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var imageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value

            backgroundView.imageSourceType = value
            refreshView()
        }

    var imageSource: Any? = null
        set(value) {
            field = value

            backgroundView.imageSource = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value

            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var topName = ""
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value,topNameView)

            topNameView.text = value

        }

    // ----------------------------------------------------------------------------------

    var subTitle = ""
        set(value) {
            field = value

            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }


    var hasRightArrow: Boolean = false
        set(value) {
            field = value
            refreshView()
        }


    var isShimmerOn = false
        set(value) {
            field = value
            if(value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
            mainViewTop.visibility = if(value) View.GONE else View.VISIBLE
            mainViewBottom.visibility = if(value) View.GONE else View.VISIBLE
            shimmerLayout.visibility = if(value) View.VISIBLE else View.GONE
        }

    private fun refreshView() {
        mainViewTop.visibility = if(hasRightArrow) View.INVISIBLE else View.VISIBLE
        rightArrowView.visibility = if(hasRightArrow) View.VISIBLE else View.INVISIBLE

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_menu_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyMenuCardAttr)
        typedArray.run {
            imageSourceType = ImageSourceType.values()[getInt(
                R.styleable.LoyaltyMenuCardAttr_imageSourceType,
                3 // BASE64
            )]
            name = getString(R.styleable.LoyaltyMenuCardAttr_name) ?: ""
            topName = getString(R.styleable.LoyaltyMenuCardAttr_topName)?:""

            isShimmerOn = getBoolean(R.styleable.LoyaltyMenuCardAttr_isShimmerOn,false)
            hasRightArrow = getBoolean(R.styleable.LoyaltyMenuCardAttr_hasRightArrow,false)

            if (imageSourceType == ImageSourceType.DRAWABLE) {
                imageSource = getDrawable(R.styleable.LoyaltyMenuCardAttr_imageSource)
            } else {
                imageSource = getString(R.styleable.LoyaltyMenuCardAttr_imageSource) ?: ""
            }
        }

        TouchFeedbackUtil.attach(containerView, onPress)
    }
}