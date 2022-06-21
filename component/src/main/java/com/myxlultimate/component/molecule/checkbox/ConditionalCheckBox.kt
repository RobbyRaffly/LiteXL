package com.myxlultimate.component.molecule.checkbox

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.molecule_conditional_check_box.view.*
import kotlinx.android.synthetic.main.organism_status_payment_card.view.*

class ConditionalCheckBox(
    context: Context,
    res : AttributeSet?
) : LinearLayout(context, res) {

    var setCondition : Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    // ---------------------------------------------------------

    var setConditionText = ""
        set(value) {
            field = value
            refreshView()
        }

    // ---------------------------------------------------------

    var setTitleColor : Int = R.color.mud_palette_basic_dark_grey
        set(value){
            field = value
            refreshView()
        }

    // ---------------------------------------------------------


    init {
        LayoutInflater.from(context).inflate(R.layout.molecule_conditional_check_box, this, true)
        val typedArray = context.obtainStyledAttributes(res, R.styleable.ConditionalCheckBoxAttr)
        typedArray.run {
            setCondition = getBoolean(R.styleable.ConditionalCheckBoxAttr_setCondition, false)
            setConditionText = getString(R.styleable.ConditionalCheckBoxAttr_setConditionText) ?: ""
        }
        typedArray.recycle()


    }

    private fun refreshView() {
        val drawable = resources.getDrawable(R.drawable.circle_background)
        val drawableWrapper = DrawableCompat.wrap(drawable)

        if (setCondition) {
            DrawableCompat.setTint(drawableWrapper, ContextCompat.getColor(context, R.color.mud_palette_basic_green))
            checkIcon.background = drawableWrapper
        } else {
            DrawableCompat.setTint(drawableWrapper, ContextCompat.getColor(context, R.color.mud_palette_basic_light_grey))
            checkIcon.background = drawableWrapper
        }

        conditionTextView.text = setConditionText
        conditionTextView.setTextColor(ContextCompat.getColor(context, setTitleColor))
    }
}