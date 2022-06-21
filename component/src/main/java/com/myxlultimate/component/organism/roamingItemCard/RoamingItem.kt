package com.myxlultimate.component.organism.roamingItemCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.roamingItemCard.adapter.RoamingItemRowAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_roaming_item.view.*

class RoamingItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    data class Data(
        val title: String,
        val priceString: String,
        val detail: String,
        val imageSource:String,
        val items:List<RoamingItemRow.Data>
    )

    // ----------------------------------------------------------------------------------

    var title:String = ""
        set(value) {
            field = value

            titleView.text = title
            listTitleView.text = title
        }

    // ----------------------------------------------------------------------------------

    var priceString:String = ""
        set(value) {
            field = value

            priceView.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource:String = ""
        set(value) {
            field = value

            imageView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var detail:String = ""
        set(value) {
            field = value

            detailTextView.text = value
        }

    // ----------------------------------------------------------------------------------

    var items:List<RoamingItemRow.Data> = listOf()
        set(value) {
            field = value
            rowAdapter.items = items
            if(items.isEmpty())
            {
                singleInformationView.visibility = VISIBLE
                multipleInformationView.visibility = GONE
            }else{
                singleInformationView.visibility = GONE
                multipleInformationView.visibility = VISIBLE
            }

        }

    //

    var showLine:Boolean = true
        set(value) {
            field = value
            bottomLineView.visibility = if(value) VISIBLE else GONE
        }

    // ----------------------------------------------------------------------------------

    var rowAdapter:RoamingItemRowAdapter = RoamingItemRowAdapter()

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_roaming_item, this, true)
        context.theme.obtainStyledAttributes(attrs, R.styleable.roamingItemAttr, 0, 0).apply {
            try{
                getString(R.styleable.roamingItemAttr_title)?.let {
                    title = it
                }
                getString(R.styleable.roamingItemAttr_priceString)?.let {
                    priceString = it
                }
                getString(R.styleable.roamingItemAttr_imageSource)?.let {
                    imageSource = it
                }
                getString(R.styleable.roamingItemAttr_detail)?.let {
                    detail = it
                }
            }finally {
                recycle()
            }
        }

        roamingItemRowRecyclerView.adapter = rowAdapter

        roamingItemRowRecyclerView.addItemDecoration(ListUtil.getListGapDecorator(context,16))

    }

}