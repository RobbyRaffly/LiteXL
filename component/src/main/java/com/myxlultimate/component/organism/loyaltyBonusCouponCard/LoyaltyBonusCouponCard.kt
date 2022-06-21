package com.myxlultimate.component.organism.loyaltyBonusCouponCard


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.DrawableUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_bonus_coupon_card.view.*

class LoyaltyBonusCouponCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val defaultStartColor = "#c40d42"
    private val defaultEndColor = "#18448A"

    data class Data(
        val title: String = "",
        val information: String = "",
        val rightImage: String = "",
        val label: String = "",
        val startLabelHexColor: String = "#c40d42",
        val endLabelHexColor: String = "#18448A"
    )

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
            IsEmptyUtil.setVisibility(value, titleView)
        }

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            refreshView()
            IsEmptyUtil.setVisibility(value,loyaltyCouponLayoutView)
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            refreshView()
            IsEmptyUtil.setVisibility(value, informationView)
        }

    // ----------------------------------------------------------------------------------


    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

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

    // ----------------------------------------------------------------------------------
    var imageSourceType: ImageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
            refreshView()
        }

    var rightImage: Any? = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.text = title
        informationView.text = information
        rightImageView.imageSourceType = imageSourceType
        rightImageView.imageSource = rightImage
        labelRibbonView.text = label
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_bonus_coupon_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyBonusCouponCard)
        typedArray.run {
            title = getString(R.styleable.LoyaltyBonusCouponCard_title) ?: ""
            information = getString(R.styleable.LoyaltyBonusCouponCard_information) ?: ""
            imageSourceType = ImageSourceType.values()[typedArray.getInt(R.styleable.LoyaltyBonusCouponCard_imageSourceType, 3)]
            rightImage = if (imageSourceType == ImageSourceType.DRAWABLE) {
                typedArray.getDrawable(R.styleable.LoyaltyBonusCouponCard_imageSource)
            } else {
                typedArray.getString(R.styleable.LoyaltyBonusCouponCard_imageSource)
            }
            startHexColor = getString(R.styleable.LoyaltyBonusCouponCard_startColor)?:defaultStartColor
            endHexColor = getString(R.styleable.LoyaltyBonusCouponCard_endColor)?:defaultEndColor
            label = getString(R.styleable.LoyaltyBonusCouponCard_label)?:""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView, onPress)
    }

    private fun setUpColor() {
        DrawableUtil.CreateGradientBackground(context, startHexColor, endHexColor, 10f)?.let {
            loyaltyCouponLayoutView.background = it
        }

    }
}