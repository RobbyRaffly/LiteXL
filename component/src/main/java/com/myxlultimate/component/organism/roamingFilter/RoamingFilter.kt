package com.myxlultimate.component.organism.roamingFilter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_roaming_filter.view.*

class RoamingFilter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    data class Data(
        var title: String,
        var subTitle: String,
        var isChecked: Boolean = false,
        var hasBorderTop: Boolean = true,
        var hasBorderBottom: Boolean = false,
        var onCheckChange: ((Boolean) -> Unit)? = null
    )

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    var subTitle = ""
        set(value) {
            field = value
            subTitleView.text = value
        }

    var isChecked = false
        set(value) {
            field = value
            checkBoxView.isChecked = value
        }

    var hasBorderTop = true
        set(value) {
            field = value
            lineTopView.visibility = if(value) View.VISIBLE else View.INVISIBLE
        }

    var hasBorderBottom = false
        set(value) {
            field = value
            lineBottomView.visibility = if(value) View.VISIBLE else View.INVISIBLE
        }

    var onCheckChange: ((Boolean) -> Unit)? = null
        set(value) {
            field = value
            checkBoxView.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
                value?.invoke(b)
            }
        }

    fun getChecked() : Boolean {
        return checkBoxView.isChecked
    }

    private fun setListener() {
        TouchFeedbackUtil.attach(containerView){
            checkBoxView.toggle()
        }
    }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_roaming_filter, this, true)
        context.theme.obtainStyledAttributes(attrs, R.styleable.roamingFilterAttr, 0, 0).apply {
            try{
                getString(R.styleable.roamingFilterAttr_title)?.let {
                    title = it
                }
                getString(R.styleable.roamingFilterAttr_subtitle)?.let {
                    subTitle = it
                }
                getBoolean(R.styleable.roamingFilterAttr_hasBorderTop, true).let {
                    hasBorderTop = it
                }
                getBoolean(R.styleable.roamingFilterAttr_hasBorderBottom, false).let {
                    hasBorderBottom = it
                }
                getBoolean(R.styleable.roamingFilterAttr_isChecked, false).let {
                    isChecked = it
                }
            }finally {
                recycle()
            }

            setListener()
        }

    }

}