package com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_biz_optimus_quota_chooser.view.*

class SetQuotaCardItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    var label: String = context.getString(R.string.biz_optimus_label_set_quota)
        set(value) {
            field = value
            labelChooser.text = value
        }

    var quotaValue: Int = 0
        set(value) {
            field = value
            quotaValueView.setText(value.toString())
        }

    var onQuotaChooserClicked: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(quotaValueView, value)
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_optimus_quota_chooser, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuotaChooserItem)
        typedArray.run {
            label = getString(R.styleable.QuotaChooserItem_label) ?: ""
            quotaValue = getInt(R.styleable.QuotaChooserItem_quotaValue, 0)
        }

        typedArray.recycle()
    }
}
