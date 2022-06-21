package com.myxlultimate.component.organism.quotaDetailWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_quota_detail_widget_xl_pass_access.view.*

class XlPassAccessQuotaDetailWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            containerView.onActionButtonPress = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_quota_detail_widget_xl_pass_access, this, true)
    }
}