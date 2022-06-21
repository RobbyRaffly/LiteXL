package com.myxlultimate.component.organism.bonusItemCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_bonus_item_card.view.*
import kotlinx.android.synthetic.main.organism_bonus_item_card.view.containerView
import kotlinx.android.synthetic.main.organism_bonus_item_card.view.nameView
import kotlinx.android.synthetic.main.organism_bonus_item_card.view.nextButtonView
import java.text.SimpleDateFormat
import java.util.*

class BonusItemCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val name: String,
        val information: String,
        val image: Long,
        val isShimmerOn: Boolean = false
    )

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value

            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value

            informationView.text = value
        }


    // ----------------------------------------------------------------------------------

    var dateTime = 0L
        set(value) {
            field = value

            if (value == 0L){
                informationView.visibility = View.GONE
            } else {
                informationView.visibility = View.VISIBLE

                val format = SimpleDateFormat("d MMMM yyyy ", Locale.getDefault())
                informationView.apply {
                    text = context.getString(
                        R.string.organism_bonus_item_card_validity,
                        format.format(value)
                    )

                }
            }
        }

    // ----------------------------------------------------------------------------------

    var hasNextButton = true
        set(value) {
            field = value

            nextButtonView.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var hasTextNextButton = true
        set(value) {
            field = value

            nextButtonText.visibility = if (value) View.VISIBLE else View.GONE
        }

    // ----------------------------------------------------------------------------------

    var image =
        "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"
        set(value) {
            field = value
            iconView.apply {
                imageSource = value
                IsEmptyUtil.setVisibility(value, this)
            }
        }

    // ----------------------------------------------------------------------------------

    var onNextPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(nextButtonView, value)
        }

    // ----------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

    var isShimmerOn = false
        set(value) {
            field = value
            shimmerLayout.apply {
                if (value) {
                    startShimmer()
                    visibility = View.VISIBLE
                } else {
                    stopShimmer()
                    visibility = View.GONE
                }
            }
            originalView.visibility = if(value) View.GONE else View.VISIBLE
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_bonus_item_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BonusItemCard)
        typedArray.run {
            image = getString(R.styleable.BonusItemCard_iconImage) ?: ""
            name = getString(R.styleable.BonusItemCard_name) ?: ""
            dateTime = getInt(R.styleable.BonusItemCard_dateTime, 0).toLong()
            hasNextButton = getBoolean(R.styleable.BonusItemCard_hasNextButton, true)
            isShimmerOn = getBoolean(R.styleable.BonusItemCard_isShimmerOn, false)
            hasTextNextButton =
                getBoolean(R.styleable.BonusItemCard_hasTextNextButton, false)
        }
        typedArray.recycle()
    }
}