package com.myxlultimate.component.organism.loyaltyEmptyState

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import kotlinx.android.synthetic.main.organism_loyalty_empty_state.view.*

class LoyaltyEmptyState @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    data class Data(
        val imageSourceType: ImageSourceType,
        val image: Any?,
        val text: String
    )

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            emptyStateIcon.imageSourceType = value
        }

    var image: Any? = null
        set(value) {
            field = value
            emptyStateIcon.imageSource = value
        }

    var text = ""
        set(value) {
            field = value
            emptyStateText.text = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_loyalty_empty_state, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyEmptyState)
        typedArray.run {
            text = getString(R.styleable.LoyaltyEmptyState_text) ?: ""
            imageSourceType =
                ImageSourceType.values()[getInt(R.styleable.LoyaltyEmptyState_imageSourceType, 2)]
            image = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.CustomImageViewAttr_imageSource)
            } else {
                getString(R.styleable.CustomImageViewAttr_imageSource)
            }
        }
    }
}