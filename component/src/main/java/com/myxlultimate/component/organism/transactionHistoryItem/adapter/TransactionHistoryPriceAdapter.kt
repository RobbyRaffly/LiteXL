package com.myxlultimate.component.organism.transactionHistoryItem.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.packageBenefit.PackageBenefitItem
import com.myxlultimate.component.token.imageView.ImageSourceType

class TransactionHistoryPriceAdapter : RecyclerView.Adapter<TransactionHistoryPriceAdapter.ViewHolder>() {

	var items = listOf<PackageBenefitItem.Data>()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	// ----------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------

	class ViewHolder(
        val view: PackageBenefitItem,
        val itemCount: Int
    ) : RecyclerView.ViewHolder(view) {
		fun bind(data: PackageBenefitItem.Data, index: Int) {
			view.apply {
				name = data.name
				information = data.information
				amountString = data.amountString
				hasGapBottom = data.hasGapBottom
				hasBorderBottom = data.hasBorderBottom
				hasGapTop = data.hasGapTop
				iconImageSourceType = data.iconImageSourceType
				iconImage = data.iconImage
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
            PackageBenefitItem(
                parent.context
            ),
            items.size
        )
	}


}