package com.myxlultimate.component.organism.quotaDetailWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismQuotaDetailWidgetQuotaBreakdownSimpleBinding
import com.myxlultimate.component.molecule.quotaDetail.QuotaDetailItem
import com.myxlultimate.component.molecule.quotaDetail.adapters.RecycleViewAdapter
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ListUtil

class QuotaBreakdownQuotaDetailSimpleWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val TAG = this::class.java.simpleName

    val binding = OrganismQuotaDetailWidgetQuotaBreakdownSimpleBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private val recycleAdapter by lazy { RecycleViewAdapter() }

    data class Data(
        var iconImage: String = "",
        var name: String = "",
        var items: MutableList<QuotaDetailItem.Data>,
        var bottomLabelText: String = "",
        var bottomValueText: String = ""
    )

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Icon image
     * =================
     * */
    var iconImage = ""
        set(value) {
            field = value

            updateView()
        }

    private var _iconImage = ""
        set(value) {
            field = value

            binding.iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Name
     * =================
     * */
    var name = ""
        set(value) {
            field = value

            updateView()
        }

    private var _name = ""
        set(value) {
            field = value

            binding.nameView.text = value
        }

    /**
     * =================
     * Items
     * =================
     * */
    var items = mutableListOf<QuotaDetailItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
            binding.itemContainerView.visibility = if (items.isNotEmpty()) View.VISIBLE
            else View.GONE

            updateView()
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Text Bottom
     * =================
     * */
    var bottomLabelText = ""
        set(value) {
            field = value

            updateView()
        }

    private var _bottomLabelText = ""
        set(value) {
            field = value

            binding.bottomLabelText.text = value
        }

    var bottomValueText = ""
        set(value) {
            field = value

            updateView()
        }

    private var _bottomValueText = ""
        set(value) {
            field = value

            binding.bottomValueText.text = value
        }

    var iconViewBottomSource: String? = null
        set(value) {
            field = value

            updateView()
        }

    private var _iconViewBottomSource: String = ""
        set(value) {
            field = value
            binding.iconViewBottom.imageSourceType = ImageSourceType.BASE64
            binding.iconViewBottom.imageSource = value
        }

    var hideValidityLayout = false
    set(value) {
        field = value
        binding.validityLayout.visibility = if (hideValidityLayout) View.INVISIBLE else View.VISIBLE
    }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.QuotaBreakdownQuotaDetailWidgetAttr
        )

        typedArray.run {
            name = getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_name) ?: ""
            iconImage = getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_iconImage) ?: ""
            bottomLabelText = getString(
                R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_bottomLabelText
            ) ?: ""
            bottomValueText = getString(
                R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_bottomValueText
            ) ?: ""
        }
        typedArray.recycle()

        binding.itemContainerView.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = recycleAdapter.also { it.items = items }
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is QuotaDetailItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                QuotaDetailItem.Data(
                    child.name,
                    child.quotaType,
                    child.iconImage,
                    child.information,
                    child.remaining,
                    child.total,
                    child.isUnlimited,
                    child.isFup,
                    child.fupText,
                    child.fupLimitRule,
                    child.lowThreshold,
                    child.disableDetailItem
                )
            )

            binding.itemContainerView.visibility = View.VISIBLE
            binding.headerHorizontalLine.visibility = View.VISIBLE
        }
    }

    /**
     * =================================
     * Update view (keep sync)
     * =================================
     * */
    private fun updateView() {
        setBasicInfo()
        setBottomText()
        setBottomImage()
    }

    private fun setBasicInfo() {
        _name = name
        _iconImage = iconImage
    }

    private fun setBottomText() {
        _bottomLabelText = bottomLabelText
        _bottomValueText = bottomValueText
    }

    private fun setBottomImage() {
        iconViewBottomSource?.let {
            _iconViewBottomSource = it
        }
    }
}
