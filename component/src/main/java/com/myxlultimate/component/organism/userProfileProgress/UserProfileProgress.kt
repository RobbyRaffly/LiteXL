package com.myxlultimate.component.organism.userProfileProgress

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_user_profile_progress.view.*

class UserProfileProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var color = R.color.mud_palette_prepaid_turquoise
        set(value) {
            field = value
            progressView.backgroundTintList = ContextCompat.getColorStateList(context, value)
        }

    // ----------------------------------------------------------------------------------

    var progressIcon = context.getString(R.string.xl_profile)
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

    var poin = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var progress = 0F
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onClosePress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onButtonPress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.text = title
        if (title.isEmpty())
            titleView.text =
                resources.getString(R.string.organism_user_profile_progress_title, 200)

        if (poin > 0)
            titleView.text =
                resources.getString(R.string.organism_user_profile_progress_title, poin)

        progressIconView.text = progressIcon

        percentageView.text = ("${progress.toInt()}%")

        var layoutParams = progressView.layoutParams as LayoutParams
        layoutParams.weight = progress
        progressView.layoutParams = layoutParams

        val spacePercentage = 100F - progress
        layoutParams = progressSpacerView.layoutParams as LayoutParams
        layoutParams.weight = spacePercentage
        progressSpacerView.layoutParams = layoutParams
        progressSpacerView.backgroundTintList =
            ContextCompat.getColorStateList(context, R.color.mud_palette_basic_medium_grey)

        if (progress < 20F) {
            progressView.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.mud_palette_basic_red)
        } else {
            progressView.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.mud_palette_prepaid_turquoise)
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_user_profile_progress, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.UserProfileProgressAttr)
        typedArray.run {
            title = getString(R.styleable.UserProfileProgressAttr_title) ?: ""
            poin = getInt(R.styleable.UserProfileProgressAttr_point, 0)
            progress = getInt(R.styleable.UserProfileProgressAttr_progress, 0).toFloat()
            color = getResourceId(
                R.styleable.UserProfileProgressAttr_colorProgress,
                R.color.mud_palette_prepaid_turquoise
            )
            progressIcon = getString(R.styleable.UserProfileProgressAttr_progressIcon)
                ?: context.getString(R.string.xl_profile)
        }
        typedArray.recycle()

        iconView.setOnClickListener {
            onClosePress?.let { it() }
        }
        buttonView.setOnClickListener {
            onButtonPress?.let { it() }
        }

    }
}