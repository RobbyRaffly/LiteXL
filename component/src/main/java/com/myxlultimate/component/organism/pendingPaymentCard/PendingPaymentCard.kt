package com.myxlultimate.component.organism.pendingPaymentCard

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_pending_payment_card.view.*
import kotlinx.android.synthetic.main.organism_pending_payment_card.view.iconView
import kotlinx.android.synthetic.main.organism_status_payment_card.view.*
import kotlinx.android.synthetic.main.organism_transaction_history_card.view.*
import kotlinx.android.synthetic.main.organism_transaction_history_card.view.containerView
import java.text.SimpleDateFormat

class PendingPaymentCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var hasArrow: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    var imageSourceType = ImageSourceType.ASSET
        set(value) {
            field = value
            iconView.imageSourceType = value
        }


    var iconImage: Any? = null
        set(value) {
            field = value
            iconView.imageSource = value
            IsEmptyUtil.setVisibility(value, iconView)
            if (iconImage != null) {
                iconCodeView.visibility = View.GONE
            }
        }

    var iconCode = resources.getString(R.string.xl_cash)
        set(value) {
            field = value
            refreshView()
        }

    var topLabel: String = ""
        set(value) {
            field = value
            refreshView()
        }


    var bottomlabel: String = ""
        set(value) {
            field = value
            refreshView()
        }

    var onCopyPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(copyView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        if (topLabel.isNotEmpty()) {
            topLabelView.text = topLabel
        }
        if (bottomlabel.isNotEmpty()) {
            bottomLabelView.visibility = View.VISIBLE
            bottomLabelView.text = bottomlabel
        } else {
            bottomLabelView.visibility = View.GONE
        }
        iconCodeView.text = iconCode
        rightArrowView.visibility = if(hasArrow) View.VISIBLE else View.GONE
        copyView.visibility = if(hasArrow) View.GONE else View.VISIBLE
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_pending_payment_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PendingPaymentCardAttr)
        typedArray.run {
            topLabel = getString(R.styleable.PendingPaymentCardAttr_topLabel) ?: ""
            bottomlabel = getString(R.styleable.PendingPaymentCardAttr_bottomLabel) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(
                R.styleable.PendingPaymentCardAttr_imageSourceType,
                0
            )]
            if (imageSourceType == ImageSourceType.DRAWABLE) {
                iconImage = getDrawable(R.styleable.PendingPaymentCardAttr_imageSource)
            } else {
                iconImage = getString(R.styleable.PendingPaymentCardAttr_imageSource)
            }
            iconCode = getString(R.styleable.PendingPaymentCardAttr_iconCode)?:resources.getString(R.string.xl_cash)
            hasArrow = getBoolean(R.styleable.PendingPaymentCardAttr_hasArrow, false)

        }

//        if (hasNextButton) TouchFeedbackUtil.attach(containerView, onCardPress)
    }
}