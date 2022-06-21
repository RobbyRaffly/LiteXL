package com.myxlultimate.component.organism.promoVoucherCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_promo_voucher_card.view.*

class PromoVoucherCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var label = resources.getString(R.string.organism_promo_voucher_card_label)
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var buttonTitle = resources.getString(R.string.organism_promo_voucher_card_copy)
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var code = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: ((String) -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(tvAction) { value?.invoke(code) }
        }

    // ----------------------------------------------------------------------------------

    var hasBottomView: Boolean = false
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var bottomLabel = resources.getString(R.string.organism_promo_voucher_card_label)
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var bottomButtonTitle = resources.getString(R.string.organism_promo_voucher_card_copy)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var bottomCode = ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var onBottomActionButtonPress: ((String) -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(tvBottomAction) { value?.invoke(bottomCode) }
        }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        tvCode.text = code
        tvLabel.text = label
        tvAction.text = buttonTitle
        tvBottomCode.text = bottomCode
        tvBottomLabel.text = bottomLabel
        tvBottomAction.text = bottomButtonTitle
        bottomView.visibility = if (hasBottomView) View.VISIBLE else View.GONE
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_promo_voucher_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PromoVoucherCard)
        typedArray.run {
            code = getString(R.styleable.PromoVoucherCard_code) ?: ""
            label = getString(R.styleable.PromoVoucherCard_label)
                ?: resources.getString(R.string.organism_promo_voucher_card_label)
            buttonTitle = getString(R.styleable.PromoVoucherCard_buttonTitle)
                ?: resources.getString(R.string.organism_promo_voucher_card_copy)
            bottomCode = getString(R.styleable.PromoVoucherCard_bottomCode) ?: ""
            bottomLabel = getString(R.styleable.PromoVoucherCard_bottomLabel)
                ?: resources.getString(R.string.organism_promo_voucher_card_bottom_label)
            bottomButtonTitle = getString(R.styleable.PromoVoucherCard_bottomButtonTitle)
                ?: resources.getString(R.string.organism_promo_voucher_card_copy)
            hasBottomView = getBoolean(R.styleable.PromoVoucherCard_hasBottomView, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(tvAction) { onActionButtonPress?.invoke(code) }
        TouchFeedbackUtil.attach(tvBottomAction) { onBottomActionButtonPress?.invoke(code) }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


}