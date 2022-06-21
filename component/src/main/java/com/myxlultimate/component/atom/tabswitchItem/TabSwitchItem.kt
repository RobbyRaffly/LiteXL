package com.myxlultimate.component.atom.tabswitchItem

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.atom_tab_switch_item.view.*

class TabSwitchItem constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val label: String,
        val isBulletVisible:Boolean = false,
        val setBulletColor: Int? = R.attr.colorBackgroundPrimary
    )

    var backgroundColorMode = BackgroundColorMode.LIGHT
        set(value) {
            field = value

            this.isActive = isActive
        }

    // ----------------------------------------------------------------------------------

    var selectedColor = R.color.mud_palette_primary_blue
        set(value){
            field = value
            this.isActive = isActive
        }

    // ----------------------------------------------------------------------------------

    var normalTextColor = R.color.mud_palette_basic_white
        set(value){
            field = value
            this.isActive = isActive
        }

    var selectedTextColor = R.color.mud_palette_basic_black
        set(value){
            field = value
            this.isActive = isActive
        }


    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value

            labelView.text = value
        }

    var isBulletVisible = false
    set(value){
        field = value
        ivBullet.visibility = if(isBulletVisible) VISIBLE else GONE
    }

    var setBulletColor = R.attr.colorBackgroundPrimary
        set(value){
            field = value

            val typedValue = TypedValue()
            context.theme.resolveAttribute(value, typedValue, true)
            val color = ContextCompat.getColor(context, typedValue.resourceId)

            ivBullet.setColorFilter(color)
        }

    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            field = value

            containerView.backgroundTintList =
                if (value) {
                    when(backgroundColorMode){
                        BackgroundColorMode.LIGHT -> ContextCompat.getColorStateList(context,R.color.mud_palette_primary_blue)
                        BackgroundColorMode.DARK -> ContextCompat.getColorStateList(context,R.color.mud_palette_basic_white)
                        else -> ColorStateList.valueOf(selectedColor)
                    }
                } else {
                    ContextCompat.getColorStateList(context,android.R.color.transparent)
                }

            labelView.setTextColor(
                if (value) {
                    val typedValue = TypedValue()
                    val theme = context.theme
                    theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
                    if (backgroundColorMode == BackgroundColorMode.LIGHT)
                        ContextCompat.getColor(context,R.color.mud_palette_basic_white)
                    else if(backgroundColorMode == BackgroundColorMode.DARK)
                        ContextCompat.getColor(context,typedValue.resourceId)
                    else
                        selectedTextColor
                } else {
                    if (backgroundColorMode == BackgroundColorMode.LIGHT)
                        ContextCompat.getColor(context,R.color.mud_palette_basic_black)
                    else if(backgroundColorMode == BackgroundColorMode.DARK)
                        ContextCompat.getColor(context,R.color.mud_palette_basic_white)
                    else
                        normalTextColor
                }
            )
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.atom_tab_switch_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabSwitchItemAttr)
        typedArray.run {
            backgroundColorMode = BackgroundColorMode.values()[getInt(R.styleable.TabSwitchItemAttr_backgroundColorMode, 0)]
            label = getString(R.styleable.TabSwitchItemAttr_label) ?: ""
            isActive = getBoolean(R.styleable.TabSwitchItemAttr_isActive, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView, onPress)
    }
}