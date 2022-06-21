package com.myxlultimate.component.organism.quotaDetailWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_bonus_card.view.*
import kotlinx.android.synthetic.main.organism_bonus_card.view.iconView
import kotlinx.android.synthetic.main.organism_quota_detail_widget_quota_breakdown.view.*

class BonusCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val information: String = "",
        val hasDetailButton: Boolean
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var hasDetailButton = true
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value,nextButtonView)
            if(!hasDetailButton) {
                TouchFeedbackUtil.detach(bonusView)
            }
        }

    // ----------------------------------------------------------------------------------


    var information = ""
        set(value) {
            field = value

            informationView.text = value
        }

    var imageSource = ""
        set(value) {
            field = value

            iconView.imageSource = value
        }

    var title = ""
        set(value) {
            field = value

            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(bonusView, value)
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_bonus_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BonusCardAttr)
        typedArray.run {
            title = getString(R.styleable.BonusCardAttr_title) ?: ""

            information = getString(R.styleable.BonusCardAttr_information) ?: ""
            hasDetailButton = getBoolean(R.styleable.BonusCardAttr_hasSeeDetail,true)
            imageSource = getString(R.styleable.BonusCardAttr_imageSource) ?: ""
        }
        typedArray.recycle()

        if(hasDetailButton) TouchFeedbackUtil.attach(bonusView, onItemPress)
    }
}