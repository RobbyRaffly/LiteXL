package com.myxlultimate.component.molecule.packageBenefit

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.MoleculeCardImageGridItemBinding
import com.myxlultimate.component.token.imageView.ImageSourceType

class CardImageGridItem @JvmOverloads constructor(
    context: Context,
    attribute: AttributeSet? = null
) : ConstraintLayout(context, attribute) {

    var binding: MoleculeCardImageGridItemBinding? = null

    var isMoreView: Boolean = false
        set(value) {
            binding?.apply {
                field = value
                moreView.isVisible = value
                imageView.isVisible = !value
            }
        }

    var imageSource: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty())
                binding?.imageView?.imageSource = imageSource
        }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding = MoleculeCardImageGridItemBinding.inflate(LayoutInflater.from(context), this, true)
        binding?.imageView?.imageSourceType = ImageSourceType.URL
    }

    class GridItemDecorator: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position == RecyclerView.NO_POSITION) return

            val s8dp = view.context.resources.getDimension(R.dimen.mud_dimens_8dp).toInt()
            setDefaultPadding(outRect, s8dp)

            when {
                isOnRight(position) -> outRect.left = s8dp
                isOnLeft(position) -> outRect.right = s8dp
            }
        }

        private fun setDefaultPadding(outRect: Rect, s8dp: Int) {
            outRect.run {
                left = 0
                top = 0
                right = 0
                bottom = s8dp
            }
        }

        private fun isOnLeft(position: Int) = position % 2 == 0

        private fun isOnRight(position: Int) = position % 2 == 1
    }
}
