package com.myxlultimate.component.organism.quotaDetailWidget.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.organism.quotaDetailWidget.QuotaBreakdownQuotaDetailWidget

class RecycleViewAdapter(private val itemsListener: OnItemsClickListener) :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    interface OnItemsClickListener {
        fun onHeaderLayoutPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
        fun onStopButtonPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
        fun onChangePlanPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
        fun onBuyAgainPlanPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
        fun onBonusCardPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
        fun onFailedButtonPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
        fun onTrashIconClick(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
        fun onSolidButtonPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
        fun onTransferButtonPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int)
    }

    var items = listOf<QuotaBreakdownQuotaDetailWidget.Data>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    class ViewHolder(
        val view: QuotaBreakdownQuotaDetailWidget,
        private val itemsListener: OnItemsClickListener?
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
            view.apply {
                items = data.items
                topLeftTitle = data.topLeftTitle
                subtitle = data.subtitle
                bottomStatusDescription = data.bottomStatusDescription
                bottomNoteDescription = data.bottomNoteDescription
                profileName = data.profileName
                profileId = data.profileId
                profileAvatar = data.profileAvatar
                profileMode = data.profileMode
                quotaDetailWidgetMode = data.quotaDetailWidgetMode
                name = data.name
                iconImage = data.iconImage
                isDateFormatExpiredAt = data.isDateFormatExpiredAt
                expiredAt = data.expiredAt
                remainingContractMonthStr = data.remainingContractMonthStr
                remainingContractMonth = data.remainingContractMonth
                hasBonus = data.hasBonus
                bonusTitle = data.bonusTitle
                isRecurring = data.isRecurring
                quotaDetailType = data.quotaDetailType
                primaryButtonRedStyleText = data.primaryButtonRedStyleText
                primaryButtonSecondaryStyleText = data.primaryButtonSecondaryStyleText
                secondaryButtonText = data.secondaryButtonText
                statusFlagActiveText = data.statusFlagActiveText
                statusFlagInactiveText = data.statusFlagInactiveText
                validityLabelDefault = data.validityLabelDefault
                validityLabelRecurring = data.validityLabelRecurring
                validityDateText = data.validityDateText
                iconViewValiditySource = data.iconViewValiditySource
                isPrimaryButtonRedStyleVisible = data.isPrimaryButtonRedStyleVisible
                isPrimaryButtonRedStyleEnabled = data.isPrimaryButtonRedStyleEnabled
                isPrimaryButtonSecondaryStyleVisible = data.isPrimaryButtonSecondaryStyleVisible
                isPrimaryButtonSecondaryStyleEnabled = data.isPrimaryButtonSecondaryStyleEnabled
                isSecondaryButtonVisible = data.isSecondaryButtonVisible
                isHeaderIconRightVisible = data.isHeaderIconRightVisible
                isStatusFlagVisible = data.isStatusFlagVisible
                isHeaderVisible = data.isHeaderVisible
                isQuotaActionLayoutVisible = data.isQuotaActionLayoutVisible
                failTitle = data.failTitle ?:""
                failedButtonTitle = data.failedButtonTitle ?:""
                slotFee = data.slotFee ?:""
                slotFeeLabel = data.slotFeeLabel ?: ""
                slotFeeIcon = data.slotFeeIcon ?: ""
                disableDetailItem = data.disableDetailItem ?: false
                isValidateDateVisible = data.isValidateDateVisible ?: false
                disableCard = data.disableCard ?: false
                defaultValidityUnlimitedDateText = data.defaultValidityUnlimitedDateText
                isTrashIconVisible = data.isTrashIconVisible?:false
                isSolidButtonVisible = data.isSolidButtonVisible?: false
                isSolidButtonEnabled = data.isSolidButtonEnabled
                solidButtonText = data.solidButtonText
                bottomNoteDescriptionError = data.bottomNoteDescriptionError
                showTransferQuotaBeard = data.showTransferBottomBeard
                transferQuotaButtonTitle = data.transferQuotaBottomButtonTitle
                transferQuotaInformationTitle = data.transferQuotaBottomInformationTitle
                onFailedButtonClicked = {
                    itemsListener?.onFailedButtonPress(data,index)
                }
                onHeaderLayoutPress = {
                    itemsListener?.onHeaderLayoutPress(data, index)
                }
                onStopButtonPress = {
                    itemsListener?.onStopButtonPress(data, index)
                }
                onBonusCardPress = {
                    itemsListener?.onBonusCardPress(data, index)
                }
                onChangePlanPress = {
                    itemsListener?.onChangePlanPress(data, index)
                }
                onBuyAgainPlanPress = {
                    itemsListener?.onBuyAgainPlanPress(data, index)
                }
                onTrashIconClick = {
                    itemsListener?.onTrashIconClick(data,index)
                }
                onSolidButtonPress = {
                    itemsListener?.onSolidButtonPress(data, index)
                }
                onTransferQuotaButtonPress = {
                    itemsListener?.onTransferButtonPress(data,index)
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
        val holder = ViewHolder(
            QuotaBreakdownQuotaDetailWidget(
                parent.context
            ),
            itemsListener
        )
        holder.view.layoutParams = ViewGroup.LayoutParams(
            parent.layoutParams.width,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return holder
    }


}