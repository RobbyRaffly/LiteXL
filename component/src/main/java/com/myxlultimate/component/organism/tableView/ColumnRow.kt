package com.myxlultimate.component.organism.tableView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_column_row.view.*

class ColumnRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val first: String,
        val second: String,
        val third: String
    )
    // ----------------------------------------------------------------------------------

    var backgroundColumnColor = R.color.mud_palette_basic_white
        set(value) {
            field = value
            columnViewFirst.backgroundColumnColor = value
            columnViewSecond.backgroundColumnColor = value
            columnViewThird.backgroundColumnColor = value
        }

    // ----------------------------------------------------------------------------------

    var firstColumn = ""
        set(value) {
            field = value
            columnViewFirst.title = value
        }

    // ----------------------------------------------------------------------------------

    var secondColumn = ""
        set(value) {
            field = value
            columnViewSecond.title = value
        }

    // ----------------------------------------------------------------------------------

    var thirdColumn = ""
        set(value) {
            field = value
            columnViewThird.title = value
        }
    // ----------------------------------------------------------------------------------

    var isHeader = false
        set(value) {
            field = value
            columnViewFirst.isHeader = value
            columnViewSecond.isHeader = value
            columnViewThird.isHeader = value
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_column_row, this, true)

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ColumnRow)
        typedArray.run {
            firstColumn = getString(R.styleable.ColumnRow_firstColumnLabel)?:""
            secondColumn = getString(R.styleable.ColumnRow_secondColumnLabel)?:""
            thirdColumn = getString(R.styleable.ColumnRow_thirdColumnLabel)?:""
            isHeader = getBoolean(R.styleable.ColumnRow_isHeader, false)
            backgroundColumnColor = getResourceId(R.styleable.ColumnRow_backgroundColor, R.color.mud_palette_basic_white)
        }
        typedArray.recycle()
    }
}