package com.myxlultimate.component.organism.familyPlanBenefitInputCard

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.ConverterUtil
import kotlinx.android.synthetic.main.organism_family_plan_benefit_input_card_item.view.*

class FamilyPlanBenefitInputCardItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        var dataType:String,
        var value:Long,
        var label:String = "Internet Quota",
        var unit:String = "GB",
        var info:String = "24 hours in all network",
        var maxInfo:String = "Maks dibagikan: %s",
        var isExpanded:Boolean = false,
        val max:Long = -1L,
        val min:Long = 0L,
        val multiplier:Int= 1,
        val maxErrorString:String,
        val minErrorString:String,
        val multiplierErrorString:String,
        var isValid:Boolean = true

    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var dataType:String = ""

    var unit:String = ""
    set(value) {
        field = value
        this.value = this.value
        tvUnit.text = unit
    }

    var maxValue = -1L
    var minValue = 0L
    var usage = 0L
    var multiplier = 1
    var value:Long = 0
    set(value) {
        field = value
        otfValue.setOnTextChange {  }

        val formatted =  ConverterUtil.convertDelimitedNumber(value)
        val cursorPos = formatted.length
        otfValue.text = formatted
        otfValue.getEditText()?.setSelection(cursorPos)
        tvValue.text = formatted+" "+unit


        errorMessage = when{
            value>maxValue -> String.format(maxErrorString, maxValue.toString())
            value!=0L && value< minValue -> String.format(minErrorString, minValue.toString())
            value.rem(multiplier) != 0L -> String.format(multiplierErrorString, multiplier.toString())
            else -> ""
        }

        otfValue.setOnTextChange { onTextChanged() }
    }

    /**
     * Setup text for title
     */
    var label = ""
        set(value) {
            field = value
            tvLabel.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup text for info
     */
    var info = ""
        set(value) {
            field = value
            tvInfo.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup text for Error
     */
    var errorMessage = ""
        set(value) {
            field = value
            tvError.text = value
            tvError.visibility = if(errorMessage.isEmpty()) GONE else VISIBLE
        }

    var maxErrorString = ""
    var minErrorString = ""
    var multiplierErrorString = ""
    var maxInfoString = ""
    // ----------------------------------------------------------------------------------

    /**
     * Setup isExpanded
     */
    var isExpanded = false
        set(value) {
            field = value
            if (value) {
                tvValue.visibility = View.GONE
                otfValue.visibility = View.VISIBLE
                tvUnit.visibility = VISIBLE
                val mv = ConverterUtil.convertDelimitedNumber(maxValue)
                tvInfo.text = String.format(maxInfoString, "$mv $unit")
                tvError.visibility = if(errorMessage.isEmpty()) GONE else VISIBLE
            }else{
                tvValue.visibility = View.VISIBLE
                otfValue.visibility = View.GONE
                tvUnit.visibility = GONE
                tvInfo.text = info
                tvError.visibility = GONE
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * VIEW LISTENER ATTRIBUTE
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var onValueChanged: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_plan_benefit_input_card_item, this, true)

        otfValue.setOnTextChange { onTextChanged() }

    }

    // ----------------------------------------------------------------------------------

    fun onTextChanged() {
        val formatted = (otfValue.text.toString().filter { it.isDigit() }.toIntOrNull() ?: 0L).toString()
        Log.e("ASD", formatted)

        value = formatted.toLong()

        tvValue.text = formatted+" "+unit

        onValueChanged?.invoke()

    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun applyData(familyId:String, index:Int, data:Data, onValueChanged: (Long) -> Unit){
        dataType = data.dataType
        value = data.value
        Log.e("ASD",value.toString())
        label = data.label
        unit = data.unit
        info= data.info
        this.onValueChanged = {
            data.isValid = errorMessage.isEmpty()
            onValueChanged.invoke(value)
        }
        maxValue = data.max
        minValue = data.min
        multiplier = data.multiplier
        maxErrorString = data.maxErrorString
        minErrorString = data.minErrorString
        multiplierErrorString = data.multiplierErrorString
        maxInfoString = data.maxInfo
        isExpanded = data.isExpanded

    }
}