package com.myxlultimate.component.organism.loyaltyTableCard.adapter

import android.os.Build
import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.loyaltyTableCard.LoyaltyTableItem

class LoyaltyTableItemAdapter(): RecyclerView.Adapter<LoyaltyTableItemAdapter.ViewHolder>() {

    class ViewHolder(
        val view: LoyaltyTableItem
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: LoyaltyTableItem.Data, index: Int){
            view.apply {
                this.title = data.title
                this.information = data.information
                this.iconCode = data.iconCode
                this.startHexColor = data.startHexColor
                this.endHexColor = data.endHexColor
            }
        }
    }

    var items = listOf<LoyaltyTableItem.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder =  ViewHolder(
            LoyaltyTableItem(parent.context)
        )
        val width: Int = parent.measuredWidth / itemCount
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.view.foregroundGravity = Gravity.CENTER
        }
        val marginLayoutParams =
            ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams(
                    width,
                    RecyclerView.LayoutParams.MATCH_PARENT
                )
            )
        holder.view.layoutParams = marginLayoutParams
        return holder
    }

}