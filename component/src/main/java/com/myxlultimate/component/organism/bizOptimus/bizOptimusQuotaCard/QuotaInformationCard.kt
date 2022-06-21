package com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_information_card.view.*

class QuotaInformationCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    var label: String = context.getString(R.string.biz_optimus_remaining_quota_group)
        set(value) {
            field = value

            tvTitle.text = value
        }

    var quotaValue: String = "0GB"
        set(value) {
            field = value

            tvSubtitleRight.text = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_information_card, this, true)
        label = context.getString(R.string.biz_optimus_remaining_quota_group)
    }
}
