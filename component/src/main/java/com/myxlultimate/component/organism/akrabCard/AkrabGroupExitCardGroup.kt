package com.myxlultimate.component.organism.akrabCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.databinding.OrganismAkrabGroupExitCardGroupBinding
import com.myxlultimate.component.organism.akrabCard.adapter.AkrabGroupExitAdapter
import com.myxlultimate.component.util.ListUtil

class AkrabGroupExitCardGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private var binding: OrganismAkrabGroupExitCardGroupBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var groupTitle = ""
        set(value) {
            field = value
            binding?.titleView?.text = groupTitle
        }

    // ----------------------------------------------------------------------------------

    var items = mutableListOf<AkrabGroupExitCard.Data>()
        set(value) {
            field = value
            updateItems(value)
        }

    // ----------------------------------------------------------------------------------

    private val recycleAdapter by lazy {
        AkrabGroupExitAdapter(onCardPressed = { data, index ->
            onItemPress?.invoke(data, index)
        })
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * =================
     * Action listeners
     * =================
     * */

    var onItemPress: ((AkrabGroupExitCard.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        binding =
            OrganismAkrabGroupExitCardGroupBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
            )

        binding?.itemContainerView?.apply {
            val params = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params

            addItemDecoration(ListUtil.getListGapDecorator(context, 8))
            adapter = recycleAdapter.also { updateItems(items) }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun updateItems(items: List<AkrabGroupExitCard.Data>) {
        if (binding?.itemContainerView?.isComputingLayout != true)
            recycleAdapter.submitList(items)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is AkrabGroupExitCard) {
            super.addView(child, index, params)
        } else {
            items.add(
                AkrabGroupExitCard.Data(
                    child.title,
                    child.topSubtitle,
                    child.bottomSubtitle,
                    child.buttonText
                )
            )
            updateItems(items)
        }
    }
}