package com.myxlultimate.component.template.indexScrollView

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.myxlultimate.component.R
import com.myxlultimate.component.util.setTextViewAppearance

class IndexScrollView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttribute: Int = 0
) : LinearLayout(context, attr, defStyleAttribute) {

    private var mapIndex: MutableMap<String, Int>? = null
    private var onIndexTouch: ((text: String) -> Unit)? = null
    private val sInvertMatrix = Matrix()
    private val sPoint = FloatArray(2)
    private var previousY: Float = 0f

    var alphabet: CharArray? = null
        set(value) {
            getIndexList(value)
            field = value
        }

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        isFocusable = true
        isFocusableInTouchMode = true

        setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                    if (findChildByPosition(this, motionEvent.x, motionEvent.y) != null) {
                        onIndexTouch?.invoke(
                            ((findChildByPosition(
                                this,
                                motionEvent.x,
                                motionEvent.y
                            )) as TextView).apply {
                                this.setTextViewAppearance(R.style.IndexScrollerSelectedIndex)
                            }.text.toString()
                        )
                    }
                }

                MotionEvent.ACTION_UP -> {
                    resetAllIndices(this)
                }
            }
            true
        }
    }

    private fun resetAllIndices(parent: ViewGroup) {
        var child: TextView
        parent.children.forEach { view ->
            child = view as TextView
            child.apply {
                this.setTextViewAppearance(R.style.IconAppearance_MudComponents_Body1)
                this.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
            }
        }
    }

    fun setOnIndexTouchListener(onIndexTouch: (text: String) -> Unit) {
        this.onIndexTouch = onIndexTouch
    }

    private fun getIndexList(alphabet: CharArray?) {
        mapIndex = LinkedHashMap()
        if (alphabet != null) {
            for (i in alphabet.indices) {
                if ((mapIndex as LinkedHashMap<String, Int>)[alphabet[i].toString()] == null) {
                    (mapIndex as LinkedHashMap<String, Int>)[alphabet[i].toString()] = i
                }
            }
        }
        displayIndex()
    }

    private fun displayIndex() {
        var textView: TextView
        val indexList = mapIndex?.keys?.let { ArrayList(it) }
        if (indexList != null) {
            for (index in indexList) {
                textView =
                    LayoutInflater.from(context)
                        .inflate(R.layout.side_index_item, null, false) as TextView

                textView.apply {
                    gravity = Gravity.CENTER
                    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f)
                    text = index
                    this.setTextViewAppearance(R.style.IconAppearance_MudComponents_Body1)
                    this.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
                }
                addView(textView)
            }
        }
    }

    private fun findChildByPosition(parent: ViewGroup, x: Float, y: Float): View? {
        val count = parent.childCount
        var child: TextView
        if (previousY < y) {
            for (i in 0 until count - 1) {
                child = parent.getChildAt(i) as TextView
                child.apply {
                    this.setTextViewAppearance(R.style.IconAppearance_MudComponents_Body1)
                    this.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
                }
                if (child.visibility == View.VISIBLE) {
                    previousY = y
                    if (isPositionInChildView(parent, child, x, y)) return child
                }
            }
        } else {
            for (i in count - 1 downTo 0) {
                child = parent.getChildAt(i) as TextView
                child.apply {
                    this.setTextViewAppearance(R.style.IconAppearance_MudComponents_Body1)
                    this.setTextColor(ContextCompat.getColor(context, R.color.mud_palette_basic_dark_grey))
                }
                if (child.visibility == View.VISIBLE) {
                    previousY = y
                    if (isPositionInChildView(parent, child, x, y)) return child
                }
            }
        }
        return null
    }


    private fun isPositionInChildView(parent: ViewGroup, child: View, x: Float, y: Float): Boolean {
        var xPosition = x
        var yPosition = y

        sPoint[0] = xPosition + parent.scrollX - child.left
        sPoint[1] = yPosition + parent.scrollY - child.top

        val childMatrix = child.matrix
        if (!childMatrix.isIdentity) {
            childMatrix.invert(sInvertMatrix)
            sInvertMatrix.mapPoints(sPoint)
        }

        xPosition = sPoint[0]
        yPosition = sPoint[1]

        return xPosition >= 0 && yPosition >= 0 && xPosition < child.width && yPosition < child.height
    }
}