package com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_biz_optimus_information_item.view.*

class QuotaOptionInformation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var remainingPerson: Int = 0
        set(value) {
            field = value

            detailTextView.text =
                context.getString(R.string.biz_optimus_detail_information_quota, value)
        }

    var remainingQuota: Int = 0
        set(value) {
            field = value

            quotaView.text = "${value}GB"
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_optimus_information_item, this, true)
    }
}
