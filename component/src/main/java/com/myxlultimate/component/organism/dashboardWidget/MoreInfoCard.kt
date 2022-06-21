package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_volte_info_card.view.*

class MoreInfoCard@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var description = resources.getString(R.string.volte_see_more_info)
        get() = tvDescription.text.toString()
        set(value) {
            field = value
            tvDescription.text = value
        }

    // ----------------------------------------------------------------------------------

    var buttonLabel: String = resources.getString(R.string.volte_see_here)
        get() = tvSeeMore.text.toString()
        set(value) {
            field = value
            tvSeeMore.text = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

    var image = R.drawable.ic_confirmation_prepaid_new
        set(value) {
            field = value
            ivConfirmation.setImageDrawable(ContextCompat.getDrawable(context, image))
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_volte_info_card, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoreInfoCard)
        typedArray.run {
            description = getString(R.styleable.MoreInfoCard_title) ?: resources.getString(R.string.volte_see_more_info)
            buttonLabel = getString(R.styleable.MoreInfoCard_subtitle) ?: resources.getString(R.string.volte_see_here)
            image = getResourceId(R.styleable.MoreInfoCard_iconImage, R.drawable.ic_confirmation_prepaid_new)
        }
        typedArray.recycle()
    }
}