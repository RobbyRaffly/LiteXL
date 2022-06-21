package com.myxlultimate.component.molecule.unsubscribePackageReason.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.molecule.unsubscribePackageReason.UnsubscribePackageReasonItem

class RecycleViewAdapter(
    private val onActiveItemChange: (Int) -> Unit
) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    var items = listOf<UnsubscribePackageReasonItem.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: UnsubscribePackageReasonItem,
        val onActiveIndexChange: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(data: UnsubscribePackageReasonItem.Data, index: Int) {
            view.apply {
                label = data.label
                iconImage = data.iconImage
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

    // ----------------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UnsubscribePackageReasonItem(
                parent.context
            ),
            onActiveItemChange
        )
    }

    // ----------------------------------------------------------------------------------

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

}