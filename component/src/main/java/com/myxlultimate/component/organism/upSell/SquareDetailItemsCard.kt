package com.myxlultimate.component.organism.upSell

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.roamingInformation.RoamingInformationItemRow
import com.myxlultimate.component.organism.upSell.adapter.SquareDetailInfoItemsAdapter
import com.myxlultimate.component.util.ListUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_square_detail_items_card.view.*

class SquareDetailItemsCard@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs){
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    var items = mutableListOf<RoamingInformationItemRow.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value

            itemListView.visibility = if(value.isEmpty()) View.GONE else View.VISIBLE
        }
    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy { SquareDetailInfoItemsAdapter() }
    // ----------------------------------------------------------------------------------


    var topLeftTitle = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var topRightTitle = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var title = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var ribbonLabel = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var bottomLeftPrice = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var bottomRightPrice = ""
    set(value) {
        field = value
        refreshView()
        bottomRightPriceView.apply {
            if (value.isNotEmpty()) {
                if (Build.VERSION.SDK_INT >= 23) {
                    foreground = getDrawable(context,R.drawable.strikethrough_foreground)
                } else {
                    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }

    // ----------------------------------------------------------------------------------

    var buttonTextTitle = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var bottomBeardText = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var bottomBeardIcon = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var onCardPressed: (() -> Unit)? = null
    set(value) {
        field = value
        TouchFeedbackUtil.attach(this,value)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_square_detail_items_card, this, true)

        itemListView.apply {
            val params = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params

            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }

            visibility = if(items.isEmpty()) View.GONE else View.VISIBLE
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareDetailItemsCard)
        typedArray.run {
            topLeftTitle = getString(R.styleable.SquareDetailItemsCard_topLeftTitle)?:""
            topRightTitle = getString(R.styleable.SquareDetailItemsCard_topRightTitle)?:""
            title = getString(R.styleable.SquareDetailItemsCard_title)?:""
            ribbonLabel = getString(R.styleable.SquareDetailItemsCard_ribbonLabel)?:""
            bottomLeftPrice = getString(R.styleable.SquareDetailItemsCard_bottomLeftPrice)?:""
            bottomRightPrice = getString(R.styleable.SquareDetailItemsCard_bottomRightPrice)?:""
            bottomBeardText = getString(R.styleable.SquareDetailItemsCard_bottomBeardText)?:""
            bottomBeardIcon = getString(R.styleable.SquareDetailItemsCard_bottomBeardIcon)?:""
        }
        typedArray.recycle()

    }

    private fun refreshView() {
        topTitleView.text = topLeftTitle
        rightTopTitleView.text = topRightTitle
        titleView.text = title
        ribbonLabelView.text = ribbonLabel
        layoutRibbon.visibility = if (ribbonLabel.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        bottomLeftPriceView.text = bottomLeftPrice
        bottomRightPriceView.text = bottomRightPrice
        bottomButtonTitleView.text = buttonTextTitle
        bottomTitleView.text = bottomBeardText
        bottomIconView.imageSource = bottomBeardIcon
        bottomBeardLayout.visibility = if(bottomBeardText.isNotEmpty()) View.VISIBLE else View.GONE
    }
}