package com.myxlultimate.component.molecule.quotaDetail.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.molecule.quotaDetail.QuotaDetailRowSummaryWidget
import com.myxlultimate.component.token.imageView.ImageSourceType

class QuotaDetailRowSummaryAdapter :
    RecyclerView.Adapter<QuotaDetailRowSummaryAdapter.ViewHolder>() {

    var items = listOf<QuotaDetailRowSummaryWidget.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            QuotaDetailRowSummaryWidget(
                parent.context
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: QuotaDetailRowSummaryWidget
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: QuotaDetailRowSummaryWidget.Data, position: Int) {
            view.apply {
                title = data.title ?: ""
                subTitle = data.subTitle ?: ""
                imageSourceType = data.imageSourceType ?: ImageSourceType.DRAWABLE
                imageSource = data.imageSource
                imageSourceTypeIcon = data.imageSourceTypeIcon ?: ImageSourceType.DRAWABLE
                imageSourceIcon = data.imageSourceIcon
            }
        }

    }
}