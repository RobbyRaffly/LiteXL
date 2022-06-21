package com.myxlultimate.component.molecule.quotaSummary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.molecule_quota_summary_group.view.*

class QuotaSummaryGroup @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var itemCounter = 0

    // ----------------------------------------------------------------------------------

    var lineQuantity = 2

    // ----------------------------------------------------------------------------------

    private var children = mutableListOf<QuotaSummaryItem>()

    // ----------------------------------------------------------------------------------

    var isDisabled = false
        set(value) {
            field = value

            children.forEach {
                it.isDisabled = value
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_quota_summary_group, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuotaSummaryGroupAttr)
        typedArray.run {
            isDisabled = getBoolean(R.styleable.QuotaSummaryGroupAttr_isDisabled, false)
            lineQuantity = getInt(R.styleable.QuotaSummaryGroupAttr_lineQuantity, 2)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (mainView == null) {
            super.addView(child, index, params)
        } else {
            if (child != null && child is QuotaSummaryItem) {
                child.isDisabled = isDisabled

                val layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT,
                    1.0F
                )

                children.add(child)
                mainView.addView(child, index, layoutParams)

                if (itemCounter < lineQuantity) {
                    val view = View(context)
                    val layoutParams = LayoutParams(
                        4,
                        LayoutParams.MATCH_PARENT
                    )
                    view.setBackgroundColor(getColor(context, R.color.mud_palette_basic_light_grey))
                    mainView.addView(view, layoutParams)
                    itemCounter++
                }
            }
        }
    }
}