package com.myxlultimate.component.organism.hybridModeWidgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.databinding.OrganismSwitcherWithBodyBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class SwitcherWithBodyCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var binding: OrganismSwitcherWithBodyBinding? = null

    var headerTitle: String = ""
        set(value) {
            field = value
            binding?.titleHeader?.text = value
        }

    var topLabel: String = ""
        set(value) {
            field = value
            binding?.topLabelText?.text = value
        }

    var bottomLabel: String = ""
        set(value) {
            field = value
            binding?.bottomLabelText?.text = value
        }

    var topValue: String = ""
        set(value) {
            field = value
            binding?.topValueText?.text = value
        }

    var bottomValue: String = ""
        set(value) {
            field = value
            binding?.bottomValueText?.text = value
        }

    var isSwitchActive: Boolean = false
        set(value) {
            field = value
            binding?.switchView?.isChecked = isSwitchActive
        }

    var onSwicth: (Boolean) -> Unit = {}

    private fun toggle() {
        isSwitchActive = !isSwitchActive
        onSwicth(isSwitchActive)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding = OrganismSwitcherWithBodyBinding.inflate(LayoutInflater.from(context), this, true)

        binding?.apply {
            TouchFeedbackUtil.attach(switchView, ::toggle)
        }
    }
}
