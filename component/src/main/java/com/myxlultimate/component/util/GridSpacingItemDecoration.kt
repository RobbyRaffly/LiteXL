package com.myxlultimate.component.util

import android.graphics.Rect
import android.provider.MediaStore
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val isHorizontal: Boolean = false,
    private val gap: Int = 0,
    private var spanCount: Int = 1
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (spanCount < 1)
            spanCount = 1

        val childPositionInList = parent.getChildAdapterPosition(view)
        val latestListIndex = (parent.adapter?.itemCount?.minus(1))?: 0

        val mostLeftColumnIndex = 0
        val mostRightColumnIndex = spanCount - 1
        val mostTopRowIndex = 0
        val mostBottomRowIndex = latestListIndex / spanCount

        val childRowPosition = childPositionInList / spanCount
        val childColumnPosition = childPositionInList % spanCount

        val isItemAtMostLeftColumn = childColumnPosition == mostLeftColumnIndex
        val isItemAtMostRightColumn = childColumnPosition == mostRightColumnIndex
        val isItemAtMostTopRow = childRowPosition == mostTopRowIndex
        val isItemAtMostBottomRow = childRowPosition == mostBottomRowIndex

        outRect.apply {
            if (isHorizontal) {
                when {
                    isItemAtMostLeftColumn -> right = gap
                    isItemAtMostRightColumn -> left = gap
                    else -> { // in between
                        right = gap
                        left = gap
                    }
                }
            } else {
                when {
                    isItemAtMostTopRow -> bottom = gap
                    isItemAtMostBottomRow -> top = gap
                    else -> {  // in between
                        top = gap
                        bottom = gap
                    }
                }
            }
        }
    }
}