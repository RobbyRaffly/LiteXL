package com.myxlultimate.component.organism.transactionCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.transactionCard.adapter.RecycleViewAdapter
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_transaction_payment_method_option_card_group.view.*

class TransactionPaymentMethodOptionCardGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private var activeIndex = -1

    // ----------------------------------------------------------------------------------

    var items = mutableListOf<TransactionPaymentMethodOptionCard.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int, TransactionPaymentMethodOptionCard.Data) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onTopUpClick: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onPromoButtonClick : ((Int, TransactionPaymentMethodOptionCard.Data) -> Unit)? = null
    // ----------------------------------------------------------------------------------

    var onButtonClicked: ((Int, TransactionPaymentMethodOptionCard.Data) -> Unit)? = null

    private val recycleAdapter by lazy {
        RecycleViewAdapter(
            onItemPress = { index ->
                setActiveIndex(index, items)
            },
            onTopUpClick = {
                onTopUpClick?.invoke()
            },
            onDetailPromoButtonPress = {
                onPromoButtonClick?.invoke(it,items[it])
            },
            onButtonClicked = {
                onButtonClicked?.invoke(it, items[it])
            }
        )
    }

    // ----------------------------------------------------------------------------------

    var label: String = ""
        set(value) {
            field = value
            labelView.text = value
            IsEmptyUtil.setVisibility(value,labelView)
        }
    // ----------------------------------------------------------------------------------

    var labelColor = R.color.mud_palette_basic_dark_grey
        set(value) {
            field = value
            labelView.setTextColor(getColor(context,value))
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_payment_method_option_card_group, this, true)

        paymentRvContainer.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 0))
            adapter = recycleAdapter.also { it.items = items }
        }

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.TransactionPaymentMethodOptionCardGroup
        )
        typedArray.run {
            label = getString(R.styleable.TransactionPaymentMethodOptionCardGroup_label) ?: ""
            labelColor = getResourceId(R.styleable.TransactionPaymentMethodOptionCardGroup_textColor,R.color.mud_palette_basic_dark_grey)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is TransactionPaymentMethodOptionCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                TransactionPaymentMethodOptionCard.Data(
                    child.code,
                    child.name,
                    child.information,
                    child.cashbackPercentage,
                    child.iconImage,
                    child.imageSourceType,
                    child.balance,
                    child.isBalanceVisible,
                    child.inactiveRadio,
                    child.isBalanceEnough,
                    child.isCardSelected,
                    balanceString = child.balanceString,
                    ribbonTitle = child.ribbonTitle,
                    promoButtonTitle = child.promoButtonTitle,
                    promoStringTitle = child.promoStringTitle,
                    bottomInformation = child.bottomInformation,
                    bottomInformationUnderCardView = child.bottomInformationUnderCardView,
                    buttonLabel = child.buttonLabel
                )
            )
            recycleAdapter.items = items
        }
    }

    private fun setActiveIndex(value: Int, list: List<TransactionPaymentMethodOptionCard.Data>) {
        if (!paymentRvContainer.isComputingLayout) {
            activeIndex = value
            list.forEachIndexed { index, _ ->
                list[index].isCardSelected = index == value
            }
            onItemPress?.let {
                it(value, list[value])
            }
            recycleAdapter?.notifyDataSetChanged()
        }
    }
}