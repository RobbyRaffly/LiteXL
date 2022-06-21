package com.myxlultimate.component.organism.storeCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import com.myxlultimate.component.databinding.OrganismThumbnailPosterCardBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class ThumbnailPosterCard@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var binding: OrganismThumbnailPosterCardBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var imageUrl = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.cardAlbumView?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.apply {
            rectangleImageView.imageSource = imageUrl
        }
    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismThumbnailPosterCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

}