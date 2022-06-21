package com.myxlultimate.component.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Display
import com.myxlultimate.component.enums.DataUtilEnums
import java.text.DecimalFormat

object ConverterUtil {

    private const val ONE_GIGABYTE = 1048576F

    fun dpToPx(context: Context, dp: Float): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun spToPx(context: Context, sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
    }

    fun pxToDp(context: Context, px: Float): Float {
        return px * (DisplayMetrics.DENSITY_DEFAULT / context.resources.displayMetrics.densityDpi.toFloat())
    }

    fun convertVoice(voice: Int): Int {
        return voice / 60
    }

    fun revertVoice(voice: Int): Int {
        return voice * 60
    }

    fun convertDelimitedNumber(number: Long, useDot: Boolean = false): String {
        var value = DecimalFormat("#,###").format(number)

        if (useDot) {
            value = value.replace(",", ".")
        }

        return value
    }

    fun getInitialName(name: String): String {
        var lastLetter = ""
        var firstLetter = ""
        var initial = ""
        if (name.isNotEmpty()) {
            firstLetter = name.substring(0, 1).toUpperCase()
        }

        if (name.isNotEmpty() && name.split(" ").size > 1) {
            lastLetter = try {
                name.substring(name.lastIndexOf(" ") + 1, name.lastIndexOf(" ") + 2).toUpperCase()
            } catch (err: StringIndexOutOfBoundsException) {
                ""
            }
        }
        initial = firstLetter + lastLetter
        return initial
    }

    fun convertDataUnit(data: Float, dataUnit : DataUtilEnums = DataUtilEnums.KB): Pair<String, String> {
        var result = data
        var resultValue = data

        if(dataUnit == DataUtilEnums.MB) {
            result =  data * 1024.toFloat()
            resultValue = data * 1024.toFloat()
        }

        var unit = "KB"

        if (result >= 1024) {
            result /= 1024
            unit = "MB"
        }

        if (result >= 1024) {
            result /= 1024
            unit = "GB"
        }

        var formattedValue: String

        if (resultValue > ONE_GIGABYTE ) {
            formattedValue = String.format("%.2f", result).replace(',', '.')

            if (formattedValue[formattedValue.lastIndex] == '0') {
                formattedValue = formattedValue.substring(0, formattedValue.lastIndex)

                if (formattedValue[formattedValue.lastIndex] == '0') {
                    formattedValue = formattedValue.substring(0, formattedValue.lastIndex - 1)
                }
            }
        } else {
            formattedValue = "${result.toInt()}"
        }

        return Pair(formattedValue, unit)
    }

    fun convertToShortenedDelimitedNumber(value:Long, useDot: Boolean = false):String{
        return if(value>=1000)
            convertDelimitedNumber((value/1000), useDot)+"K"
        else
            convertDelimitedNumber(value, useDot)
    }
}