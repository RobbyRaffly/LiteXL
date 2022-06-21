package com.myxlultimate.component.token.imageViewZoom

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.token.imageView.ImageView
import kotlin.math.abs

class ImageViewZoom(context: Context, attrs: AttributeSet? = null) :
    ImageView(context, attrs) {

    companion object {
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 10
        const val CLICK = 3
    }

    //   For Image Zoom
    var setMatrix: Matrix? = null
    var mode = NONE

    var end = PointF()
    var first = PointF()
    var minScale = 1f
    var maxScale = 3f
    private lateinit var fArray: FloatArray
    private var viewWidth = 0
    private var viewHeight = 0
    private var saveScale = 1f
    private var originWidth = 0f
    private var originHeight = 0f
    private var oldMeasuredWidth = 0
    private var oldMeasuredHeight = 0
    private var mScaleDetector: ScaleGestureDetector? = null

    init {
        implementingZoomFeature(context)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageViewAttr)
        typedArray.run {
            imageSourceType =
                ImageSourceType.values()[getInt(R.styleable.CustomImageViewAttr_imageSourceType, 0)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.CustomImageViewAttr_imageSource)
            } else {
                getString(R.styleable.CustomImageViewAttr_imageSource)
            }
        }
        typedArray.recycle()
    }

    private fun implementingZoomFeature(context: Context) {
        super.setClickable(true)
        mScaleDetector = ScaleGestureDetector(context, scaleListener())
        setMatrix = Matrix()
        fArray = FloatArray(9)
        imageMatrix = setMatrix
        scaleType = ScaleType.MATRIX

        setOnTouchListener { _, event ->
            mScaleDetector!!.onTouchEvent(event)
            val curr = PointF(event.x, event.y)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    end.set(curr)
                    first.set(end)
                    mode = DRAG
                }
                MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                    val deltaX = curr.x - end.x
                    val deltaY = curr.y - end.y
                    val fixTransX =
                        getFixDragTrans(
                            deltaX, viewWidth.toFloat(),
                            originWidth * saveScale
                        )
                    val fixTransY =
                        getFixDragTrans(
                            deltaY, viewHeight.toFloat(),
                            originHeight * saveScale
                        )
                    setMatrix!!.postTranslate(fixTransX, fixTransY)
                    fixTrans()
                    end[curr.x] = curr.y
                }
                MotionEvent.ACTION_UP -> {
                    mode = NONE
                    val xDiff = abs(curr.x - first.x).toInt()
                    val yDiff = abs(curr.y - first.y).toInt()
                    if (xDiff < CLICK && yDiff < CLICK) performClick()
                }
                MotionEvent.ACTION_POINTER_UP -> mode = NONE
            }
            imageMatrix = setMatrix
            invalidate()
            true // indicate event was handled
        }
    }


    fun fixTrans() {
        setMatrix!!.getValues(fArray)
        val transX = fArray[Matrix.MTRANS_X]
        val transY = fArray[Matrix.MTRANS_Y]
        val fixTransX = getFixTrans(
            transX, viewWidth.toFloat(),
            originWidth * saveScale
        )
        val fixTransY = getFixTrans(
            transY, viewHeight.toFloat(), originHeight * saveScale
        )
        if (fixTransX != 0f || fixTransY != 0f) setMatrix!!.postTranslate(fixTransX, fixTransY)
    }

    private fun getFixTrans(trans: Float, viewSize: Float, contentSize: Float): Float {
        val minTrans: Float
        val maxTrans: Float
        if (contentSize <= viewSize) {
            minTrans = 0f
            maxTrans = viewSize - contentSize
        } else {
            minTrans = viewSize - contentSize
            maxTrans = 0f
        }
        if (trans < minTrans) return -trans + minTrans
        if (trans > maxTrans) return -trans + maxTrans
        return 0f
    }

    private fun getFixDragTrans(delta: Float, viewSize: Float, contentSize: Float): Float {
        return if (contentSize <= viewSize) {
            0f
        } else {
            delta
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        //
        // Rescales image on rotation
        //
        if (oldMeasuredHeight == viewWidth && oldMeasuredHeight
            == viewHeight || viewWidth == 0 || viewHeight == 0
        ) return
        oldMeasuredHeight = viewHeight
        oldMeasuredWidth = viewWidth
        if (saveScale == 1f) {
            //Fit to screen.
            val scale: Float
            val drawable = drawable
            if (drawable == null || drawable.intrinsicWidth == 0 ||
                drawable.intrinsicHeight == 0
            ) return
            val bmWidth = drawable.intrinsicWidth
            val bmHeight = drawable.intrinsicHeight
            Log.d("bmSize", "bmWidth: $bmWidth bmHeight : $bmHeight")
            val scaleX = viewWidth.toFloat() / bmWidth.toFloat()
            val scaleY = viewHeight.toFloat() / bmHeight.toFloat()
            scale = scaleX.coerceAtMost(scaleY)
            setMatrix!!.setScale(scale, scale)
            // Center the image
            var redundantYSpace = viewHeight.toFloat() - scale * bmHeight.toFloat()
            var redundantXSpace = viewWidth.toFloat() - scale * bmWidth.toFloat()
            redundantYSpace /= 2.toFloat()
            redundantXSpace /= 2.toFloat()
            setMatrix!!.postTranslate(redundantXSpace, 0f)
            originWidth = viewWidth - 2 * redundantXSpace
            originHeight = viewHeight.toFloat()
            imageMatrix = setMatrix
        }
        fixTrans()
    }

    fun setMaxZoom(x: Float) {
        maxScale = x
    }


    private fun scaleListener() = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            mode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            var mScaleFactor = detector!!.scaleFactor
            val origScale = saveScale
            saveScale *= mScaleFactor
            if (saveScale > maxScale) {
                saveScale = maxScale
                mScaleFactor = maxScale / origScale
            } else if (saveScale < minScale) {
                saveScale = minScale
                mScaleFactor = minScale / origScale
            }
            if (originWidth * saveScale <= viewWidth || originHeight * saveScale <= viewHeight) setMatrix!!.postScale(
                mScaleFactor,
                mScaleFactor,
                viewWidth / 2.toFloat(),
                viewHeight / 2.toFloat()
            ) else setMatrix!!.postScale(
                mScaleFactor,
                mScaleFactor,
                detector.focusX,
                detector.focusY
            )
            fixTrans()
            return true
        }
    }

}