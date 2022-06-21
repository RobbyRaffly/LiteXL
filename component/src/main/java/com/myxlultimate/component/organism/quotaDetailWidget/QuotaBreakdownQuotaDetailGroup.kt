package com.myxlultimate.component.organism.quotaDetailWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.myxlultimate.component.databinding.OrganismQuotaBreakdownQuotaDetailGroupBinding
import com.myxlultimate.component.organism.quotaDetailWidget.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.ListUtil

class QuotaBreakdownQuotaDetailGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs), RecycleViewAdapter.OnItemsClickListener {

    private var binding: OrganismQuotaBreakdownQuotaDetailGroupBinding? = null

    // ----------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    var groupTitle = ""
        set(value) {
            field = value
            binding?.apply {
                titleView.isVisible = value.isNotEmpty()
                titleView.text = value
            }
        }

    var showExclamationMarkCta = false
        set(value) {
            field = value
            binding?.exclamationMarkCta?.isVisible = value
        }

    // ----------------------------------------------------------------------------------

    var items = mutableListOf<QuotaBreakdownQuotaDetailWidget.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onHeaderLayoutPress: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onStopButtonPress: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onChangePlanPress: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onBuyAgainPlanPress: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onBonusCardPress: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onFailedButtonPress: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onTrashIconClick: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onSolidButtonPress: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    var onTransferButtonPress: ((QuotaBreakdownQuotaDetailWidget.Data, Int) -> Unit)? = null

    var onExclamationCtaClicked = {}

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        RecycleViewAdapter(this)
    }

    init {
        binding = OrganismQuotaBreakdownQuotaDetailGroupBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        binding?.exclamationMarkCta?.setOnClickListener { onExclamationCtaClicked() }
        binding?.itemContainerView?.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    override fun onHeaderLayoutPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onHeaderLayoutPress?.let { it(data, index) }
    }

    override fun onStopButtonPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onStopButtonPress?.let { it(data, index) }
    }

    override fun onChangePlanPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onChangePlanPress?.let { it(data, index) }
    }

    override fun onBuyAgainPlanPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onBuyAgainPlanPress?.let { it(data, index) }
    }

    override fun onBonusCardPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onBonusCardPress?.let { it(data, index) }
    }

    override fun onFailedButtonPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onFailedButtonPress?.let { it(data, index) }
    }

    override fun onTrashIconClick(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onTrashIconClick?.let { it(data, index) }
    }

    override fun onSolidButtonPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onSolidButtonPress?.let { it(data, index) }
    }

    override fun onTransferButtonPress(data: QuotaBreakdownQuotaDetailWidget.Data, index: Int) {
        onTransferButtonPress?.let { it(data,index) }
    }
}