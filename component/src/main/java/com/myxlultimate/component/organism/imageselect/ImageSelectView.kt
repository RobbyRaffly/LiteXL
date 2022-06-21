package com.myxlultimate.component.organism.imageselect

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_image_select.view.*

class ImageSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    var isDone = false
        set(value) {
            field = value

            reloadData()
        }

    // ----------------------------------------------------------------------------------

    var hintTop = ""
        set(value) {
            field = value
            tvHintTop.text = value
        }

    // ----------------------------------------------------------------------------------

    var textCenter = ""
        set(value) {
            field = value
            tvTextCenter.text = value
        }

    // ----------------------------------------------------------------------------------

    var textHintCenter = ""
        set(value) {
            field = value
            tvTextHintCenter.text = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(false, clView, value)
        }

    var drawableStart: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_media_camera)
        set(value) {
            field = value

            reloadData()
        }

    var drawableStartDone: Drawable? = ContextCompat.getDrawable(
        context, R.drawable.ic_success_active
    )
        set(value) {
            field = value

            reloadData()
        }

    // ----------------------------------------------------------------------------------
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_image_select, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectImageCard)
        typedArray.run {
            hintTop = getString(R.styleable.SelectImageCard_hintTop) ?: ""
            textCenter = getString(R.styleable.SelectImageCard_textCenter) ?: ""
            textHintCenter = getString(R.styleable.SelectImageCard_textHintCenter) ?: ""
            isDone = getBoolean(R.styleable.SelectImageCard_isDone, false)
            (getDrawable(R.styleable.SelectImageCard_drawableStart) ?: ContextCompat.getDrawable(
                context, R.drawable.ic_media_camera
            )).let {
                drawableStart = it
            }
            (getDrawable(R.styleable.SelectImageCard_drawableStartDone)
                ?: ContextCompat.getDrawable(
                    context, R.drawable.ic_success_active
                )).let {
                drawableStartDone = it
            }
        }
        typedArray.recycle()
    }

    private fun reloadData() {
        if (isDone) {
            tvTextHintCenter.visibility = View.GONE
            tvTextCenter.visibility = View.VISIBLE
            ivImageStatus.imageSourceType = ImageSourceType.DRAWABLE
            ivImageStatus.imageSource = drawableStartDone
        } else {
            tvTextHintCenter.visibility = View.VISIBLE
            tvTextCenter.visibility = View.GONE
            ivImageStatus.imageSourceType = ImageSourceType.DRAWABLE
            ivImageStatus.imageSource = drawableStart
        }
    }
}
