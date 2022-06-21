package com.myxlultimate.component.organism.menuPrioFlexCard.adapter
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.menuPrioFlexCard.MenuPrioFlexCardItem

class MenuPrioFlexCardAdapter(val onItemPress: (Int) -> Unit): RecyclerView.Adapter<MenuPrioFlexCardAdapter.ViewHolder>() {

    class ViewHolder(
        val view: MenuPrioFlexCardItem,
        val onItemPress: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: MenuPrioFlexCardItem.Data, index: Int){
            view.apply {
                this.label = data.label
                this.iconImage = data.iconImage
                this.imageSourceType = data.imageSourceType
                this.onPress = {onItemPress(index)}
            }
        }
    }

    var items = listOf<MenuPrioFlexCardItem.Data>()
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
            MenuPrioFlexCardItem(parent.context),
            onItemPress
        )
//        val width: Int = parent.measuredWidth / 3
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            holder.view.foregroundGravity = Gravity.CENTER
//        }
//        val marginLayoutParams =
//            ViewGroup.MarginLayoutParams(
//                ViewGroup.LayoutParams(
//                    width,
//                    RecyclerView.LayoutParams.MATCH_PARENT
//                )
//            )
//        holder.view.layoutParams = marginLayoutParams

        return holder
    }

}