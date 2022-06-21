package com.myxlultimate.component.organism.bizOnTable

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_bizon_level_indicator_card.view.*
import kotlinx.android.synthetic.main.organism_table_three_column.view.*

class BizonTableListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    twoColumn : Boolean? = false
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val columnOne : String?="",
        val columnTwo : Long?=0,
        val columnThree : Long?=0
    )

    var columnOne = ""
        set(value) {
            field = value
            txt_column_one.text = value
        }

    // ----------------------------------------------------------------------------------

    var columnTwo : Long = 0
        set(value) {
            field = value
            txt_column_two.text = context.getString(
                R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(columnTwo, true)
            )
        }

    // ----------------------------------------------------------------------------------

    var columnThree : Long = 0
        set(value) {
            field = value
            if (value <= 0.toLong()){
                txt_column_three.visibility = View.GONE
                divider_column_three.visibility = View.GONE
            } else {
                txt_column_three.visibility = View.VISIBLE
                divider_column_three.visibility = View.VISIBLE
                txt_column_three.text = context.getString(
                    R.string.indonesian_rupiah_balance_remaining,
                    ConverterUtil.convertDelimitedNumber(columnThree, true)
                )
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_table_three_column, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BizonCashbackListItem)
        typedArray.run {
            columnOne = getString(R.styleable.BizonCashbackListItem_titleOne)?:""
//            columnTwo = getLong(R.styleable.BizonCashbackListItem_titleTwo)?:0
//            columnThree = getString(R.styleable.BizonCashbackListItem_titleThree)?:0
        }
        typedArray.recycle()

    }

    fun hideBottomDivider(){
        divider_bottom.visibility = View.GONE
    }
}