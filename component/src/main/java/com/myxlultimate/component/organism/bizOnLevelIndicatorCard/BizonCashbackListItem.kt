package com.myxlultimate.component.organism.bizOnLevelIndicatorCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.bizOnLevelIndicatorCard.enum.BizOnWalletStatus
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_bizon_cashback_tier.view.*
import kotlinx.android.synthetic.main.organism_bizon_level_indicator_card.view.*
import kotlinx.android.synthetic.main.organism_table_three_column.view.*

class BizonCashbackListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    twoColumn : Boolean? = false
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val casbackName : String?="",
        val casbackInformation : String?="",
        val casbackPrice : Long
    )

    // ----------------------------------------------------------------------------------

    var vbizonCashbackImg = resources.getString(R.string.xl_bizon_cashback_gopay)
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var vcasbackName = ""
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var vcasbackInformation = ""
        set(value) {
            field = value
            setRefreshView()
        }


    // ----------------------------------------------------------------------------------

    var vcasbackPrice: Long = 0
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_bizon_cashback_tier, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BizonCashbackListTierItem)
        typedArray.run {
            vcasbackName = getString(R.styleable.BizonCashbackListTierItem_casbackName)?:""
        }
        typedArray.recycle()

    }

    private fun setRefreshView() {

        casbackNameView.text = context.getString(R.string.xl_bizon_title_total_cashback)
        casbackInformationView.text = vcasbackInformation
        casbackPriceView.text = context.getString(
            R.string.indonesian_rupiah_balance_remaining,
            ConverterUtil.convertDelimitedNumber(vcasbackPrice, true)
        )
        casbackPriceView.visibility = if (vcasbackPrice <= 0.toLong()) View.GONE else View.VISIBLE

        when(vcasbackName){
            BizOnWalletStatus.GOPAY.method ->{
                casbackIconView.imageSource = resources.getString(BizOnWalletStatus.GOPAY.pictureBase64)
            }
            BizOnWalletStatus.OVO.method ->{
                casbackIconView.imageSource = resources.getString(BizOnWalletStatus.OVO.pictureBase64)
            }
            BizOnWalletStatus.SHOPEEPAY.method ->{
                casbackIconView.imageSource = resources.getString(BizOnWalletStatus.SHOPEEPAY.pictureBase64)
            }
            BizOnWalletStatus.DANA.method ->{
                casbackIconView.imageSource = resources.getString(BizOnWalletStatus.DANA.pictureBase64)
            }
            BizOnWalletStatus.AKULAKU.method ->{
                casbackIconView.imageSource = resources.getString(BizOnWalletStatus.AKULAKU.pictureBase64)
            }
            BizOnWalletStatus.KREDIVO.method ->{
                casbackIconView.imageSource = resources.getString(BizOnWalletStatus.KREDIVO.pictureBase64)
            }
            else -> {
                casbackIconView.imageSource = vbizonCashbackImg
            }
        }
    }
}