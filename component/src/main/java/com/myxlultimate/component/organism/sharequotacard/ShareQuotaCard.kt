package com.myxlultimate.component.organism.sharequotacard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.databinding.OrganismShareQuotaCardBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class ShareQuotaCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    private var binding: OrganismShareQuotaCardBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var topTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var bottomTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var topRightTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var bottomRightTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.cardView?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.apply {
            internetQuotaItemView.apply {
                name = topTitle
                amountString = topRightTitle
            }
            accumulatedQuotaItemView.apply {
                name = bottomTitle
                amountString = bottomRightTitle
            }
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
            OrganismShareQuotaCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

}