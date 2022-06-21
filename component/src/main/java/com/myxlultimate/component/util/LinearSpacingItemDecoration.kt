package com.myxlultimate.component.util

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingItemDecoration(
    private val orientation: Int = LinearLayoutManager.VERTICAL,
    private val gap: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.apply {
                if (parent.getChildAdapterPosition(view) > 0) {
                    left = gap
                }
            }
        } else {
            outRect.apply {
                if (parent.getChildAdapterPosition(view) > 0) {
                    top = gap
                }
            }
        }
    }
}