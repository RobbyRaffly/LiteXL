package com.myxlultimate.component.organism.funCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.databinding.OrganismSectionListWithHeaderBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class SectionListWithHeader @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private var binding: OrganismSectionListWithHeaderBinding? = null

    var useSubtitle: Boolean = true
        set(value) {
            field = value
            binding?.subtitleHeader?.isVisible = value
        }

    var useButton: Boolean = false
        set(value) {
            field = value
            binding?.buttonView?.visibility = if(value) View.VISIBLE else View.INVISIBLE
        }

    var titleText: String = ""
        set(value) {
            field = value
            binding?.titleHeader?.text = value
        }

    var subtitleText: String = ""
        set(value) {
            field = value
            binding?.subtitleHeader?.text = value
        }

    var buttonText: String = ""
        set(value) {
            field = value
            binding?.buttonView?.text = value
        }

    var leftTopIcon: String = ""
        set(value) {
            field = value
            binding?.leftTopIconView?.imageSource = value
            binding?.leftTopIconView?.visibility = if(value.isNotEmpty()) View.VISIBLE else View.GONE
        }

    var onButtonClicked: () -> Unit = {}
        set(value) {
            field = value
            binding?.apply {
                TouchFeedbackUtil.attach(buttonView, onButtonClicked)
            }
        }

    val contents: RecyclerView? by lazy { binding?.contents }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismSectionListWithHeaderBinding.inflate(LayoutInflater.from(context), this, true)

        binding?.apply {
            TouchFeedbackUtil.attach(buttonView, onButtonClicked)
        }
    }
}
