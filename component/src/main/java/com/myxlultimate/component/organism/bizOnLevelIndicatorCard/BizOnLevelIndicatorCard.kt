package com.myxlultimate.component.organism.bizOnLevelIndicatorCard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.bizOnLevelIndicatorCard.adapter.BizonItemCashbackAdapter
import com.myxlultimate.component.organism.bizOnLevelIndicatorCard.enum.BizOnLevelInformationFlagStatus
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_bizon_level_indicator_card.view.*
import kotlinx.android.synthetic.main.organism_bizon_level_indicator_card.view.bottomView
import kotlinx.android.synthetic.main.organism_bizon_level_indicator_card.view.informationView
import kotlinx.android.synthetic.main.organism_bizon_level_indicator_card.view.progressView


class BizOnLevelIndicatorCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val flagStatus: BizOnLevelInformationFlagStatus,
        val casbackIcon: String,
        val casbackName: String,
        val casbackInformation: String,
        val casbackPrice: Long,
        val bizonLevelTitle: String,
        val bizonTotalLevelTitle: String,
        val minSpending: Int,
        val maxSpending: Int,
        val startBold: Int = 0,
        val endBold: Int = 0
    )

    var level = ""
    // ----------------------------------------------------------------------------------


    var flagStatus = BizOnLevelInformationFlagStatus.NONE
        set(value) {
            field = value
            setRefreshView()
        }




    // ----------------------------------------------------------------------------------

    var bizonLevelBigIcon = resources.getString(R.string.xl_bizon_level1)
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var bizonLevelSmallIcon = resources.getString(R.string.xl_bizon_level2)
        set(value) {
            field = value
            setRefreshView()
        }

    var items = mutableListOf<BizonCashbackListItem.Data>()
        set(value) {
            field = value
            bizonItemCashbackAdapter.items = value
        }


    // ----------------------------------------------------------------------------------

    var bizonLevelTitle = ""
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var bizonTotalLevelTitle = ""
        set(value) {
            field = value
            setRefreshView()
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            setRefreshView()
        }

    var startBold = 0
        set(value) {
            field = value
            setRefreshView()
        }


    var endBold = 0
        set(value) {
            field = value
            setRefreshView()
        }


    var minSpending = 0
    // ----------------------------------------------------------------------------------

    var maxSpending = 1
    // ----------------------------------------------------------------------------------

    var currentSpending = 0
        set(value) {
            field = value
            setUpProgress()
        }

    // ----------------------------------------------------------------------------------

    var onBottomClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(bottomView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private val bizonItemCashbackAdapter by lazy {
        BizonItemCashbackAdapter()
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_bizon_level_indicator_card, this, true)

        rv_cashbackView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            //addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = bizonItemCashbackAdapter.also {
                it.items = items
            }
        }

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BizonLevelIndicatorCard)
        typedArray.run {
//            bizonLevelBigIcon =
//                getString(R.styleable.BizonLevelIndicatorCard_bizonBigIcon) ?: ""
//            bizonLevelSmallIcon =
//                getString(R.styleable.BizonLevelIndicatorCard_bizOnSmallIcon) ?: ""
            bizonLevelTitle =
                getString(R.styleable.BizonLevelIndicatorCard_bizOnLevelTitle) ?: ""
            flagStatus = BizOnLevelInformationFlagStatus.values()[getInt(
                R.styleable.BizonLevelIndicatorCard_flagStatusLevel,
                0 // NONE
            )]
            minSpending = getInt(R.styleable.BizonLevelIndicatorCard_bizonMinSpending, 0)
            maxSpending = getInt(R.styleable.BizonLevelIndicatorCard_bizonMaxSpending, 1)
            currentSpending = getInt(R.styleable.BizonLevelIndicatorCard_bizonCurrentSpending, 0)
        }
        typedArray.recycle()


    }

    private fun setRefreshView() {

        bizonLevelIconView.imageSource = bizonLevelBigIcon
        bizonLevelProgressIcon.imageSource = bizonLevelSmallIcon
        bizonLevelTitleView.text = resources.getString(R.string.xl_bizon_level_title, bizonLevelTitle)
        bizonTotalLevelTitleView.text = bizonTotalLevelTitle
        informationView.text = information
        informationView.setBold(information, startBold, endBold)
        setUpFlagStatus()

    }

    private fun setUpFlagStatus() {

        when (flagStatus) {
            BizOnLevelInformationFlagStatus.NONE -> {
                dividerCashbackView.visibility = View.GONE
                rv_cashbackView.visibility = View.GONE
                titleLevel.visibility = View.GONE
                bizonLevelProgressIcon.imageSource = resources.getString(R.string.xl_bizon_level1)
            }
            BizOnLevelInformationFlagStatus.LEVEL1 -> {
                dividerCashbackView.visibility = View.VISIBLE
                rv_cashbackView.visibility = View.VISIBLE
                progressView.progressDrawable = ContextCompat.getDrawable(context, R.drawable.bizon_progress_gradiant_one)
                titleLevel.visibility = View.VISIBLE
                bizonLevelProgressIcon.imageSource = resources.getString(R.string.xl_bizon_level2)
            }
            BizOnLevelInformationFlagStatus.LEVEL2 -> {
                dividerCashbackView.visibility = View.VISIBLE
                rv_cashbackView.visibility = View.VISIBLE
                progressView.progressDrawable = ContextCompat.getDrawable(context, R.drawable.bizon_progress_gradiant_two)
                titleLevel.visibility = View.VISIBLE
                bizonLevelProgressIcon.imageSource = resources.getString(R.string.xl_bizon_level3)
            }
            BizOnLevelInformationFlagStatus.LEVEL3 -> {
                dividerCashbackView.visibility = View.VISIBLE
                rv_cashbackView.visibility = View.VISIBLE
                progressView.progressDrawable = ContextCompat.getDrawable(context, R.drawable.bizon_progress_gradiant_three)
                titleLevel.visibility = View.VISIBLE
                bizonLevelProgressIcon.imageSource = resources.getString(R.string.xl_bizon_level4)
            }
            BizOnLevelInformationFlagStatus.LEVEL4 -> {
                dividerCashbackView.visibility = View.VISIBLE
                rv_cashbackView.visibility = View.VISIBLE
                progressView.progressDrawable = ContextCompat.getDrawable(context, R.drawable.bizon_progress_gradiant_four)
                titleLevel.visibility = View.VISIBLE
                bizonLevelProgressIcon.visibility = View.GONE
            }
        }
    }

    private fun TextView.setBold(
        charSequence: CharSequence,
        start: Int,
        end: Int
    ) {
        if (charSequence.isNotEmpty() && charSequence.length > start && charSequence.length > end && end > start) {

            val ssBuilder = SpannableStringBuilder(charSequence)
            val boldSpan: ClickableSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = getColor(
                        context,
                        R.color.mud_palette_basic_black
                    )
                    ds.isUnderlineText = false
                    ds.typeface = Typeface.DEFAULT_BOLD
                }

                override fun onClick(widget: View) {
//                    TODO("Not yet implemented")
                }
            }

            charSequence.let {
                ssBuilder.setSpan(
                    boldSpan,
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            this.text = ssBuilder

            this.movementMethod = LinkMovementMethod.getInstance()
            this.highlightColor = Color.TRANSPARENT
        }
    }

    private fun setUpProgress() {
        if (maxSpending < 1) {
            progressView.progress = 0
            return
        }
        val progress =
            ((currentSpending - minSpending).toFloat() / (maxSpending - minSpending).toFloat()) * 100 // to percentage
        progressView.progress = progress.toInt()
    }

    override fun addView(child: View?,  index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is BizonCashbackListItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                BizonCashbackListItem.Data(
                    child.vcasbackName,
                    child.vcasbackInformation,
                    child.vcasbackPrice
                )
            )
            bizonItemCashbackAdapter.items = items
        }
    }

}