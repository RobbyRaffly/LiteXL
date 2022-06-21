package com.myxlultimate.component.organism.noticeCard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_flat_notice_card.view.*
import kotlinx.android.synthetic.main.organism_package_card_option.view.*
import kotlinx.android.synthetic.main.organism_status_payment_card.view.*

class FlatNoticeCard@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var setNoticeBackgroundColor : Int? = null
        set(value) {
            field = value
            value?.let {
                NoticeCardView.backgroundTintList = ColorStateList.valueOf(it)
            }
        }

    // ----------------------------------------------------------------------------------

    var setTitleColor : Int? = null
        set(value) {
            field = value
            value?.let {
                NoticeCardTitleView.setTextColor(it)
            }
        }

    // ----------------------------------------------------------------------------------

    var setTitleAppearance : Int? = null
        set(value) {
            field = value
            value?.let{
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    NoticeCardTitleView.setTextAppearance(context, it)
                } else {
                    NoticeCardTitleView.setTextAppearance(it)
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var setExclamationMarkColor : Int? = null
        set(value) {
            field  = value
            value?.let {
                NoticeCardLeftIconView.setTextColor(it)
            }
        }

    // ----------------------------------------------------------------------------------

    var setExclamationMarkBackgroundColor : Int? = null
        set(value) {
            field = value
            value?.let {
                NoticeCardLeftIconView.backgroundTintList = ColorStateList.valueOf(it)
            }
        }

    // ----------------------------------------------------------------------------------

    var title = ""
    set(value) {
        field = value
        NoticeCardTitleView.text = value
    }

    // ----------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------
    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_flat_notice_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.noticeCardAttr)
        typedArray.run {
            title = getString(R.styleable.noticeCardAttr_title)?:""
        }
        typedArray.recycle()
    }



}