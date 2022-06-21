package com.myxlultimate.component.organism.tableView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_column_item.view.*


class ColumnItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val title: String,
        val backgroundColor: Int? = R.color.mud_palette_basic_white,
        val isHeader: Boolean? = false
    )
    // ----------------------------------------------------------------------------------

    var backgroundColumnColor = R.color.mud_palette_basic_white
        set(value) {
            field = value
            columnView.setBackgroundColor(getColor(context, value))
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            headerTitleView.text = value
            bodyTitleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var isHeader = false
        set(value) {
            field = value
            headerTitleView.visibility = if (value) View.VISIBLE else View.GONE
            bodyTitleView.visibility = if (value) View.GONE else View.VISIBLE
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_column_item, this, true)

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ColumnItem)
        typedArray.run {
            title = getString(R.styleable.ColumnItem_title) ?: ""
            isHeader = getBoolean(R.styleable.ColumnItem_isHeader, false)
            backgroundColumnColor = getResourceId(R.styleable.ColumnItem_backgroundColor, 0)
        }
        typedArray.recycle()
    }
}