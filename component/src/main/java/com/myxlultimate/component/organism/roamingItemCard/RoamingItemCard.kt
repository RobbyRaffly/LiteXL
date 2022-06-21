package com.myxlultimate.component.organism.roamingItemCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.roamingItemCard.adapter.RoamingItemAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_roaming_item_card.view.*


class RoamingItemCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    var items:List<RoamingItem.Data> = listOf()
        set(value) {
            field = value
            itemAdapter.items = items

        }

    // ----------------------------------------------------------------------------------

    var itemAdapter:RoamingItemAdapter = RoamingItemAdapter()

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_roaming_item_card, this, true)

        roamingItemRecyclerView.adapter = itemAdapter

        roamingItemRecyclerView.addItemDecoration(ListUtil.getListGapDecorator(context, 16))

    }

}