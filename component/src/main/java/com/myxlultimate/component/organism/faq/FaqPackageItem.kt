package com.myxlultimate.component.organism.faq

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import com.myxlultimate.component.R
import android.widget.LinearLayout
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_faq_package_item.view.*

class FaqPackageItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val iconDrawable: Drawable?,
        val title: String,
        val description: String
    )

    var iconDrawable: Drawable? = null
        set(value) {
            field = value
            iconView.imageSource = value
        }

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    var description = ""
        set(value) {
            field = value
            descriptionView.text = value
        }

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(this, value)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.organism_faq_package_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FaqPackageItem)
        typedArray.run {
            iconDrawable = getDrawable(R.styleable.FaqPackageItem_iconDrawable)
            title = getString(R.styleable.FaqPackageItem_title) ?: ""
            description = getString(R.styleable.FaqPackageItem_description) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(this, onPress)
    }

}