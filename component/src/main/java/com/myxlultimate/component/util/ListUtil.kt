package com.myxlultimate.component.util

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object ListUtil {

    private fun getLinearItemDecoration(isHorizontal: Boolean, gap : Int): RecyclerView.ItemDecoration {
        return LinearSpacingItemDecoration(
            when (isHorizontal) {
                true -> LinearLayoutManager.HORIZONTAL
                false -> LinearLayoutManager.VERTICAL
            },
            gap
        )
    }

    fun getListGapDecorator(
        context: Context,
        gapSizeDp: Int,
        isHorizontal: Boolean = false,
        layoutManager: RecyclerView.LayoutManager? = null
    ): RecyclerView.ItemDecoration {
        val gapFloat = (gapSizeDp / 1.2).toFloat()
        val gap =
            (gapFloat * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

        return layoutManager?.let {
            when (layoutManager) {
                is GridLayoutManager -> GridSpacingItemDecoration(
                    isHorizontal,
                    gap,
                    layoutManager.spanCount
                )
                else -> getLinearItemDecoration(isHorizontal, gap)
            }
        }?: getLinearItemDecoration(isHorizontal, gap)
    }

}