package com.myxlultimate.component.organism.loyaltyCard

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_history_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class LoyaltyHistoryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val dateTime: Long? = 0L,
        val title: String? = "",
        val poin: Int? = 0,
        val expiration: Int? = 0,
        val hasPointIcon: Boolean? = false,
        val isPendingPoint: Boolean? = false
    )

    var dateTime: Long = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
            IsEmptyUtil.setVisibility(value,titleView)
        }

    // ----------------------------------------------------------------------------------

    var poin = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var expiration = 0
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasPointIcon = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isPendingPoint = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(cardView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        val format = SimpleDateFormat("d MMMM yyyy, h.mma", Locale.getDefault())
        dateTimeView.text = format.format(dateTime)
        titleView.text = title
        pointView.text = resources.getString(
            R.string.organism_history_card_point,
            (if (poin > -1) "+" else "") + ConverterUtil.convertDelimitedNumber(
                poin.toLong(),
                true
            )
        )
        pointView.setTextColor(
            ContextCompat.getColor(
                context,
                if (poin > -1) R.color.mud_package_status_green else R.color.mud_palette_basic_red
            )
        )
        expirationView.text =
            resources.getString(R.string.organism_history_card_expiration, expiration)
        expirationView.visibility = if (expiration > 0) View.VISIBLE else View.GONE
        iconView.visibility = if (hasPointIcon) View.VISIBLE else View.GONE
        if (isPendingPoint) {
            titleView.setTextColor(
                ContextCompat.getColor(
                    context, R.color.mud_palette_basic_dark_grey
                )
            )
            pointView.setTextColor(
                ContextCompat.getColor(
                    context, R.color.mud_palette_basic_dark_grey
                )
            )
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_loyalty_history_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoyaltyHistoryCardAttr)
        typedArray.run {
            dateTime = getInt(R.styleable.LoyaltyHistoryCardAttr_dateTime, 0).toLong()
            title = getString(R.styleable.LoyaltyHistoryCardAttr_title) ?: ""
            poin = getInt(R.styleable.LoyaltyHistoryCardAttr_point, 0)
            expiration = getInt(R.styleable.LoyaltyHistoryCardAttr_validity, 0)
            hasPointIcon = getBoolean(R.styleable.LoyaltyHistoryCardAttr_hasPointIcon, false)
            isPendingPoint = getBoolean(R.styleable.LoyaltyHistoryCardAttr_isPendingPoint, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(cardView, onPress)
    }
}