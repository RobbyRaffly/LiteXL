package com.myxlultimate.component.organism.faq

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.databinding.OrganismGuestFloatingCardItemBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class GuestFloatingCardItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private var binding: OrganismGuestFloatingCardItemBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var icon = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var showRightArrow = true
        set(value) {
            field = value
            refreshView()
        }
    // --------
    // --------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.cardView?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.apply {
            titleView.text = title
            iconView.imageSource = icon
            arrowRightView.visibility = if(showRightArrow) View.VISIBLE else View.GONE
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
            OrganismGuestFloatingCardItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

}