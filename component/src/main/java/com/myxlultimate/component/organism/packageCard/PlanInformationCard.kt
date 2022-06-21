package com.myxlultimate.component.organism.packageCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismPlanInformationCardBinding
import com.myxlultimate.component.token.imageView.ImageSourceType

class PlanInformationCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var binding: OrganismPlanInformationCardBinding? = null

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    var planTitle = ""
        set(value) {
            field = value
            binding?.titlePlanView?.text = value
        }

    var planInformation = ""
        set(value) {
            field = value
            binding?.subtitlePlanView?.text = value
        }

    var ctaBottomText = ""
        set(value) {
            field = value

            binding?.ctaBottomView?.text = value
        }

    var ctaTextColor = ContextCompat.getColor(context, R.color.colorPrimary)
        set(value) {
            field = value
            binding?.ctaBottomView?.setTextColor(value)
        }

    var planInfoTopRightText = ""
        set(value) {
            field = value
            binding?.pricePlanView?.text = value
        }

    var planInfoTopRightColor = ContextCompat.getColor(context, R.color.colorPrimary)
        set(value) {
            field = value
            binding?.pricePlanView?.setTextColor(value)
        }

    var planIconType = ImageSourceType.BASE64
        set(value) {
            field = value
            binding?.iconPlanView?.imageSourceType = value
        }

    var planIcon: Any? = null
        set(value) {
            field = value
            binding?.iconPlanView?.imageSource = value
        }

    var useExtensionView = false
        set(value) {
            field = value
            binding?.extensionView?.isVisible = value
        }

    var showWarning = false
        set(value) {
            field = value
            binding?.apply {
                warningView.isVisible = value
                ctaBottomView.isVisible = !value
            }
        }

    var warningText = ""
        set(value) {
            field = value
            binding?.warningInformationView?.text = value
        }

    var extensionText = ""
        set(value) {
            field = value
            binding?.extensionInformationView?.text = extensionText
        }

    var extensionButtonText = ""
        set(value) {
            field = value
            binding?.extensionButtonView?.text = extensionButtonText
        }

    var onExtensionButtonClicked = {}
    var onCtaBottomClicked = {}

    init {
        binding =
            OrganismPlanInformationCardBinding.inflate(LayoutInflater.from(context), this, true)

        binding?.ctaBottomView?.setOnClickListener {
            onCtaBottomClicked()
        }
    }
}
