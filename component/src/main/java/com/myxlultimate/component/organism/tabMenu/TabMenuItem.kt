package com.myxlultimate.component.organism.tabMenu

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_tab_menu_item.view.*


class TabMenuItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val label: String,
        var isActive: Boolean = false,
        var textColorActive: Int = -1,
        var textColorInactive: Int = -1,
        var activeMenuColor : Int = -1,
        var inactiveMenuColor : Int = -1,
        var paddingHorizontal: Float = 12f,
        var paddingVertical:Float = 4f,
        var inactiveAlpha:Float = 0.5f
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value

            activeLabelView.text = value
            inactiveLabelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var deactivatable = false

    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            if (deactivatable && field && !value) return

            field = value

            refreshView()

            onChange?.let {
                it(value)
            }
        }

    // ----------------------------------------------------------------------------------

    var textColor:Int = ContextCompat.getColor(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var textColorInactive:Int = ContextCompat.getColor(context, R.color.mud_palette_basic_black)
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var activeMenuColor:Int = ContextCompat.getColor(context, R.color.mud_palette_basic_black)
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var inactiveMenuColor:Int = ContextCompat.getColor(context, R.color.mud_palette_basic_white)
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var inactiveAlpha:Float = 0.5f
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var paddingVertical:Float = 4f
        set(value) {
            field =value
            setPadding()
        }

    var paddingHorizontal:Float = 12f
        set(value) {
            field = value
            setPadding()
        }
    // ----------------------------------------------------------------------------------
    var onChange: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_tab_menu_item, this, true)

        val colorPrimaryTypedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorPrimary, colorPrimaryTypedValue, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabMenuItemAttr)
        typedArray.run {
            label = getString(R.styleable.TabMenuItemAttr_label) ?: ""
            isActive = getBoolean(R.styleable.TabMenuItemAttr_isActive, false)
            textColor = getColor(R.styleable.TabMenuItemAttr_textColorActive, colorPrimaryTypedValue.data)
            textColorInactive = getColor(R.styleable.TabMenuItemAttr_textColorInactive, ContextCompat.getColor(context,R.color.mud_palette_basic_white))
            inactiveMenuColor = getColor(R.styleable.TabMenuItemAttr_inactiveMenuColor, ContextCompat.getColor(context,R.color.mud_palette_basic_white))
            activeMenuColor = getColor(R.styleable.TabMenuItemAttr_activeMenuColor, colorPrimaryTypedValue.data)
            paddingHorizontal = getDimension(R.styleable.TabMenuItemAttr_android_paddingHorizontal,12f)
            paddingVertical = getDimension(R.styleable.TabMenuItemAttr_android_paddingVertical,4f)
        }


        TouchFeedbackUtil.attach(containerView) { toggle() }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun toggle() {
        isActive = !isActive
    }

    private fun refreshView(){

        activeContainerView.visibility = if (isActive) View.VISIBLE else View.GONE
        inactiveContainerView.visibility = if (!isActive) View.VISIBLE else View.GONE

        inactiveContainerView.alpha = inactiveAlpha

        activeLabelView.setTextColor(textColor)
        inactiveLabelView.setTextColor(textColorInactive)
        ViewCompat.setBackgroundTintList(inactiveContainerView, ColorStateList.valueOf(inactiveMenuColor))
        ViewCompat.setBackgroundTintList(activeContainerView, ColorStateList.valueOf(activeMenuColor))

    }

    private fun setPadding(){
        val horizontal = (paddingHorizontal * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        val vertical = (paddingVertical * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        activeContainerView.setPadding(horizontal,vertical, horizontal, vertical)
        inactiveContainerView.setPadding(horizontal,vertical, horizontal, vertical)
    }
}