package com.myxlultimate.component.molecule.tabSwitch

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.myxlultimate.component.R
import com.myxlultimate.component.atom.otpHolderSingle.OtpHolderSingle
import com.myxlultimate.component.atom.tabswitchItem.TabSwitchItem
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.organism.billingPaidItem.BillingPaidItem
import com.myxlultimate.component.organism.packageCard.FamilyPackageCard
import kotlinx.android.synthetic.main.molecule_tab_switch.view.*

class TabSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var items = mutableListOf<TabSwitchItem.Data>()
        set(value) {
            field = value
            setTabSwitch(items.size, items)
        }


    var backgroundColorMode = BackgroundColorMode.LIGHT
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var normalColor = ContextCompat.getColor(context, R.color.mud_palette_pale_blue)
        set(value) {
            field = value
            refreshView()
        }

    var selectedColor = ContextCompat.getColor(context, R.color.mud_palette_primary_blue)
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var normalTextColor = ContextCompat.getColor(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value
            refreshView()
        }

    var selectedTextColor = ContextCompat.getColor(context, R.color.mud_palette_basic_black)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var firstLabel = ""
        set(value) {
            field = value

            firstTabView.label = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var secondLabel = ""
        set(value) {
            field = value

            secondTabView.label = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var thirdLabel = ""
        set(value) {
            field = value

            thirdTabView.label = value

            if (value != "") {
                thirdTabView.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    var fourthLabel = ""
        set(value) {
            field = value

            fourthTabView.label = value

            if (value != "") {
                fourthTabView.visibility = View.VISIBLE
            }
        }

    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var activeIndex: Int = 0
        set(value) {
            field = value

            items.forEachIndexed { index, data ->
                val layoutParent = containerView
                val tabSwitchItem = layoutParent.getChildAt(index) as TabSwitchItem
                tabSwitchItem.isActive = value == index
            }
            firstTabView.isActive = value == 0
            secondTabView.isActive = value == 1
            thirdTabView.isActive = value == 2
            fourthTabView.isActive = value == 3

            onChange?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Int) -> Unit)? = null


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_tab_switch, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabSwitchAttr)
        typedArray.run {
            backgroundColorMode = BackgroundColorMode.values()[getInt(
                R.styleable.TabSwitchAttr_backgroundColorMode,
                0
            )]
            firstLabel = typedArray.getString(R.styleable.TabSwitchAttr_firstLabel) ?: ""
            secondLabel = typedArray.getString(R.styleable.TabSwitchAttr_secondLabel) ?: ""
            thirdLabel = typedArray.getString(R.styleable.TabSwitchAttr_thirdLabel) ?: ""
            fourthLabel = typedArray.getString(R.styleable.TabSwitchAttr_fourthLabel) ?: ""
            activeIndex = typedArray.getInt(R.styleable.TabSwitchAttr_activeIndex, 0)
            normalColor = typedArray.getColor(
                R.styleable.TabSwitchAttr_normalColor,
                ContextCompat.getColor(context, R.color.mud_palette_pale_blue)
            )
            selectedColor = typedArray.getColor(
                R.styleable.TabSwitchAttr_selectedColor,
                ContextCompat.getColor(context, R.color.mud_palette_primary_blue)
            )
            normalTextColor = typedArray.getColor(
                R.styleable.TabSwitchAttr_normalTextColor,
                ContextCompat.getColor(context, R.color.mud_palette_pale_blue)
            )
            selectedTextColor = typedArray.getColor(
                R.styleable.TabSwitchAttr_selectedTextColor,
                ContextCompat.getColor(context, R.color.mud_palette_primary_blue)
            )
            isShimmerOn = typedArray.getBoolean(
                R.styleable.TabSwitchAttr_isShimmerOn,
                false
            )
        }
        typedArray.recycle()

        firstTabView.onPress = { activeIndex = 0 }
        secondTabView.onPress = { activeIndex = 1 }
        thirdTabView.onPress = { activeIndex = 2 }
        fourthTabView.onPress = { activeIndex = 3 }

    }

    private fun refreshView() {
        shimmerLayout.visibility = if (isShimmerOn) View.VISIBLE else View.GONE

        containerView.backgroundTintList =
            if (isShimmerOn) ContextCompat.getColorStateList(context, R.color.transparent)
            else if (backgroundColorMode == BackgroundColorMode.LIGHT)
                ContextCompat.getColorStateList(context, R.color.mud_palette_pale_blue)
            else if (backgroundColorMode == BackgroundColorMode.DARK)
                ContextCompat.getColorStateList(context, R.color.mud_palette_transparent_white)
            else
                ColorStateList.valueOf(normalColor)

        firstTabView.apply {
            backgroundColorMode = this@TabSwitch.backgroundColorMode
            selectedColor = this@TabSwitch.selectedColor
            normalTextColor = this@TabSwitch.normalTextColor
            selectedTextColor = this@TabSwitch.selectedTextColor
            visibility = if (firstLabel.isNotEmpty() && !isShimmerOn) View.VISIBLE else View.GONE
        }

        secondTabView.apply {
            backgroundColorMode = this@TabSwitch.backgroundColorMode
            selectedColor = this@TabSwitch.selectedColor
            normalTextColor = this@TabSwitch.normalTextColor
            selectedTextColor = this@TabSwitch.selectedTextColor
            visibility = if (secondLabel.isNotEmpty() && !isShimmerOn) View.VISIBLE else View.GONE
        }

        thirdTabView.apply {
            backgroundColorMode = this@TabSwitch.backgroundColorMode
            selectedColor = this@TabSwitch.selectedColor
            normalTextColor = this@TabSwitch.normalTextColor
            selectedTextColor = this@TabSwitch.selectedTextColor
        }

        fourthTabView.apply {
            backgroundColorMode = this@TabSwitch.backgroundColorMode
            selectedColor = this@TabSwitch.selectedColor
            normalTextColor = this@TabSwitch.normalTextColor
            selectedTextColor = this@TabSwitch.selectedTextColor
        }
    }


    private fun setTabSwitch(numberOfSwitch: Int, items: MutableList<TabSwitchItem.Data>) {
        val layoutParent = containerView
        layoutParent.removeAllViews()
        for (index in 0 until numberOfSwitch) {
            val switchItem = TabSwitchItem(context)
            switchItem.label = items[index].label
            switchItem.isBulletVisible = items[index].isBulletVisible
            switchItem.setBulletColor = items[index].setBulletColor ?:R.attr.colorPrimary
            switchItem.backgroundColorMode = backgroundColorMode
            switchItem.normalTextColor = normalTextColor
            switchItem.selectedColor = selectedColor
            switchItem.selectedTextColor = selectedTextColor
            switchItem.onPress = { activeIndex = index}
            switchItem.isActive = activeIndex== index

            val layoutParams = LinearLayout.LayoutParams(
                0,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                1F
            )
            switchItem.layoutParams = layoutParams
            layoutParent.addView(switchItem)
        }
        return
    }

}