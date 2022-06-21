package com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.profileSelector.ProfileSelector
import kotlinx.android.synthetic.main.organism_biz_optimus_set_quota_card.view.*

class SetQuotaCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    data class Data(
        val profileId: String,
        val currentQuotaValue: Int
    )

    val profileSelector: ProfileSelector by lazy { findViewById<ProfileSelector>(R.id.profileSelectorView) }

    var profileId = ""
        set(value) {
            field = value

            profileSelector.id = value
        }

    var currentQuotaValue = 0
        set(value) {
            field = value

            bottomQuotaChooser.quotaValue = currentQuotaValue
        }

    var labelChooserValue = ""
        set(value) {
            field = value

            bottomQuotaChooser.label = value
        }

    var onChooserClicked: (() -> Unit)? = null
        set(value) {
            field = value

            bottomQuotaChooser.onQuotaChooserClicked = value
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_optimus_set_quota_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SetQuotaCard)
        typedArray.run {
            profileId = getString(R.styleable.SetQuotaCard_profileId) ?: ""
            currentQuotaValue = getInt(R.styleable.SetQuotaCard_currentQuota, 0)
            labelChooserValue = getString(R.styleable.SetQuotaCard_labelChooser) ?: ""
        }

        typedArray.recycle()
    }
}
