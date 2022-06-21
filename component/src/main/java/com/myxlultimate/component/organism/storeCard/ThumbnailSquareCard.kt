package com.myxlultimate.component.organism.storeCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismItemThumbnailSquareBinding
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.token.imageView.ImageSourceType.BASE64
import com.myxlultimate.component.token.imageView.ImageSourceType.DRAWABLE
import com.myxlultimate.component.util.TouchFeedbackUtil

class ThumbnailSquareCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var binding: OrganismItemThumbnailSquareBinding? = null

    var usePlayButton: Boolean = false
        set(value) {
            field = value
            binding?.squareThumbnailPlayButtonImageView?.isVisible = value
        }

    var useSubtitle: Boolean = false
        set(value) {
            field = value
            binding?.squareThumbnailSubtitleView?.isVisible = value
        }

    var useRightIcon: Boolean = false
        set(value) {
            field = value
            binding?.squareThumbnailArrowView?.isVisible = value
        }

    var titleText: String = ""
        set(value) {
            field = value
            binding?.squareThumbnailTitleView?.text = value
        }

    var subtitleText: String = ""
        set(value) {
            field = value
            binding?.squareThumbnailSubtitleView?.text = value
        }

    var rightIcon: Any = R.drawable.ic_play_button_image
        set(value) {
            field = value
            binding?.squareThumbnailArrowView?.imageSource = value
        }

    var rightIconType: ImageSourceType = DRAWABLE
        set(value) {
            field = value
            binding?.squareThumbnailArrowView?.imageSourceType = value
        }

    var imageSourceType: ImageSourceType = ImageSourceType.URL
        set(value) {
            field = value
            binding?.squareThumbnailImageView?.imageSourceType = value
        }

    var leftIcon: Any = ""
        set(value) {
            field = value
            binding?.squareThumbnailIconView?.imageSource = value
        }

    var leftIconType: ImageSourceType = BASE64
        set(value) {
            field = value
            binding?.squareThumbnailIconView?.imageSourceType = value
        }

    var imageSource: Any = ""
        set(value) {
            field = value
            binding?.squareThumbnailImageView?.imageSource = value
        }

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.squareThumbnailCard?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismItemThumbnailSquareBinding.inflate(LayoutInflater.from(context), this, true)
    }
}