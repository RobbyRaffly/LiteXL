package com.myxlultimate.component.organism.akrabCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismAkrabGroupExitCardBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class AkrabGroupExitCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var binding: OrganismAkrabGroupExitCardBinding? = null

    data class Data(
        var title: String? = "",
        var topSubtitle: String? = "",
        var bottomSubtitle: String? = "",
        var buttonText: String? = ""
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var topSubtitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var bottomSubtitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var buttonText = resources.getString(R.string.akrab_exit_text)
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
        binding?.titleView?.text = title
        binding?.informationTopView?.text = topSubtitle
        binding?.informationBottomView?.text = bottomSubtitle
        binding?.bottomButtonTitleView?.text = buttonText
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
            OrganismAkrabGroupExitCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

}