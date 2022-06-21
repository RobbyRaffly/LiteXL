package com.myxlultimate.component.organism.tableView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.tableView.adapter.TableViewRecyclerView
import kotlinx.android.synthetic.main.organism_table_view.view.*

class TableView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<ColumnRow.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        TableViewRecyclerView()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_table_view, this, true)

        rvTableView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is ColumnRow) {
            super.addView(child, index, params)
        } else {
            items.add(
                ColumnRow.Data(
                    child.firstColumn,
                    child.secondColumn,
                    child.thirdColumn
                )
            )
            recycleAdapter.items = items
        }
    }

}