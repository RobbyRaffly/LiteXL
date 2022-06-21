package com.myxlultimate.component.organism.flatQuickMenu

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.storeCard.enums.SecondaryColorMode
import com.myxlultimate.component.util.ColorUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_banner_card_banner.view.*
import kotlinx.android.synthetic.main.organism_flat_quick_menu_item.view.*
import kotlinx.android.synthetic.main.organism_flat_quick_menu_item.view.containerView
import java.lang.Exception

class FlatQuickMenuItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val label: String,
        val iconImage: String,
        var isActive: Boolean = false,
        val labelColor : Int = 0,
        var labelHexString : String
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value

            labelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var iconImage = ""
        set(value) {
            field = value

            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------


    var deactivatable = false

    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            if (deactivatable && field && !value) return

            field = value

            containerView.alpha = if (value) 1F else .5F

            onChange?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null


    // ----------------------------------------------------------------------------------

    var textColor : Int = 0
        set(value) {
            field = value
            setTextColor()
        }

    // ----------------------------------------------------------------------------------

    var labelHexString = ""
    set(value) {
        field = value
        setTextColor()
    }
    // ----------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_flat_quick_menu_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlatQuickMenuItemAttr)
        typedArray.run {
            label = getString(R.styleable.FlatQuickMenuItemAttr_label) ?: ""
            iconImage = getString(R.styleable.FlatQuickMenuItemAttr_iconImage) ?: ""
            isActive = getBoolean(R.styleable.FlatQuickMenuItemAttr_isActive, false)
            textColor = getResourceId(R.styleable.FlatQuickMenuItemAttr_labelColor,0)
            labelHexString = getString(R.styleable.FlatQuickMenuItemAttr_labelHexStringColor)?:""
        }

        TouchFeedbackUtil.attach(containerView) { toggle() }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun toggle() {
        isActive = !isActive
    }

    fun setTextColor() {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)

        if (labelHexString.isNotEmpty()) {
            ColorUtil.parseColor(
                labelHexString,
                onParsed = {
                    labelView.setTextColor(it)
                },
                onError = {
                    labelView.setTextColor(ContextCompat.getColor(context, if(textColor ==0 )typedValue.resourceId else textColor))
                }
            )
        } else {
            labelView.setTextColor(ContextCompat.getColor(context, if(textColor ==0 )typedValue.resourceId else textColor))
        }

    }
}