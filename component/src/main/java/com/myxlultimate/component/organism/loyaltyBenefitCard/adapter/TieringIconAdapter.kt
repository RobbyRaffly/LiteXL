package com.myxlultimate.component.organism.loyaltyBenefitCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.token.imageView.ImageView
import com.myxlultimate.component.util.ConverterUtil

class TieringIconAdapter() : RecyclerView.Adapter<TieringIconAdapter.ViewHolder>() {

    var items = listOf<ImageView>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            ImageView(
                parent.context
            )
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            ConverterUtil.dpToPx(parent.context, 24f).toInt(),
            ConverterUtil.dpToPx(parent.context, 24f).toInt()
        )
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: ImageView
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: ImageView, index: Int) {
            view.apply {
                imageSourceType = data.imageSourceType
                imageSource = data.imageSource
            }
        }
    }

}