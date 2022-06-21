package com.myxlultimate.component.organism.promoLabel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_promo_label_view.view.*

class PromoLabelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = "Promo"
        set(value) {
            field = value
            labelTextView.text = value
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_promo_label_view, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TapeLabelView)
        typedArray.run {
            title = getString(R.styleable.TapeLabelView_title) ?: "Promo"
        }
        typedArray.recycle()
    }
}