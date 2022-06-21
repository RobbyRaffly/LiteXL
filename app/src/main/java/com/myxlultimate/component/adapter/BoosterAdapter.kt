package com.myxlultimate.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.R
import com.myxlultimate.component.model.BoosterModel

class BoosterAdapter (val data: ArrayList<BoosterModel>): RecyclerView.Adapter<BoosterViewHolder>() {

    lateinit var parent: ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoosterViewHolder {
        this.parent = parent

        return BoosterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_booster,parent,false))
    }

    override fun onBindViewHolder(holder: BoosterViewHolder, position: Int) {
        holder.bindData(this@BoosterAdapter,position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}