package com.myxlultimate.component.organism.benefitInfoItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_benefit_info_item.view.*

class BenefitInfoItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val imageTitle: String = "",
        val title: String = "",
        val subtitleLeft: String = "",
        val subtitleRight: String = "",
        val imageSubtitle: String = "",
        val description: String = "",
        val isFUP: Boolean = false
    )

    // ----------------------------------------------------------------------------------

    var imageTitle = ""
        set(value) {
            field = value
            ivTitle.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            tvTitle.text = value
        }

    // ----------------------------------------------------------------------------------

    var subtitleLeft = ""
        set(value) {
            field = value
            tvSubtitleLeft.text = value
        }

    // ----------------------------------------------------------------------------------

    var subtitleRight = ""
        set(value) {
            field = value
            tvSubtitleRight.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageSubtitle = ""
        set(value) {
            field = value
            ivSubtitle.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            tvDescription.text = value
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_benefit_info_item, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BenefitInfoItem)
        typedArray.run {
            imageTitle = getString(R.styleable.BenefitInfoItem_imageTitle) ?: ""
            title = getString(R.styleable.BenefitInfoItem_title) ?: ""
            subtitleLeft = getString(R.styleable.BenefitInfoItem_subtitleLeft) ?: ""
            subtitleRight = getString(R.styleable.BenefitInfoItem_subtitleRight) ?: ""
            imageSubtitle = getString(R.styleable.BenefitInfoItem_imageSubtitle) ?: ""
            description = getString(R.styleable.BenefitInfoItem_description) ?: ""
        }
        typedArray.recycle()

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}