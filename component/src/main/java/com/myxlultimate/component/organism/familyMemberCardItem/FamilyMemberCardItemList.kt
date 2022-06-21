package com.myxlultimate.component.organism.familyMemberCardItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.familyMemberCardItem.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_language_option_card.view.*

class FamilyMemberCardItemList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<FamilyMemberCardItem.Data>()
        set(value) {
            field = value
            updateItems(value)
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        RecycleViewAdapter(
            onActiveItemChange = {
                activeIndex = it
            },
            onItemPress = {
                onItemPress?.invoke(it)
            }
        )
    }

    // ----------------------------------------------------------------------------------

    var activeIndex = -1
        set(value) {
            if (!itemContainerView.isComputingLayout) {
                field = value
                items.forEachIndexed { index, data ->
                    items[index].isRadioActive = index == value
                }
                updateItems(items)
                onActiveItemChange?.let { it(value) }
            }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Action listeners
     * =================
     * */
    var onActiveItemChange: ((Int) -> Unit)? = null

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_member_card_item_list, this, true)

        itemContainerView.apply {
            val params = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params

            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { updateItems(items) }
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FamilyMemberCardItemAttr)
        typedArray.run {
            activeIndex = getInt(R.styleable.FamilyMemberCardItemAttr_activeIndex, -1)
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is FamilyMemberCardItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                FamilyMemberCardItem.Data(
                    child.cardMode,
                    child.cardListenerMode,
                    child.isDisabled,
                    child.imageScaleType,
                    child.profileName,
                    child.profileId,
                    child.customValidity,
                    child.profileMode,
                    child.imageSourceType,
                    child.profileAvatar,
                    child.addMemberButtonText,
                    child.hasCloseButton,
                    child.endButtonViewColor,
                    child.radioDeactivatable,
                    items.size == activeIndex,
                    child.memberStateColor,
                    child.memberStatus,
                    child.addMemberTitle,
                    child.validityMemberText
                )
            )
            updateItems(items)
        }
    }

    // ----------------------------------------------------------------------------------

    private fun updateItems(items: List<FamilyMemberCardItem.Data>) {
        if (!itemContainerView.isComputingLayout)
            recycleAdapter.items = items
    }
}