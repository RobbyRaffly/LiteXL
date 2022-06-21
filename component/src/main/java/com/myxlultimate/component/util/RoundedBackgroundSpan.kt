package com.myxlultimate.component.util

import android.content.Context
import android.graphics.Paint

import android.graphics.RectF

import android.graphics.Canvas
import android.graphics.Paint.FontMetricsInt

import android.text.style.ReplacementSpan
import android.util.Log

class RoundedBackgroundSpan
/**
 * @param backgroundColor color value, not res id
 * @param textSize        in pixels
 */(private val context: Context, private val mBackgroundColor: Int, private val mTextColor: Int, private val mTextSize: Float, private val textHeightWrappingDp:Float = 4f) :
    ReplacementSpan() {
    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        var paint = paint
        paint = Paint(paint) // make a copy for not editing the referenced paint
        paint.textSize = mTextSize

        // Draw the rounded background
        paint.color = mBackgroundColor
        val textHeightWrapping: Float = ConverterUtil.dpToPx(context, textHeightWrappingDp)
        val tagBottom =
            top + textHeightWrapping + getPaddingY(context) + mTextSize + getPaddingY(context) + textHeightWrapping
        val tagRight = x + getTagWidth(text, start, end, paint)
        val rect = RectF(x, top.toFloat(), tagRight, tagBottom)
        canvas.drawRoundRect(rect, getRadius(context), getRadius(context), paint)

        // Draw the text
        paint.color = mTextColor
        canvas.drawText(
            text,
            start,
            end,
            x + getPaddingX(context),
            tagBottom - getPaddingY(context) - textHeightWrapping - getMagicNumber(context),
            paint
        )
    }

    private fun getTagWidth(text: CharSequence, start: Int, end: Int, paint: Paint): Int {
        return Math.round(
            getPaddingX(context) + paint.measureText(
                text.subSequence(start, end).toString()
            ) + getPaddingX(context)
        )
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        var paint = paint
        paint = Paint(paint) // make a copy for not editing the referenced paint
        paint.textSize = mTextSize
        return getTagWidth(text, start, end, paint)
    }

    private fun getPaddingX(context: Context) = ConverterUtil.dpToPx(context, 10f)
    private fun getPaddingY(context: Context) = ConverterUtil.dpToPx(context, 0f)
    private fun getMagicNumber(context: Context) = ConverterUtil.dpToPx(context, 2f)
    private fun getRadius(context: Context) = ConverterUtil.dpToPx(context, 10f)

}