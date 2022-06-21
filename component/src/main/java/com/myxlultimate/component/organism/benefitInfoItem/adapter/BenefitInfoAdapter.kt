package com.myxlultimate.component.organism.benefitInfoItem.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.benefitInfoItem.BenefitInfoItem

class BenefitInfoAdapter : RecyclerView.Adapter<BenefitInfoAdapter.ViewHolder>() {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = listOf<BenefitInfoItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            BenefitInfoItem(parent.context)
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // ----------------------------------------------------------------------------------

    override fun getItemCount(): Int = items.size

    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: BenefitInfoItem
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: BenefitInfoItem.Data) {
            view.apply {
                imageTitle = data.imageTitle
                title = data.title
                subtitleLeft = data.subtitleLeft
                subtitleRight = if (data.isFUP) "FUP ${data.subtitleRight}" else data.subtitleRight
                imageSubtitle = data.imageSubtitle
                description = data.description
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

}