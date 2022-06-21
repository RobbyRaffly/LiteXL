package com.myxlultimate.component.organism.topUpHistoryItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_top_up_history_item.view.*
import kotlinx.android.synthetic.main.organism_top_up_history_item.view.lineView
import kotlinx.android.synthetic.main.organism_top_up_history_item.view.priceView
import java.text.SimpleDateFormat

class TopUpHistoryItem @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
            val priceString: String,
            val date: Long = 0,
            val detail: String? = "",
            val hasLine: Boolean? = true,
            val hasReload: Boolean? = false
    )

    // ----------------------------------------------------------------------------------

    var priceString: String = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var detail = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var date: Long = 0
        set(value) {
            field = value

            val format = SimpleDateFormat("d MMMM yyyy")
            dateView.text = format.format(value*1000L)
        }

    // ----------------------------------------------------------------------------------

    var hasLine = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasReload = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onReloadPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(reloadView, value)
        }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        if (priceString.isNotEmpty()) {
            if(detail.isNotEmpty())
                priceView.text = priceString + " - $detail"
            else
                priceView.text = priceString
        }
        lineView.visibility = if (!hasLine) View.GONE else View.VISIBLE
        reloadView.visibility = if (hasReload) View.VISIBLE else View.GONE

        if (detail.isNotEmpty()) {
            priceView.text = priceString + " - $detail"
        }else{
            priceView.text = priceString
        }
    }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.organism_top_up_history_item, this, true)

        val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.TopUpHistoryItem)
        typedArray.run {
            priceString = getString(R.styleable.TopUpHistoryItem_priceString)?:""
            hasLine = getBoolean(R.styleable.TopUpHistoryItem_hasLine, true)
            hasReload = getBoolean(R.styleable.TopUpHistoryItem_hasReload, false)
            detail = getString(R.styleable.TopUpHistoryItem_detail)?:""
            date = getInt(R.styleable.TopUpHistoryItem_date,0).toLong()
        }
        typedArray.recycle()
    }
}