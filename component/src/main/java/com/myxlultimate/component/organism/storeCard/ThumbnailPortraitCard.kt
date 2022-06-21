package com.myxlultimate.component.organism.storeCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import com.myxlultimate.component.databinding.OrganismThumbnailPotraitBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class ThumbnailPortraitCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var binding: OrganismThumbnailPotraitBinding? = null

    var icon = ""
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

    var imageUrl = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var showPlayButton = false
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.squareThumbnailCard?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.squareThumbnailTitleView?.text = title
        binding?.squareThumbnailImageView?.imageSource = imageUrl
        binding?.squareThumbnailIconView?.imageSource = icon
        binding?.squareThumbnailPlayButtonImageView?.visibility =
            if (showPlayButton) View.VISIBLE else View.GONE
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismThumbnailPotraitBinding.inflate(LayoutInflater.from(context), this, true)
    }


}