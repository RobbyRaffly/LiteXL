package com.myxlultimate.component.organism.akrabCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.akrabCard.AkrabGroupExitCard

class AkrabGroupExitAdapter(val onCardPressed: (AkrabGroupExitCard.Data, Int) -> Unit) :
    ListAdapter<AkrabGroupExitCard.Data, AkrabGroupExitAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(AkrabGroupExitCard(parent.context))
        holder.view.layoutParams = ViewGroup.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(val view: AkrabGroupExitCard) : RecyclerView.ViewHolder(view) {
        fun bind(data: AkrabGroupExitCard.Data, index: Int) {
            with(view) {
                title = data.title ?: ""
                topSubtitle = data.topSubtitle ?: ""
                bottomSubtitle = data.bottomSubtitle ?: ""
                buttonText = data.buttonText ?: ""
                onCardPress = {
                    onCardPressed.invoke(data, index)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<AkrabGroupExitCard.Data>() {
                override fun areItemsTheSame(
                    oldItem: AkrabGroupExitCard.Data,
                    newItem: AkrabGroupExitCard.Data
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: AkrabGroupExitCard.Data,
                    newItem: AkrabGroupExitCard.Data
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}