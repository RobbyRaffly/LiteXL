package com.myxlultimate.component.organism.missionListCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.missionListCard.adapter.MissionListCardsAdapter
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_mission_list_cards.view.*

class MissionListCards constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
// ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    var items = mutableListOf<MissionListItemCard.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
        }
    // ----------------------------------------------------------------------------------

    var onItemPress: ((Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var title = ""
    set(value) {
        field = value
        titleView.text = value
    }
    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        MissionListCardsAdapter { index ->
            onItemPress?.let {
                it(
                    index
                )
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_mission_list_cards, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MissionListCards)
        typedArray.run {
            title = getString(R.styleable.MissionListCards_title) ?: ""
        }

        typedArray.recycle()

        missionListCardsView.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            addItemDecoration(ListUtil.getListGapDecorator(context, 8,false))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is MissionListItemCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                MissionListItemCard.Data(
                    child.imageSourceType,
                    child.title,
                    child.buttonTitle,
                    child.information,
                    child.imageSource,
                    child.imageIcon,
                    child.missionStatus,
                    child.isDisabled
                )
            )
            recycleAdapter.items = items
        }
    }
}