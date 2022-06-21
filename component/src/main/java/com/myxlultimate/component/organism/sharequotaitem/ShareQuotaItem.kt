package com.myxlultimate.component.organism.sharequotaitem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.databinding.OrganismShareQuotaItemBinding
import com.myxlultimate.component.enums.QuotaType

class ShareQuotaItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var binding: OrganismShareQuotaItemBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var iconCode = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var amount : Long = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var amountString = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var quotaType = QuotaType.DATA
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var outlineTextValue = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var onPressed : (() -> Unit)? = null
    set(value) {
        field = value
        binding?.outlinedTextfieldView?.onPress = {
            onPressed?.invoke()
        }
    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.apply {
            packageBenefitItem.name = name
            packageBenefitItem.iconImage = iconCode
            packageBenefitItem.amount = amount
            packageBenefitItem.amountString = amountString
            packageBenefitItem.quotaType = quotaType
            outlinedTextfieldView.text = outlineTextValue
        }
    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismShareQuotaItemBinding.inflate(LayoutInflater.from(context), this, true)
    }


}