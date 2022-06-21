package com.myxlultimate.component.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.text.style.StrikethroughSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.myxlultimate.component.R


fun TextView.setStrikeThroughSpannable(charSequence: CharSequence, start: Int, end: Int) {
    val ssBuilder = SpannableStringBuilder(charSequence)
    val strikeThroughSpan = StrikethroughSpan()
    charSequence.let {
        ssBuilder.setSpan(
            strikeThroughSpan,
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.text = ssBuilder
}

fun TextView.parseHybridBetaTagSpannable(
    string: String,
    colorResId: Int = R.color.mud_palette_prio_gold,
    smallSpan: Boolean = true
) {
    if (string.contains('*')) {
        val builder = SpannableStringBuilder(string.replace("*", ""))
        val sequences = string.split('*')
        var start = 0
        sequences.forEachIndexed { index, s ->
            if (index % 2 == 1) {
                start = sequences[index - 1].length
                val color = ContextCompat.getColor(context, colorResId)
                val span = RoundedBackgroundSpan(
                    context,
                    color,
                    Color.WHITE,
                    ConverterUtil.spToPx(context, if(smallSpan) 6f else 11f),
                    2f
                )
                builder.setSpan(
                    span,
                    start,
                    start + s.length,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }

        //TODO: remove tag
        builder.replace(Regex("/\\*"), "")
        setText(builder, TextView.BufferType.SPANNABLE)
    }  else if (string.contains("^")) {
        val builder = SpannableStringBuilder(string.replace("^", ""))
        val sequences = string.split('^')
        var start: Int
        sequences.forEachIndexed { index, s ->
            if (index % 2 == 1) {
                start = sequences[index - 1].length
                val view = LayoutInflater.from(context).inflate(R.layout.molecule_spannable_beta_badge, null)
                val span = ImageSpan(createBitmapFromView(context ,view))
                builder.setSpan(
                    span,
                    start,
                    start + s.length,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }
        text = builder
    } else {
        text = string
    }
}

fun createBitmapFromView(context : Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(view.measuredWidth, 100, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }
