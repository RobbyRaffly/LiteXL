package com.myxlultimate.component.organism.bizOptimus.bizOptimusAddMemberDetailQuota

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_biz_optimus_add_member_card_item.view.*
import kotlinx.android.synthetic.main.organism_biz_optimus_add_member_card_item.view.tvMainQuota
import kotlinx.android.synthetic.main.organism_biz_optimus_add_member_detail_quota.view.*

class BizOptimusAddMemberDetailQuota @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var label= ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var quota: Int = 0
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var onSelectQuota: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(tvAction){
                otfQuota.clearFocus()
                value?.invoke()
            }
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_optimus_add_member_detail_quota, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BizOptimusAddMemberCardDetailQuota)
        typedArray.run {
            label = getString(R.styleable.BizOptimusAddMemberCardDetailQuota_label) ?: ""
        }

        typedArray.recycle()
        TouchFeedbackUtil.attach(otfQuota, onSelectQuota)

    }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        tvMainQuota.text =label
        otfQuota.text = ""+quota
    }

    // ----------------------------------------------------------------------------------


}