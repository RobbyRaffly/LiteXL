package com.myxlultimate.component.organism.roamingInformation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.roamingInformation.RoamingInformationItemRow
import com.myxlultimate.component.token.imageView.ImageSourceType

class RoamingInformationItemListAdapter: RecyclerView.Adapter<RoamingInformationItemListAdapter.ViewHolder>(){

    var items = listOf<RoamingInformationItemRow.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: RoamingInformationItemRow
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: RoamingInformationItemRow.Data, index: Int) {
            view.apply {
                title = data.title
                imageSourceType = data.imageSourceType?:ImageSourceType.BASE64
                imageSource = data.imageSource
            }

        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RoamingInformationItemRow(
                parent.context
            )
        )
    }


}