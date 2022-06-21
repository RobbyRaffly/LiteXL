package com.myxlultimate.component.organism.concentTickCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_consent_tick_card.view.*

class ConsentTickCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE

    // ----------------------------------------------------------------------------------

    var imageSource: Any? = getDrawable(context, R.drawable.ic_yes_consent_xl_satu)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    var isActive = false
        set(value) {
            field = value
            refreshView()
            onChange?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_consent_tick_card, this, true)

        this.background = getDrawable(context, R.drawable.consent_untick_background)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ConsentTickCard)
        typedArray.run {
            title = typedArray.getString(R.styleable.ConsentTickCard_title) ?: ""
            isActive = getBoolean(R.styleable.ConsentTickCard_isActive, false)
            imageSource = getDrawable(R.styleable.ConsentTickCard_imageSource)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(this) {
            if (!isActive) {
                isActive = true
            }
        }
    }

    private fun refreshView() {
        titleView.text = title
        imageView.imageSourceType = imageSourceType
        imageView.imageSource = imageSource
        checkView.visibility = if (isActive) View.VISIBLE else View.GONE
        borderContainerView.setBackgroundResource(if (isActive) R.drawable.consent_tick_background else R.drawable.consent_untick_background)

    }

}