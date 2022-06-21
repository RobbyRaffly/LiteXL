package com.myxlultimate.component.organism.storeCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.databinding.OrganismThumbnailAlbumCardBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class ThumbnailAlbumCard  @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private var binding: OrganismThumbnailAlbumCardBinding? = null

    // ----------------------------------------------------------------------------------
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

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.funAlbumLayoutView?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.apply {
            albumTitleView.text = title
            squareImageView.imageSource = imageUrl
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
            OrganismThumbnailAlbumCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

}