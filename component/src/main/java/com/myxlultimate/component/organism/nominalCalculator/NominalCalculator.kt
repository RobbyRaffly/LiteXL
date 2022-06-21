package com.myxlultimate.component.organism.nominalCalculator

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_nominal_calculator.view.*

class NominalCalculator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var minNominal: Long = 1000
    // ----------------------------------------------------------------------------------

    var maxNominal: Long = 5000000

    // ----------------------------------------------------------------------------------

    var leap: Long = 1000

    // ----------------------------------------------------------------------------------

    var nominal = minNominal
        set(value) {
            field = value
            nominalView.text = context.getString(R.string.indonesian_rupiah_balance_remaining,
                ConverterUtil.convertDelimitedNumber(value))
        }


    // ----------------------------------------------------------------------------------
    var minusIconEnabled = true
        set(value){
            field = value
            minusIcon.isEnabled = value
            minusIcon.setColorFilter(if(minusIconEnabled) Color.TRANSPARENT else resources.getColor(R.color.basicLightGrey))
        }
    // ----------------------------------------------------------------------------------
    var plusIconEnabled = true
        set(value){
            field = value
            plusIcon.isEnabled = value
            plusIcon.setColorFilter(if(plusIconEnabled) Color.TRANSPARENT else resources.getColor(R.color.basicLightGrey))
        }
    // ----------------------------------------------------------------------------------
    var minusIconOnClickAction: (()-> Unit)? = null
        set(value){
            field = value
            setAction()

        }
    // ----------------------------------------------------------------------------------
    var plusIconOnClickAction: (()-> Unit)? = null
        set(value){
            field = value
            setAction()

        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_nominal_calculator, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NominalCalculator)
        typedArray.run {
            minNominal = getInt(R.styleable.NominalCalculator_minNominal, 1000).toLong()
            maxNominal = getInt(R.styleable.NominalCalculator_maxNominal, 5000000).toLong()
            nominal = getInt(R.styleable.NominalCalculator_nominal, minNominal.toInt()).toLong()
            leap = getInt(R.styleable.NominalCalculator_leap, 1000).toLong()
        }
        typedArray.recycle()

        setAction()

    }

    private fun setAction(){
        if(minusIconOnClickAction!=null){
            TouchFeedbackUtil.attach(minusIcon){
                minusIconOnClickAction?.invoke()
            }
        }else {
            TouchFeedbackUtil.attach(minusIcon) {
                if (nominal < minNominal) {
                    nominal = minNominal
                } else {
                    nominal -= leap
                }
            }
        }

        if(plusIconOnClickAction!=null){
            TouchFeedbackUtil.attach(plusIcon){
                plusIconOnClickAction?.invoke()
            }
        }else {
            TouchFeedbackUtil.attach(plusIcon) {
                if (nominal > maxNominal) {
                    nominal = maxNominal
                } else {
                    nominal += leap
                }
            }
        }
    }
}