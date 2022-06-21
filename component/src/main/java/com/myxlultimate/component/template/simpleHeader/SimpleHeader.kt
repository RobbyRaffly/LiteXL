package com.myxlultimate.component.template.simpleHeader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_simple_header_toolbar.view.*

class SimpleHeader @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.Theme_AppCompat_DayNight_DarkActionBar
) : LinearLayout(context, attrs, defStyleAttr) {

    var onBackButtonClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(iconView, value)
        }

    // ----------------------------------------------------------------------------------


    var onEndTitleClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(endTitleView, value)
            TouchFeedbackUtil.attach(endBubbleView, value)

        }

    // ----------------------------------------------------------------------------------

    var onEndIconClickLinearLayout: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(endIconImageView, value)
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var endTitle = ""
        set(value) {
            field = value
            endTitleView.text = value
            IsEmptyUtil.setVisibility(value,endTitleView)
        }

    // ----------------------------------------------------------------------------------

    var icon = resources.getString(R.string.xl_left_arrow)
        set(value) {
            field = value
            iconView.text = value
        }

    // ----------------------------------------------------------------------------------

    var endIconImage = R.drawable.ic_xl_logo
    set(value) {
        field = value
        endIconImageView.imageSource = getDrawable(context,value)
    }

    // ----------------------------------------------------------------------------------

    var isEndIconVisible = false
    set(value) {
        field = value
        IsEmptyUtil.setVisibility(value,endIconImageView)
    }

    // ----------------------------------------------------------------------------------


    var headerBackgroundColor = R.color.mud_palette_basic_white
        set(value) {
            field = value
            appbarView.setBackgroundColor(ContextCompat.getColor(context, value))
            iconView.setBackgroundColor(ContextCompat.getColor(context, value))
            titleView.setBackgroundColor(ContextCompat.getColor(context, value))
        }

    // ----------------------------------------------------------------------------------


    var headerTextColor = R.color.mud_palette_basic_black
        set(value) {
            field = value
            titleView.setTextColor(ContextCompat.getColor(context, value))
            iconView.setTextColor(ContextCompat.getColor(context, value))
            refreshView()
        }


    var endTextColor = 0
        set(value) {
            field = value
            refreshView()
        }

    var endBubbleBackgroundColor = 0
        set(value) {
            field = value
            refreshView()
        }

    var endBubbleText = ""
        set(value) {
            field = value
            refreshView()
        }

    var hasEndBubble = false
        set(value) {
            field = value
            refreshView()
        }

    private fun refreshView() {
        endBubbleView.apply {
            visibility = if (hasEndBubble) View.VISIBLE else View.GONE
            text = endBubbleText
        }
        endTitleView.setTextColor(
            ContextCompat.getColor(
                context,
                if (endTextColor != 0) endTextColor else headerTextColor
            )
        )
        endBubbleView.backgroundTintList = ContextCompat.getColorStateList(
            context,
            if (endBubbleBackgroundColor != 0) endBubbleBackgroundColor else headerTextColor
        )
    }

    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_simple_header_toolbar, this, true)

        context.theme.obtainStyledAttributes(attrs, R.styleable.simpleHeaderAttr, 0, 0).apply {
            try {
                getString(R.styleable.simpleHeaderAttr_title)?.let {
                    title = it
                }
                getResourceId(
                    R.styleable.simpleHeaderAttr_backgroundColor,
                    R.color.mud_palette_basic_white
                )?.let {
                    headerBackgroundColor = it
                }
                getResourceId(
                    R.styleable.simpleHeaderAttr_textColor,
                    R.color.mud_palette_basic_black
                )?.let {
                    headerTextColor = it
                }
                getResourceId(R.styleable.simpleHeaderAttr_endTextColor, 0)?.let {
                    endTextColor = it
                }
                getResourceId(R.styleable.simpleHeaderAttr_endBubbleBackgroundColor, 0)?.let {
                    endBubbleBackgroundColor = it
                }
                getString(R.styleable.simpleHeaderAttr_iconCode)?.let {
                    icon = it
                }
                getString(R.styleable.simpleHeaderAttr_endTitle)?.let {
                    endTitle = it
                }
                getString(R.styleable.simpleHeaderAttr_endBubbleText)?.let {
                    endBubbleText = it
                }
                getBoolean(R.styleable.simpleHeaderAttr_hasEndBubble, false).let {
                    hasEndBubble = it
                }
                getResourceId(R.styleable.simpleHeaderAttr_endIconDrawable,R.drawable.ic_xl_logo).let {
                    endIconImage = it
                }
                getBoolean(R.styleable.simpleHeaderAttr_endIconVisible,false).let {
                    isEndIconVisible = it
                }
            } finally {
                recycle()
            }
        }
    }
}