package com.myxlultimate.component.organism.bizOnTable

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.bizOnTable.adapter.BizonItemTableAdapter
import kotlinx.android.synthetic.main.organism_bizon_table_info.view.*

class BizOnTableListGroup@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var columnOne = resources.getString(R.string.xl_bizon_title_container_column_one)
        set(value) {
            field = value
            setRefreshView()
        }

    var columnTwo = resources.getString(R.string.xl_bizon_title_owner)
        set(value) {
            field = value
            setRefreshView()
        }

    var columnThree = resources.getString(R.string.xl_bizon_title_member)
        set(value) {
            field = value
            setRefreshView()
        }

    var twoTable = true
        set(value) {
            field = value
            setRefreshView()
        }

    var items = mutableListOf<BizonTableListItem.Data>()
        set(value) {
            field = value
            bizonItemCashbackAdapter.items = value
        }

    var onItemPress: ((Int) -> Unit)? = null

    private val bizonItemCashbackAdapter by lazy {
        BizonItemTableAdapter()
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_bizon_table_info, this, true)

        rv_table_item.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            //addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = bizonItemCashbackAdapter.also {
                it.items = items
            }
        }
    }

    private fun setRefreshView() {
        txt_title_column_one.text = columnOne
        txt_title_column_two.text = columnTwo
        txt_title_column_three.text = columnThree
        if (twoTable){
            divider_two_column.visibility = View.GONE
            container_two_column.visibility = View.GONE
        } else {
            divider_two_column.visibility = View.VISIBLE
            container_two_column.visibility = View.VISIBLE
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is BizonTableListItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                BizonTableListItem.Data(
                    child.columnOne,
                    child.columnTwo,
                    child.columnThree
                )
            )
            bizonItemCashbackAdapter.items = items
        }
    }
}