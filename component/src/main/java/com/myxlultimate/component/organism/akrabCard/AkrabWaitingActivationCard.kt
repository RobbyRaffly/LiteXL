package com.myxlultimate.component.organism.akrabCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.myxlultimate.component.databinding.OrganismWaitingActivationCardBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class AkrabWaitingActivationCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var binding: OrganismWaitingActivationCardBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

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

    var subtitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var showRightArrow = true
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value
            binding?.cardView?.apply {
                TouchFeedbackUtil.attach(this, onCardPress)
            }
        }


    // ------------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.iconView?.imageSource = icon
        binding?.titleView?.text = title
        binding?.subtitleView?.text = subtitle
        binding?.rightIconView?.visibility = if (showRightArrow) View.VISIBLE else View.INVISIBLE
        binding?.iconView?.visibility = if (icon.isNotEmpty()) View.VISIBLE else View.GONE
        binding?.subtitleView?.visibility = if (subtitle.isNotEmpty()) View.VISIBLE else View.GONE
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        binding =
            OrganismWaitingActivationCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

}