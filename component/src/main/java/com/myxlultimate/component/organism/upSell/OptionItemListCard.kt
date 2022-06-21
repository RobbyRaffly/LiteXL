package com.myxlultimate.component.organism.upSell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.upSell.adapter.OptionItemListAdapter
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_option_item_list_card.view.*

class OptionItemListCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var topTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    var bottomTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    var imageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
            iconView.imageSourceType = value
        }

    var imageSource: Any? = null
        set(value) {
            field = value
            iconView.imageSource = value
        }

    var items: List<OptionItem.Data> = emptyList()
        set(value) {
            field = value
            recycleAdapter.submitList(value)
        }

    private val recycleAdapter by lazy {
        OptionItemListAdapter {
            activeIndex = it
        }
    }

    var activeIndex = 0
        set(value) {
            field = value

            refreshAdapter(value)
            onActiveItemChange(value)
        }

    var onActiveItemChange: ((Int) -> Unit) = {}

    //region private
    private fun refreshView() {
        topTitleView.text = topTitle
        subtitleView.text = bottomTitle
    }

    private fun refreshAdapter(value: Int) {
        val newItems = items.mapIndexed { index, data ->
            OptionItem.Data(data.title, data.description, data.imageSource, index == value)
        }
        recycleAdapter.submitList(newItems)
    }
    //endregion

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_option_item_list_card, this, true)

        itemListView.apply {
            val params = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params

            addItemDecoration(ListUtil.getListGapDecorator(context, 12))
            adapter = recycleAdapter.also { it.submitList(items) }
            itemAnimator = null
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OptionItemListCard)
        typedArray.run {
            activeIndex = getInt(R.styleable.OptionItemListCard_activeIndex, -1)
            topTitle = getString(R.styleable.OptionItemListCard_topTitle) ?: ""
            bottomTitle = getString(R.styleable.OptionItemListCard_bottomTitle) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(
                R.styleable.OptionItemListCard_imageSourceType,
                3
            )]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.OptionItemListCard_imageSource)
            } else {
                getString(R.styleable.OptionItemListCard_imageSource)
            }
        }
        typedArray.recycle()
    }


    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is OptionItem) {
            super.addView(child, index, params)
        } else {
            val newItems = items.toMutableList()
            newItems.add(OptionItem.Data(child.title, child.description, child.icon, items.size == activeIndex))
            recycleAdapter.submitList(newItems)
        }
    }
}
