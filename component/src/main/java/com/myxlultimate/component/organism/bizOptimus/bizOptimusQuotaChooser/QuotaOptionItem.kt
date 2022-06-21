package com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_quota_option_item_card.view.*

class QuotaOptionItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var isViewSelected: Boolean = false
        set(isSelected) {
            field = isSelected

            IsEmptyUtil.setVisibility(isSelected, selectedView)
        }

    var quotaOption: String = ""
        set(value) {
            field = value

            tvQuotaOption.text = value
            IsEmptyUtil.setVisibility(value, tvQuotaOption)
        }

    var isReversibleSelection: Boolean = false

    var onSelectedChange: (Boolean) -> Unit = {}

    private fun toggleSelection() {
        if (isViewSelected && !isReversibleSelection) return

        isViewSelected = !isViewSelected
        onSelectedChange(isViewSelected)
    }

    init {
        LayoutInflater.from(context).inflate(
            R.layout.molecule_quota_option_item_card,
            this,
            true
        )

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuotaOptionItem)
        quotaOption = typedArray.getString(R.styleable.QuotaOptionItem_quotaOptionValue) ?: ""
        isReversibleSelection =
            typedArray.getBoolean(R.styleable.QuotaOptionItem_isReversibleSelection, false)

        typedArray.recycle()

        TouchFeedbackUtil.attach(this) { toggleSelection() }
    }
}
