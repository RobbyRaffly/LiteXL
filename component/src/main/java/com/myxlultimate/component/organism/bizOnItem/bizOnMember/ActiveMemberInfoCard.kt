package com.myxlultimate.component.organism.bizOnItem.bizOnMember

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_biz_on_active_member_info_card.view.*

class ActiveMemberInfoCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var icon = ContextCompat.getDrawable(context, R.drawable.ic_biz_on_icon_member)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var value = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_biz_on_active_member_info_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActiveMemberInfoCard)
        typedArray.run {
            title = getString(R.styleable.ActiveMemberInfoCard_title) ?: ""
            icon = getDrawable(R.styleable.ActiveMemberInfoCard_imageDrawable)
            value = getString(R.styleable.ActiveMemberInfoCard_value) ?: ""
        }
        typedArray.recycle()

    }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        tvTitle.text = title
        ivIcon.setImageDrawable(icon)
        tvValue.text = value
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}