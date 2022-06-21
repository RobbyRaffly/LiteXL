package com.myxlultimate.component.organism.dashboardWidget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_loyalty_bonus_coupon_card.view.*
import kotlinx.android.synthetic.main.organism_loyalty_pending_card.view.*
import kotlinx.android.synthetic.main.organism_loyalty_pending_card.view.titleView
import kotlinx.android.synthetic.main.organism_spend_limit_category_card.view.*
import kotlinx.android.synthetic.main.organism_tab_menu_item.view.*
import kotlinx.android.synthetic.main.organism_volte_ready_card.view.*
import kotlinx.android.synthetic.main.organism_welcome_header_toolbar.view.*
import kotlinx.android.synthetic.main.template_card_widget.view.*

class VoLTEReadyCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = resources.getString(R.string.xl_volte_ready)
        set(value) {
            field = value
            refreshView()
        }

    var icon = R.string.xl_bars
        set(value) {
            field = value
            refreshView()
        }

    var rightIcon = R.drawable.ic_arrow_right_prepaid
        set(value) {
            field = value
            refreshView()
        }

    var iconBackgroundColor = R.color.primaryBlue
        set(value) {
            field = value
            refreshView()
        }

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(volteCard) { value?.invoke() }
        }

    private fun refreshView() {
        tvTitle.text = title
        iconBar.text = resources.getString(icon)
        iconBar.backgroundTintList = ContextCompat.getColorStateList(context, iconBackgroundColor)
        iconChevron.imageSourceType = ImageSourceType.DRAWABLE
        iconChevron.imageSource = ContextCompat.getDrawable(context, rightIcon)
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_volte_ready_card, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoLTEReadyCard)
        typedArray.run {
            title = getString(R.styleable.VoLTEReadyCard_title) ?: resources.getString(R.string.xl_volte_ready)
            icon = getResourceId(R.styleable.VoLTEReadyCard_icon, R.string.xl_bars)
            rightIcon = getResourceId(R.styleable.VoLTEReadyCard_rightIcon, R.drawable.ic_arrow_right_prepaid)
            iconBackgroundColor = getResourceId(R.styleable.VoLTEReadyCard_iconBackgroundColor, R.color.primaryBlue)
        }
        typedArray.recycle()
    }
}