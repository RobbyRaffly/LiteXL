package com.myxlultimate.component.organism.familyMemberCardItem.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.familyMemberCardItem.FamilyMemberCardItem

class RecycleViewAdapter(
    private val onActiveItemChange: ((Int) -> Unit)? = null,
    private val onItemPress: ((Int) -> Unit)? = null
): RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

    var total = 0
    var items = listOf<FamilyMemberCardItem.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: FamilyMemberCardItem,
        private val onActiveItemChange: ((Int) -> Unit)? = null,
        private val onItemPress: ((Int) -> Unit)? = null
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: FamilyMemberCardItem.Data, index: Int) {
            view.apply {
                cardMode = data.cardMode
                cardListenerMode = data.cardListenerMode
                isDisabled = data.isDisabled
                imageScaleType = data.imageScaleType
                profileName = data.profileName
                profileId = data.profileId
                customValidity = data.customValidity
                profileMode = data.profileMode
                imageSourceType = data.imageSourceType
                profileAvatar = data.profileAvatar
                addMemberButtonText = data.addMemberButtonText
                hasCloseButton = data.hasCloseButton
                endButtonViewColor = data.endButtonViewColor
                radioDeactivatable = data.radioDeactivatable
                isRadioActive = data.isRadioActive
                memberStateColor = data.memberStateColor
                memberStatus = data.memberStatus
                data.addMemberTitle?.also {
                    addMemberTitle = it
                }
                validityMemberText = data.validityMemberText
                onChangePress = {
                    if (it) {
                        onActiveItemChange?.invoke(index)
                    }
                }
                onPress = {
                    onItemPress?.invoke(index)
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
        return ViewHolder(
            FamilyMemberCardItem(
                parent.context
            ),
            onActiveItemChange,
            onItemPress
        )
    }
}