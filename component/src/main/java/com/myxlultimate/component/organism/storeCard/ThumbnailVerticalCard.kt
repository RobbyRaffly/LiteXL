package com.myxlultimate.component.organism.storeCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.myxlultimate.component.databinding.OrganismThumbnailVerticalCardBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class ThumbnailVerticalCard@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private var binding: OrganismThumbnailVerticalCardBinding? = null

    var imageUrl = ""
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


    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.smallVerticalView?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.apply {
            circleImageView.imageSource = imageUrl
            smallVerticalTextView.text = title
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismThumbnailVerticalCardBinding.inflate(LayoutInflater.from(context), this, true)
    }


}