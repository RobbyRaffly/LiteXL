package com.myxlultimate.component.organism.familyPlanBenefitInputCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.familyPlanBenefitInputCard.FamilyPlanBenefitInputCardItem

class BenefitInputCardItemAdapter: RecyclerView.Adapter<BenefitInputCardItemAdapter.ViewHolder>() {

    var items = listOf<FamilyPlanBenefitInputCardItem.Data>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onItemChange:((Int, Long)->Unit)? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var familyId:String = ""
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun setExpanded(isExpanded:Boolean){
        items.forEach { it.isExpanded = isExpanded }
        notifyDataSetChanged()
    }

    class ViewHolder(val view: FamilyPlanBenefitInputCardItem): RecyclerView.ViewHolder(view) {

        fun bind(familyId:String, index:Int, data: FamilyPlanBenefitInputCardItem.Data, onItemChange:((Int, Long)->Unit)?=null){
            view.applyData( familyId, index, data){ it ->
                data.value = it
                onItemChange?.invoke(index, it)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FamilyPlanBenefitInputCardItem(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(familyId, position, items[position], onItemChange)
    }

    override fun getItemCount() = items.size
}