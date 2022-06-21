package com.myxlultimate.component.organism.akrabCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.akrabCard.AkrabSelectItemCard

class RecycleViewAdapter(
    private val onActiveItemChange: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    var items = listOf<AkrabSelectItemCard.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: AkrabSelectItemCard,
        private val onActiveItemChange: ((Int) -> Unit)? = null
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: AkrabSelectItemCard.Data, index: Int) {
            view.apply {
                this.title = data.title ?: ""
                this.subtitle = data.subtitle ?: ""
                this.akrabSelectItemMode = data.cardMode
                this.isRadioActive = data.isRadioActive ?: false
                onCardPress = {
                    isRadioActive = !isRadioActive
                }
                onChangePress = {
                    if (it) {
                        onActiveItemChange?.invoke(index)
                    } else {
                        onActiveItemChange?.invoke(-1)
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

        val holder = ViewHolder(
            AkrabSelectItemCard(
                parent.context
            ),
            onActiveItemChange
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        return holder
    }
}