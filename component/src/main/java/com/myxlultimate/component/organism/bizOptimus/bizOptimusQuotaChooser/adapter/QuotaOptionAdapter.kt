package com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser.QuotaOptionInformation
import com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser.QuotaOptionItem
import com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser.adapter.QuotaItemType.Header
import com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser.adapter.QuotaItemType.Item
import com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser.adapter.QuotaOptionViewObject.QuotaOptionHeaderItemViewObject
import com.myxlultimate.component.organism.bizOptimus.bizOptimusQuotaChooser.adapter.QuotaOptionViewObject.QuotaOptionItemViewObject

class QuotaOptionAdapter(
    private val onItemSelected: (QuotaOptionItemViewObject) -> Unit
) : ListAdapter<QuotaOptionViewObject, ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            Header.code -> QuotaOptionHeaderViewHolder(QuotaOptionInformation(parent.context))
            Item.code -> QuotaOptionItemViewHolder(QuotaOptionItem(parent.context), onItemSelected)
            else -> throw RuntimeException("View Not Handled yet")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is QuotaOptionItemViewHolder -> holder.onBind(getItem(position) as QuotaOptionItemViewObject)
            is QuotaOptionHeaderViewHolder -> holder.onBind(getItem(position) as QuotaOptionHeaderItemViewObject)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is QuotaOptionHeaderItemViewObject -> Header
            is QuotaOptionItemViewObject -> Item
        }.code
    }

    object DiffCallback : ItemCallback<QuotaOptionViewObject>() {
        override fun areItemsTheSame(
            oldItem: QuotaOptionViewObject,
            newItem: QuotaOptionViewObject
        ): Boolean {
            return oldItem is QuotaOptionItemViewObject && newItem is QuotaOptionItemViewObject &&
                    oldItem.quotaValue == newItem.quotaValue && oldItem.isActive == newItem.isActive

        }

        override fun areContentsTheSame(
            oldItem: QuotaOptionViewObject,
            newItem: QuotaOptionViewObject
        ): Boolean {
            return oldItem == newItem
        }
    }
}

class QuotaOptionItemViewHolder(
    private val view: QuotaOptionItem,
    private val onItemSelected: (QuotaOptionItemViewObject) -> Unit
) : ViewHolder(view) {
    fun onBind(item: QuotaOptionItemViewObject) {
        view.quotaOption = "${item.quotaValue}GB"
        view.isViewSelected = item.isActive

        view.onSelectedChange = { isSelected ->
            if (isSelected) onItemSelected(item)
        }
    }
}

class QuotaOptionHeaderViewHolder(val view: QuotaOptionInformation) : ViewHolder(view) {
    fun onBind(item: QuotaOptionHeaderItemViewObject) {
        view.remainingPerson = item.remainingPerson
        view.remainingQuota = item.remainingQuota
    }
}

enum class QuotaItemType(val code: Int) {
    Header(1),
    Item(2)
}

sealed class QuotaOptionViewObject {
    data class QuotaOptionHeaderItemViewObject(val remainingQuota: Int, val remainingPerson: Int) :
        QuotaOptionViewObject()

    data class QuotaOptionItemViewObject(val quotaValue: Int, val isActive: Boolean) :
        QuotaOptionViewObject()
}
