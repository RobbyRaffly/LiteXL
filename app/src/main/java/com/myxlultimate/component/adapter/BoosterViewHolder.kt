package com.myxlultimate.component.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_booster.view.*

class BoosterViewHolder (view: View): RecyclerView.ViewHolder(view) {

    val title = view.titleBooster
    val desc = view.descBooster
    val image = view.imgBooster

    fun bindData(adapter: BoosterAdapter, position:Int){
        title.setText(adapter.data.get(position)?.BoosterTitle.toString())
        desc.setText(adapter.data.get(position)?.descBooster.toString())
        image.setImageResource(adapter.data.get(position)?.imgBooster)

//        navigasi.setOnClickListener{
//            val intent = Intent(adapter.parent.context, DetailResepActivity::class.java)
//            intent.putExtra("data",adapter.data.get(position))
//            adapter.parent.context.startActivity(intent)
//
//        }


    }
}