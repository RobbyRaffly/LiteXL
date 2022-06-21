package com.myxlultimate.component.organism.storeWidget.shimmering

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.storeCard.enums.SizeMode
import kotlinx.android.synthetic.main.shimmering_store_card_widget.view.*

class ShimmeringStoreCardWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data (
        val sizeMode : SizeMode?=SizeMode.LARGE,
        val isShimmerOn : Boolean? = false
    )

    var sizeMode = SizeMode.LARGE

    var isShimmerOn = false
    set(value) {
        field = value
        if(value) {
            shimmerLayout.startShimmer()
            storeLargeView.visibility =  if (sizeMode == SizeMode.LARGE) View.VISIBLE else View.GONE
            storeSmallView.visibility =  if (sizeMode == SizeMode.SMALL) View.VISIBLE else View.GONE
            storeSquareView.visibility = if (sizeMode == SizeMode.SQUARE) View.VISIBLE else View.GONE

        } else {
            shimmerLayout.stopShimmer()
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.shimmering_store_card_widget, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShimmeringStoreCardWidget)
        typedArray.run {
           sizeMode = SizeMode.values()[getInt(R.styleable.ShimmeringStoreCardWidget_sizeModeCard,0)]
            isShimmerOn = getBoolean(R.styleable.ShimmeringStoreCardWidget_isShimmerOn,false)
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


}