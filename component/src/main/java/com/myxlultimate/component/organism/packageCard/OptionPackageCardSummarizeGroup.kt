package com.myxlultimate.component.organism.packageCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.packageCard.adapters.OptionPackageCardRecycleViewAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_option_package_card_summarize_group.view.*
import java.text.SimpleDateFormat

class OptionPackageCardSummarizeGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<OptionPackageCard.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        OptionPackageCardRecycleViewAdapter { index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }

    // ----------------------------------------------------------------------------------

    var date: Long = 0
        set(value) {
            field = value
            refreshView()
        }

    var dateString = ""
        set(value) {
            field = value
            refreshView()
        }

    var customDateFormat: String = "MMMM yyyy"
        set(value) {
            field = value
            refreshView()
        }

    private fun refreshView() {
        val format = SimpleDateFormat(customDateFormat)
        dateView.text = if (dateString.isNotEmpty()) dateString else format.format(date * 1000L)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_option_package_card_summarize_group, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.PackageCardOptionSummarizeGroupAttr)
        typedArray.run {
            date =
                typedArray.getInt(R.styleable.PackageCardOptionSummarizeGroupAttr_date, 0).toLong()
            customDateFormat =
                typedArray.getString(R.styleable.PackageCardOptionSummarizeGroupAttr_customDateFormat)
                    ?: "MMMM yyyy"
            dateString = typedArray.getString(R.styleable.PackageCardOptionSummarizeGroupAttr_dateString) ?: ""
        }
        typedArray.recycle()

        itemContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is OptionPackageCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                OptionPackageCard.Data(
                    child.name,
                    child.validity,
                    child.price,
                    child.originalPrice,
                    child.information,
                    child.hasNextButton,
                    child.hasPromo,
                    child.image,
                    child.availability,
                    child.ribbonTitle,
                    bottomTitle = child.bottomTitle,
                    bottomTitleColor = child.bottomTitleColor,
                    isLock = child.isLock,
                    transactionStatus = child.transactionStatus,
                    isForHistory = child.isForHistory,
                    upperRightIcon = child.upperRightIcon,
                    loyaltyRibbonTitle = child.loyaltyRibbonTitle,
                    loyaltySmallIcon = child.loyaltyIcon,
                    startLoyaltyRibbonHexColor = child.startHexColor,
                    endLoyaltyRibbonHexColor = child.endHexColor,
                    useSmallTitle = child.useSmallTitle,
                    hasChinView = child.hasChinView,
                    chinListItems = child.chinListItem,
                    cardBackgroundMode = child.cardBackgorundMode,
                    ribbonMode = child.ribbonMode,
                    backgroundUrl = child.backgroundUrl,
                    setRibbonColor = child.setRibbonColor
                )
            )
            recycleAdapter.items = items
        }
    }

}