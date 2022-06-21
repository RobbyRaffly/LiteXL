package com.myxlultimate.component.organism.roamingItemCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_roaming_item_row.view.*
import kotlinx.android.synthetic.main.organism_roaming_item_row.view.titleView

class RoamingItemRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    data class Data(
        val title: String,
        val priceString: String,
        val imageSource:String
    )

    // ----------------------------------------------------------------------------------

    var title:String = ""
        set(value) {
            field = value

            titleView.text = title
        }

    // ----------------------------------------------------------------------------------

    var priceString:String = ""
        set(value) {
            field = value

            priceView.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource:String = ""
        set(value) {
            field = value

            imageView.imageSource = value
        }
    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_roaming_item_row, this, true)
        context.theme.obtainStyledAttributes(attrs, R.styleable.roamingItemAttr, 0, 0).apply {
            try{
                getString(R.styleable.roamingItemAttr_title)?.let {
                    title = it
                }
                getString(R.styleable.roamingItemAttr_priceString)?.let {
                    priceString = it
                }
                getString(R.styleable.roamingItemAttr_imageSource)?.let {
                    imageSource = it
                }
            }finally {
                recycle()
            }
        }

    }

}