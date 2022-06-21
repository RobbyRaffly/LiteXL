package com.myxlultimate.component.organism.akrabCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.myxlultimate.component.databinding.OrganismAkrabSelectItemCardBinding
import com.myxlultimate.component.organism.akrabCard.enum.AkrabSelectItemCardMode
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_family_member_card_item.view.*

class AkrabSelectItemCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    data class Data(
        var cardMode : AkrabSelectItemCardMode,
        var title : String?="",
        var subtitle : String?="",
        var isRadioActive : Boolean? = false
    )

    private var binding: OrganismAkrabSelectItemCardBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    var akrabSelectItemMode = AkrabSelectItemCardMode.GROUP_DETAIL
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

    var subtitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ------------------------------------------------------------------------------------

    var radioDeactivatable = false

    // ------------------------------------------------------------------------------------

    var onChangePress: ((Boolean) -> Unit)? = null

    // ------------------------------------------------------------------------------------

    var isRadioActive = false
        set(value) {
            if (radioDeactivatable && field && !value) return
            field = value
            IsEmptyUtil.setVisibility(isRadioActive, selectedView)
            onChangePress?.let { it(isRadioActive) }
        }

    // ------------------------------------------------------------------------------------

    var onCardPress: (() -> Unit)? = null
        set(value) {
            field = value
            binding?.cardView?.apply {
                TouchFeedbackUtil.attach(this, onCardPress)
            }
        }

    // ------------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.titleView?.text = title
        binding?.subtitleView?.text = subtitle
        binding?.subtitleView?.visibility = if (subtitle.isNotEmpty()) View.VISIBLE else View.GONE
        when (akrabSelectItemMode) {
            AkrabSelectItemCardMode.GROUP_DETAIL -> {
                binding?.addIconView?.visibility = View.GONE
            }
            AkrabSelectItemCardMode.ADD -> {
                binding?.addIconView?.visibility = View.VISIBLE
            }
            else -> {

            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        binding =
            OrganismAkrabSelectItemCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

}