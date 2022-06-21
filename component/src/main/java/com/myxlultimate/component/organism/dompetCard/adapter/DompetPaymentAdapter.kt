package com.myxlultimate.component.organism.dompetCard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.dompetCard.DompetPaymentWidget
import com.myxlultimate.component.organism.dompetCard.enums.WidgetStyle
import com.myxlultimate.component.token.imageView.ImageSourceType

class DompetPaymentAdapter(private val itemListener: OnItemClickListener) :
    RecyclerView.Adapter<DompetPaymentAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onPress(data: DompetPaymentWidget.Data, index: Int)
    }

    var items = listOf<DompetPaymentWidget.Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DompetPaymentWidget(
                parent.context
            ),
            itemListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position, items.size)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        val view: DompetPaymentWidget,
        private val itemListener: OnItemClickListener?
    ): RecyclerView.ViewHolder(view){
        fun bind(data: DompetPaymentWidget.Data, position: Int, size: Int) {
            view.apply {
                widgetStyle = data.widgetStyle?: WidgetStyle.FULL
                title = data.title?: ""
                subTitle = data.subTitle?: ""
                imageSourceType = data.imageSourceType?: ImageSourceType.DRAWABLE
                imageSource = data.imageSource
                imageSourceTypeIcon = data.imageSourceTypeIcon?: ImageSourceType.DRAWABLE
                imageSourceIcon = data.imageSourceIcon
                ribbonLabel = data.ribbonLabel?:""
                ribbonLabelNew = data.ribbonLabelNew?:""
                buttonLabelPrimary = data.buttonLabelPrimary?:""
                buttonLabelSecondary = data.buttonLabelSecondary?:""
                isErrorSubTitle = data.isErrorSubTitle?:false
                warningTitle = data.warningTitle?:""
                onPress = {
                    itemListener?.onPress(data, position)
                }
            }
        }

    }
}