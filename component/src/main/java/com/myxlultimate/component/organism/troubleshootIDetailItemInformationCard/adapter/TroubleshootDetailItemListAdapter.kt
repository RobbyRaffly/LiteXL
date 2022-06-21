package com.myxlultimate.component.organism.troubleshootIDetailItemInformationCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.troubleshootIDetailItemInformationCard.TroubleshootIDetailItem

class TroubleshootDetailItemListAdapter(val onItemPress: (Int) -> Unit) : RecyclerView.Adapter<TroubleshootDetailItemListAdapter.ViewHolder>(){

    var items = listOf<TroubleshootIDetailItem.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: TroubleshootIDetailItem,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: TroubleshootIDetailItem.Data, index: Int) {
            view.apply {
                leftTitle = data.leftTitle
                rightTitle = data.rightTitle
                onPress = { onItemPress(index) }
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    override fun getItemCount(): Int = items.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            TroubleshootIDetailItem(
                parent.context
            ),
            onItemPress
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

}