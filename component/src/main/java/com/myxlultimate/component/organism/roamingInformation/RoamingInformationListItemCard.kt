package com.myxlultimate.component.organism.roamingInformation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.roamingInformation.adapter.RoamingInformationItemListAdapter
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_roaming_information_list_items_card.view.*

class RoamingInformationListItemCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<RoamingInformationItemRow.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }
    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy { RoamingInformationItemListAdapter() }
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }
    // ----------------------------------------------------------------------------------

    var buttonPrimaryLabel = ""
        set(value) {
            field = value
            primaryButtonView.text = value
            IsEmptyUtil.setVisibility(value,primaryButtonView)
        }

    // ----------------------------------------------------------------------------------

    var buttonSecondaryLabel = ""
        set(value) {
            field = value
            secondaryButtonView.text = value
            IsEmptyUtil.setVisibility(value,secondaryButtonView)
        }
    // ----------------------------------------------------------------------------------

    var onPrimaryButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            primaryButtonView.setOnClickListener {
                onPrimaryButtonPress?.let { it() }
            }
        }

    // ----------------------------------------------------------------------------------

    var imageSource = R.drawable.ic_roaming_glode_prepaid
    set(value) {
        field = value
        imageView.imageSource = getDrawable(context,value)
    }

    // ----------------------------------------------------------------------------------

    var onSecondaryButtonPress : (() -> Unit)? = null
        set(value) {
            field = value

            secondaryButtonView.setOnClickListener {
                onSecondaryButtonPress?.let { it() }
            }
        }
    // ----------------------------------------------------------------------------------

    var isPrimaryButtonEnabled= true
    set(value) {
        field = value
        primaryButtonView.isEnabled = value
    }
    // ----------------------------------------------------------------------------------

    var isSecondaryButtonEnabled = true
    set(value) {
        field = value
        secondaryButtonView.isEnabled = value
    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_roaming_information_list_items_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.RoamingInformationListItemCard)
        typedArray.run {
            title = getString(R.styleable.RoamingInformationListItemCard_title)?:""
            buttonPrimaryLabel = getString(R.styleable.RoamingInformationListItemCard_primaryButtonLabel)?:""
            buttonSecondaryLabel = getString(R.styleable.RoamingInformationListItemCard_secondaryButtonLabel)?:""
            isPrimaryButtonEnabled = getBoolean(R.styleable.RoamingInformationListItemCard_isPrimaryButtonEnabled,true)
            isSecondaryButtonEnabled = getBoolean(R.styleable.RoamingInformationListItemCard_isSecondaryButtonEnabled,true)
        }
        typedArray.recycle()

        informationItemListRecyclerView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is RoamingInformationItemRow) {
            super.addView(child, index, params)
        } else {
            items.add(
                RoamingInformationItemRow.Data(
                    child.title,
                    child.imageSourceType,
                    child.imageSource
                )
            )
            recycleAdapter.items = items
        }
    }

}