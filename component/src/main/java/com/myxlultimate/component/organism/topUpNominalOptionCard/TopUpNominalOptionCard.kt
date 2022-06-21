package com.myxlultimate.component.organism.topUpNominalOptionCard

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_top_up_nominal_option_card.view.*

class TopUpNominalOptionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var subTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var bottomFlag = ""
        set(value) {
            field = value

            bottomFlagView.apply {
                text = value
                visibility = if(value.isNotEmpty()) View.VISIBLE else View.GONE
            }

            // set padding 18 if bottom flag exist
            val paddingDp = 18
            val gapFloat = (paddingDp / 1.2).toFloat()
            val gap =
                (gapFloat * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
            priceView.setPadding(0, 0, 0, if (value.isNotEmpty()) gap else 0)
        }

    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            field = value

            borderContainerView.setBackgroundResource(if (isActive) R.drawable.top_up_nominal_option_background_updated else 0)
            checkView.visibility = if (isActive) View.VISIBLE else View.GONE

            onChange?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var isShimmerOn: Boolean = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.apply {
                    startShimmer()
                    visibility = View.VISIBLE
                }
                originalView.visibility = View.GONE
            } else {
                shimmerLayout.apply {
                    stopShimmer()
                    visibility = View.GONE
                }
                originalView.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        nameView.text = title

        priceView.apply {
            text = subTitle
            visibility = if(subTitle.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_top_up_nominal_option_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TopUpNominalOptionCardAttr)
        typedArray.run {
            title = typedArray.getString(R.styleable.TopUpNominalOptionCardAttr_title)?:""
            subTitle = typedArray.getString(R.styleable.TopUpNominalOptionCardAttr_subtitle)?:""
            bottomFlag = typedArray.getString(R.styleable.TopUpNominalOptionCardAttr_bottomFlag)?:""
            isActive = getBoolean(R.styleable.TopUpNominalOptionCardAttr_isActive, false)
            isShimmerOn = getBoolean(R.styleable.TopUpNominalOptionCardAttr_isShimmerOn, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView) {
            if (!isActive) {
                isActive = true
            }
        }
    }
}