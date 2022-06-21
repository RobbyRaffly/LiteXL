package com.myxlultimate.component.organism.topUpSubscriptionItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_top_up_subscription_item.view.*

class TopUpSubscriptionItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val imageSourceType: ImageSourceType,
        val image: Any?,
        val isShimmerOn: Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value

            messageView.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value

            iconView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var image :Any? = null
        set(value) {
            field = value

            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(contentView, value)
        }
    
    // ----------------------------------------------------------------------------------

    var isShimmerOn: Boolean = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.apply {
                    visibility = View.VISIBLE
                    startShimmer()
                }
                originalView.visibility = View.GONE
            } else {
                shimmerLayout.apply {
                    visibility = View.GONE
                    stopShimmer()
                }
                originalView.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_top_up_subscription_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopUpSubscriptionItemAttr)
        typedArray.run {
            title = getString(R.styleable.TopUpSubscriptionItemAttr_title) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.TopUpSubscriptionItemAttr_imageSourceType, 2)]
            image = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.CustomImageViewAttr_imageSource)
            } else {
                getString(R.styleable.CustomImageViewAttr_imageSource)
            }
            isShimmerOn = getBoolean(R.styleable.TopUpSubscriptionItemAttr_isShimmerOn, false)
        }

        TouchFeedbackUtil.attach(contentView, onPress)
    }

}