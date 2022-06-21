package com.myxlultimate.component.organism.bizOptimus.bizOptimusAddMemberCardItem

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

class BizOptimusAddMemberCardItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val msisdn:String,
        val quota:Int,
        val actionLabel:String
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var msisdn = ""
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

    var actionLabel = ""
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(tvAction, value)
        }

    // ----------------------------------------------------------------------------------

    var onDeleteButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(btnDelete, value)
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_optimus_add_member_card_item, this, true)

        TouchFeedbackUtil.attach(tvAction, onActionButtonPress)
        TouchFeedbackUtil.attach(btnDelete, onDeleteButtonPress)

    }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        profileSelectorView.name = msisdn
        tvMainQuota.text = "$quota GB"
        tvAction.text = actionLabel
    }

    // ----------------------------------------------------------------------------------

    fun setData(data: Data){
        msisdn = data.msisdn
        quota = data.quota
        actionLabel = data.actionLabel
    }

    // ----------------------------------------------------------------------------------

}