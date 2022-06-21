package com.myxlultimate.component.util

import android.annotation.SuppressLint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation

object TouchFeedbackUtil {

    // Tweak this value as you see fit
    private const val DELAY_MILLIS = 200L

    private var previousClickTimeMillis = 0L

    @SuppressLint("ClickableViewAccessibility")
    fun attach(isFillAfter: Boolean = true, view: View, onClickListener: (() -> Unit)? ) {
        var opacity = AlphaAnimation(.5F, .5F)

        var rect = Rect(view.left, view.top, view.right, view.bottom)


        view.setOnTouchListener { it, motion ->
            when (motion.action) {
                MotionEvent.ACTION_DOWN -> {
                    rect = Rect(it.left, it.top, it.right, it.bottom)
                    opacity = AlphaAnimation(1F, .5F)
                }

                MotionEvent.ACTION_UP -> {
                    opacity = AlphaAnimation(.5F, 1F)
                    if (rect.contains(it.left + motion.x.toInt(), it.top + motion.y.toInt())) {
                        if (onClickListener != null) {
                            resolveMultipleClick(onClickListener)
                        }
                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    opacity = AlphaAnimation(.5F, 1F)
                }
            }

            opacity.duration = 0
            opacity.fillAfter = isFillAfter
            it.startAnimation(opacity)

            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun attach(view: View, onClickListener: (() -> Unit)?) {
        var opacity = AlphaAnimation(.5F, .5F)

        var rect = Rect(view.left, view.top, view.right, view.bottom)

        view.setOnTouchListener { it, motion ->
            when (motion.action) {
                MotionEvent.ACTION_DOWN -> {
                    rect = Rect(it.left, it.top, it.right, it.bottom)
                    opacity = AlphaAnimation(1F, .5F)
                }

                MotionEvent.ACTION_UP -> {
                    opacity = AlphaAnimation(.5F, 1F)
                    if (rect.contains(it.left + motion.x.toInt(), it.top + motion.y.toInt())) {
                        if (onClickListener != null) {
                            resolveMultipleClick(onClickListener)
                        }
                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    opacity = AlphaAnimation(.5F, 1F)
                }
            }

            opacity.duration = 0
            opacity.fillAfter = true
            it.startAnimation(opacity)

            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun detach(view: View) {
        view.setOnTouchListener { _, motion ->
            when (motion.action) {
                MotionEvent.ACTION_DOWN -> {}

                MotionEvent.ACTION_UP -> {}
            }

            true
        }
    }

    private fun resolveMultipleClick(onClickListener: (() -> Unit)) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
            previousClickTimeMillis = currentTimeMillis
            onClickListener()
        }
    }
}