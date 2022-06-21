package com.myxlultimate.component.organism.bizOnItem.bizOnActivation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_item_cashback_history_card.view.*

class CashbackActivationItemCard  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val iconItem: String,
        val label: String,
        val amount :String,
        val isShowArrow: Boolean,
        val isShowButtonLine: Boolean? = false
    )

    private val defaultIconItem = "iVBORw0KGgoAAAANSUhEUgAAAGAAAABgCAMAAADVRocKAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAByUExURUdwTPDw8PHx8fHx8fDw8PDw8P////Dw8PHx8ff39/Dw8PHx8eLi4vDw8PLy8u/v7/Ly8u/v7xMTE1paWiMjIxsbG+Pj44KCgj09Pb29vTExMUdHR9zc3JycnHV1dczMzNTU1GJiYoqKirCwsKioqGhoaM/Js8wAAAARdFJOUwDFNkjp+APUXhKpjuqdT4JMoT8nRAAAA4VJREFUaN69Wue2gjAMRmV7vFqlLFGG+P6veLWlCk0YgjE/PFhKM76MLsOYRN7a3fsrxzQtyzSdlb93157xLfIOW4cJslq/jDnbwxeYeO6KDdDKXcZj41tshCx/M3d023XYJHJcm3L4mSzWHwwvWKw/g3bHPqbdB3D/mWwGmX9Trb9lM2k7CQlvxWbTaoKZ1iZbQOYo1huLLSJrJOzcheM/OLiD47MvkEtnnzErrYfGz+qojNOA8yCNy6jOhjjgSNtev/8k1/ioUXxN+n0J9Va7z//DKj2iFFdhXzxgEdcTv2EUHHspiHpYbJH8g/e8DAwvWFzw70BewgEozkBkoNC5mAQDmp+T92DyKcgZy4NWg3hC0d5pHoqah6shrrn0otuz+SYRzq+KB0fN1PFVG6tfV/X9A8lEPJXyRSn+JA/8lQRXrMbZIyni1Hx8Kl5/GmsXsv359Oo0nDIwBRr5eS3CuK2AUkEEc817dWipgChwkd+leevfC0xpMGn5vInCy5AKUIFECnbOWuZK36/Tllky6coc+pLzSqLQ/6WDxE2chlyzgrAfV2+lhwUwHlRa9cEbKVSqUmbdtZCyUa0SrbTSGQzjN0Fs4QDwoiNw0O4RdFQqOA6D5eEQh4HW/ww88dQVWUoUhDjMIE1HumsLASPQhetBE4G0LSyEK8DfNSt/pwlFMl3k73rHcRWeNjrojZUujcS483HYQfmldaWPdcAKTaoLUwGMG5QrXe0UKzx6lCXAnHfECQXudwBcAmPNw5NQoQ9WdnuVOtMCT0kerAQiLmNgswjxtHT4O1kV9CjIIFpYFEnPh76RgUjYay215oDKY25Yt7Zn5bpjCdqDRKSHkLJugrlCJ79xLNZ8EMclMGUOdMIbY8QXHrHsYBjfobAZBlVHrTuGsmOYWJjFpxbJ3H3SSGbodkuMhJplAgYjM7kxCvQJmKEXA76MAddLAj0DchPRgvxgQOumlkMfaOSpYmqyq+cmuyXpmk9J1+QFh7xkkhd9+mkL+cSLfOpIPvmln76PL0AifAESTVyAkC+h6BeB5MtY+oU4+VYC+WYI/XYO+YYU/ZYa/aYg+bYm/cbsjK3l9LOtZYN8c9wm3t7/wQEF/REL/SER/TEX/UGdYS85anSmnMjaiw5LJxyX2uTHvT84sKY/cv/BpYEn2LTXHn5wceMXV0+0yzOaZb5yeWbx9Z9/2M735IXeODoAAAAASUVORK5CYII="

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    var iconItem = defaultIconItem
        set(value) {
            field = value
            if (value.isNotEmpty()) iconView.imageSource = value
        }

    var label = ""
        set(value) {
            field = value
            labelView.text = value
        }

    var amount = ""
        set(value) {
            field = value
            amountView.text = value
        }

    var isShowArrow = false
        set(value) {
            field = value
            refreshView()
        }

    var isShowButtonLine = false
        set(value) {
            field = value
            bottomLineView.visibility = if (value) View.VISIBLE else View.GONE
        }

    var onItemPress: (() -> Unit)? = null
        set(value) {
            field = value
            refreshView()
        }

    private fun refreshView() {
        iconRightArrow.visibility = if (isShowArrow) View.VISIBLE else View.GONE
        if (isShowArrow) {
            TouchFeedbackUtil.attach(cardView, onItemPress)
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_item_cashback_history_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CashbackActivationItemCard)
        typedArray.run {
            title = getString(R.styleable.CashbackActivationItemCard_title)?:""
            iconItem = getString(R.styleable.CashbackActivationItemCard_iconItem )?:defaultIconItem
            label = getString(R.styleable.CashbackActivationItemCard_label )?:""
            amount = getString(R.styleable.CashbackActivationItemCard_amount )?:""
            isShowArrow = getBoolean(R.styleable.CashbackActivationItemCard_isShowArrow, false)
        }
        typedArray.recycle()

    }
}