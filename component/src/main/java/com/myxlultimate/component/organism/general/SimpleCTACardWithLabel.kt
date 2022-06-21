package com.myxlultimate.component.organism.general

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismSimpleCtaCardWithLabelBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class SimpleCTACardWithLabel @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var binding: OrganismSimpleCtaCardWithLabelBinding? = null

    var ctaLabel: String = ""
        set(value) {
            field = value
            binding?.ctaLabel?.text = value
        }

    var ctaValue: String = ""
        set(value) {
            field = value
            binding?.ctaValue?.text = value
        }
    var ctaLabelColor: Int = ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey)
        set(value) {
            field = value
            binding?.ctaLabel?.setTextColor(value)
        }

    var ctaValueColor: Int = ContextCompat.getColor(context, R.color.colorPrimary)
        set(value) {
            field = value
            binding?.ctaValue?.setTextColor(value)
        }

    var onCtaClicked: () -> Unit = {}

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismSimpleCtaCardWithLabelBinding.inflate(LayoutInflater.from(context), this, true)

        binding?.ctaValue?.apply {
            TouchFeedbackUtil.attach(this, onCtaClicked)
        }
    }
}
