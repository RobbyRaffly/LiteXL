package com.myxlultimate.component.organism.dompetCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.billingEstimationItem.BillingEstimationItem
import com.myxlultimate.component.organism.dompetCard.adapter.DompetPaymentAdapter
import com.myxlultimate.component.util.ListUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_dompet_payment_group.view.*

class DompetPaymentGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), DompetPaymentAdapter.OnItemClickListener {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title: String = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var onPressInformation : (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(informationView, value)
        }
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<DompetPaymentWidget.Data>()
        set(value) {
            field = value
            dompetPaymentAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: ((DompetPaymentWidget.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val dompetPaymentAdapter by lazy {
        DompetPaymentAdapter(this)
    }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_dompet_payment_group, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DompetPaymentGroupAttr)

        typedArray.run {
            title = getString(R.styleable.DompetPaymentGroupAttr_title) ?: ""
        }
        typedArray.recycle()

        dompetPaymentContainerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = dompetPaymentAdapter.also {
                it.items = items
            }
        }


        TouchFeedbackUtil.attach(informationView, onPressInformation)
    }

    override fun onPress(data: DompetPaymentWidget.Data, index: Int) {
        onPress?.let { it(data, index) }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is DompetPaymentWidget) {
            super.addView(child, index, params)
        }else{
            items.add(
                DompetPaymentWidget.Data(
                    child.widgetStyle,
                    child.title,
                    child.subTitle,
                    child.imageSourceType,
                    child.imageSource,
                    child.imageSourceTypeIcon,
                    child.imageSourceIcon,
                    child.ribbonLabel,
                    child.ribbonLabelNew,
                    child.isErrorSubTitle,
                    child.warningTitle,
                    child.buttonLabelPrimary,
                    child.buttonLabelSecondary
                )
            )
            dompetPaymentAdapter.items = items
        }
    }
}