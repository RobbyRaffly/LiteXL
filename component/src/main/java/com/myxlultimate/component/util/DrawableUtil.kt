package com.myxlultimate.component.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue


object DrawableUtil {
    fun CreateGradientBackground(
        context: Context,
        startHexColor: String,
        endHexColor: String,
        cornerRadius: Float? = 0f
    ): GradientDrawable? {
        return try {
            val dpToFloat = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, cornerRadius ?: 0f,
                context.resources.displayMetrics
            )

            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(
                    Color.parseColor(startHexColor),
                    Color.parseColor(endHexColor)
                )
            )
            gradientDrawable.cornerRadius = dpToFloat
            gradientDrawable.setGradientCenter(0.5f, 0.75f)
            gradientDrawable
        } catch (e: Exception) {
            null
        }
    }

    fun CreateGradientVerticalBackground(
        context: Context,
        startHexColor: String,
        endHexColor: String,
        cornerRadius: Float? = 0f
    ): GradientDrawable? {
       return try {
            val dpToFloat = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, cornerRadius ?: 0f,
                context.resources.displayMetrics
            )

            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    Color.parseColor(startHexColor),
                    Color.parseColor(endHexColor)
                )
            )
            gradientDrawable.cornerRadius = dpToFloat
            gradientDrawable.setGradientCenter(0.5f, 0.75f)
            gradientDrawable
        } catch (e: Exception) {
            null
        }
    }

    fun CreateShapeGradientBackground(
        context: Context,
        startHexColor: String,
        endHexColor: String,
        cornerRadius: Float?
    ): GradientDrawable? {
        return try {
            val shape = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(
                    Color.parseColor(startHexColor),
                    Color.parseColor(endHexColor)
                )
            )
            val dpToFloat = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, cornerRadius ?: 0f,
                context.resources.displayMetrics
            )
            shape.shape = GradientDrawable.RECTANGLE
            shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, dpToFloat, dpToFloat, 0f, dpToFloat)
            shape.setGradientCenter(0.5f, 0.75f)

            shape
        } catch (e: Exception) {
            null
        }
    }

}