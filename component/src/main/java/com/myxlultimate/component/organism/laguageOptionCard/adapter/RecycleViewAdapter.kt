package com.myxlultimate.component.organism.laguageOptionCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.molecule.optionItemCard.LanguageOptionItemCard

class RecycleViewAdapter(private val onActiveItemChange: (Int) -> Unit): RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

    var total = 0
    var items = listOf<LanguageOptionItemCard.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: LanguageOptionItemCard,
        val onActiveIndexChange: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: LanguageOptionItemCard.Data, index: Int) {
            view.apply {
                title = data.title
                imageSource = data.imageSource
                deactivatable = data.isActive
                isActive = data.isActive
                onChange = {
                    if (it) {
                        onActiveIndexChange(index)
                    }
                }
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
            LanguageOptionItemCard(
                parent.context
            ),onActiveItemChange
        )
    }
}