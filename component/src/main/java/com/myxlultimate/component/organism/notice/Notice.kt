package com.myxlultimate.component.organism.notice

import android.content.Context
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_notice.view.*

class Notice @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Variables
     */
    var icon :Int?=0
    set(value) {
        field = value
        if (value != 0 && value !=null) {
            NoticeElIcon.setIcon(value)
        }
    }

    var description :CharSequence
    get() = NoticeElContent.getText()
    set(value) {
        NoticeElContent.setText(value as String)
    }

    var backgroundColor : Int?=0
    set(value) {
        field = value
        if (value != 0 && value != null) {
            val backgroundStyle = getBackgroundStyle(Notice)
            backgroundStyle.setColor(value)
        }
    }

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_notice, this, true)

        context.theme.obtainStyledAttributes(attrs, R.styleable.noticeAttr, 0, 0).apply{
            try {
                getString(R.styleable.noticeAttr_description)?.let {
                    description = it
                }
                getResourceId(R.styleable.noticeAttr_iconCode, 0)?.let {
                    icon = it
                }
                getColor(R.styleable.noticeAttr_color, 0)?.let {
                    backgroundColor = it
                }
            } finally {
                recycle()
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    private fun getBackgroundStyle(view: LinearLayout): GradientDrawable {
        val drawableContainerState =
            view.background.constantState as DrawableContainer.DrawableContainerState

        return drawableContainerState.children[0] as GradientDrawable
    }

}