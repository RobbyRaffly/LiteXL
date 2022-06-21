package com.myxlultimate.component.organism.upSell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_option_item.view.*

class OptionItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs){
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val title: String,
        val description: String = "",
        val imageSource: String,
        var isActive: Boolean = false
    )


    var title = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var description = ""
    set(value) {
        field = value
        IsEmptyUtil.setVisibility(value,tvDescription)
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var isLineVisible = true
    set(value) {
        field = value
        IsEmptyUtil.setVisibility(value,lineView)
    }

    // ----------------------------------------------------------------------------------

    var icon = ""
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------

    var deactivatable = false

    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            if (deactivatable && field && !value) return

            field = value
            IsEmptyUtil.setVisibility(value, selectedView)
        }

    private fun toggleSelection(){
        if(isActive) return

        isActive = !isActive
        onChange(isActive)
    }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit) = {}

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_option_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OptionItem)
        typedArray.run {
            title = getString(R.styleable.OptionItem_title)?:""
            description = getString(R.styleable.OptionItem_subtitle)?:""
            icon = getString(R.styleable.OptionItem_icon)?:""
            isActive = getBoolean(R.styleable.OptionItem_isActive,false)
            isLineVisible = getBoolean(R.styleable.OptionItem_isShowButtonLine,true)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView) { toggleSelection()}
    }

    private fun refreshView() {
        titleView.text = title
        tvDescription.text = description
        iconView.imageSource = icon
    }
}
