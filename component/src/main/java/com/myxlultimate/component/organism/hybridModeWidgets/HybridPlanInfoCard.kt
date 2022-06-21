package com.myxlultimate.component.organism.hybridModeWidgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isGone
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.toGone
import com.myxlultimate.component.util.toVisible
import kotlinx.android.synthetic.main.organism_hybrid_plan_info.view.*

class HybridPlanInfoCard(
    context: Context,
    attr: AttributeSet? = null
): LinearLayout(context, attr) {

    var topImageSource : Any = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var topImageSourceType : ImageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var bottomImageSource : Any = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var bottomImageSourceType : ImageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var topTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var topTitleDescription = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var bottomTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var bottomInformation = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var bottomExpiredText = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var warningInformationText = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var extensionInformationText = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var extensionButtonText = ""
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------

    var extensionButtonOnPressed : (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    // -------------------------------------------------------------------------------------------



    // -------------------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_hybrid_plan_info, this, true)
    }

    private fun refreshView(){
        topImageView.apply {
            imageSource = topImageSource
            imageSourceType = topImageSourceType
        }
        bottomImageView.apply {
            imageSource = bottomImageSource
            imageSourceType = bottomImageSourceType
        }
        extensionButtonView.apply {
            text = extensionButtonText
            setOnClickListener {
                extensionButtonOnPressed?.invoke()
            }
        }

        topTitleView.text = topTitle
        topTitleDescriptionView.text = topTitleDescription
        bottomTitleView.text = bottomTitle
        bottomInformationView.text = bottomInformation
        bottomExpiredView.text = bottomExpiredText
        warningInformationView.text = warningInformationText
        extensionInformationView.text = extensionInformationText

        topTitleDescriptionView.isGone = topTitleDescription.isEmpty()

        if (warningInformationText.isEmpty()) {
            warningInformationView.toGone()
            warningIconView.toGone()
        } else {
            warningInformationView.toVisible()
            warningIconView.toVisible()
        }
    }
}