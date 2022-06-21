package com.myxlultimate.component.organism.welcomeHeaderGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.BottomTitleMode
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_welcome_header_toolbar.view.*

class WelcomeHeaderToolbar @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * On Click Listener
     */
    var onBackButtonClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(iconView, value)
        }

    var onBottomTitleClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(bottomTitleView, value)
            TouchFeedbackUtil.attach(bottomTitleInfoView, value)
        }

    var onRightArrowClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(titleViewLayout,value)
        }

    /**
     * Variables
     */
    var topTitle = ""
        set(value) {
            field = value
            topTitleView.text = value
            IsEmptyUtil.setVisibility(value, topTitleView)
        }

    var bottomTitle = ""
        set(value) {
            field = value
            setBottomTitle()
        }

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    var isShimmerOn = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.apply {
                    startShimmer()
                    visibility = View.VISIBLE
                }
            } else {
                shimmerLayout.apply {
                    stopShimmer()
                    visibility = View.GONE
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var image = 0
        set(value) {
            field = value
            if (image != 0) {
                titleView.setBackgroundColor(getColor(context, R.color.transparent))
                imageView.visibility = View.VISIBLE
                imageView.imageSourceType = ImageSourceType.DRAWABLE
                imageView.imageSource = getDrawable(context, value)
            } else {
                imageView.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------

    var hasRightArrow = false
        set(value) {
            field = value
            arrowRightView.visibility = if (value) View.VISIBLE else View.GONE
            if(value) {
                bottomTitleView.setCompoundDrawables(null,null,null,null)
            }
        }
    // ----------------------------------------------------------------------------------

    var icon = resources.getString(R.string.xl_left_arrow)
        set(value) {
            field = value
            iconView.text = value
        }

    // ----------------------------------------------------------------------------------

    var headerBackgroundColor = R.color.mud_palette_basic_white
        set(value) {
            field = value
            appbarView.setBackgroundColor(getColor(context, value))
            iconView.setBackgroundColor(getColor(context, value))
            titleView.setBackgroundColor(getColor(context, value))
            if (image > 0) {
                titleView.setBackgroundColor(getColor(context, R.color.transparent))
            }
        }

    // ----------------------------------------------------------------------------------

    var headerTextColor = R.color.mud_palette_basic_black
        set(value) {
            field = value
            titleView.setTextColor(getColor(context, value))
            iconView.setTextColor(getColor(context, value))
        }

    // ----------------------------------------------------------------------------------

    var headerTopTitleColor = R.color.mud_palette_basic_white
        set(value) {
            field = value
            topTitleView.setTextColor(getColor(context, value))
        }

    // ----------------------------------------------------------------------------------

    var headerBottomTitleColor = R.color.mud_palette_prio_gold
        set(value) {
            field = value
            bottomTitleView.setTextColor(getColor(context, value))
            bottomTitleInfoView.setTextColor(getColor(context, value))
        }

    var bottomTitleMode: BottomTitleMode = BottomTitleMode.ACTION
        set(value) {
            field = value
            setBottomTitle()
        }
    // ----------------------------------------------------------------------------------

    var bottomPadding: Int = 0
        set(value) {
            field = value
            appbarView.setPadding(
                appbarView.paddingLeft,
                appbarView.paddingTop,
                paddingRight,
                value
            )
        }
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_welcome_header_toolbar, this, true)

        context.theme.obtainStyledAttributes(attrs, R.styleable.welcomeHeaderAttr, 0, 0).apply {
            try {
                getString(R.styleable.welcomeHeaderAttr_title)?.let {
                    title = it
                }
                getString(R.styleable.welcomeHeaderAttr_topTitle)?.let {
                    topTitle = it
                }
                getString(R.styleable.welcomeHeaderAttr_bottomTitle)?.let {
                    bottomTitle = it
                }
                getResourceId(
                    R.styleable.welcomeHeaderAttr_backgroundColor,
                    R.color.mud_palette_basic_white
                )?.let {
                    headerBackgroundColor = it
                }
                getResourceId(
                    R.styleable.welcomeHeaderAttr_textColor,
                    R.color.mud_palette_basic_black
                )?.let {
                    headerTextColor = it
                }
                getResourceId(
                    R.styleable.welcomeHeaderAttr_topTitleColor,
                    R.color.mud_palette_basic_white
                )?.let {
                    headerTopTitleColor = it
                }
                getResourceId(
                    R.styleable.welcomeHeaderAttr_bottomTitleColor,
                    R.color.mud_palette_prio_gold
                )?.let {
                    headerBottomTitleColor = it
                }
                getString(R.styleable.welcomeHeaderAttr_iconCode)?.let {
                    icon = it
                }
                getResourceId(R.styleable.welcomeHeaderAttr_imageSource, 0).let {
                    image = it
                }
                getInt(R.styleable.welcomeHeaderAttr_bottomTitleMode, 0).let {
                    bottomTitleMode = BottomTitleMode.values()[it]
                }
                getDimensionPixelSize(R.styleable.welcomeHeaderAttr_paddingBottom, 0).let {
                    bottomPadding = it
                }
                getBoolean(R.styleable.welcomeHeaderAttr_isShimmerOn, false).let {
                    isShimmerOn = it
                }
                getBoolean(R.styleable.welcomeHeaderAttr_hasRightArrow,false).let {
                    hasRightArrow = it
                }
            } finally {
                recycle()
            }
        }
    }

    private fun setBottomTitle() {
        bottomTitleView.visibility = GONE
        bottomTitleInfoView.visibility = GONE
        var currBottomTitleView: TextView = if (bottomTitleMode == BottomTitleMode.INFO)
            bottomTitleInfoView
        else
            bottomTitleView

        currBottomTitleView.text = bottomTitle
        IsEmptyUtil.setVisibility(bottomTitle, currBottomTitleView)
    }

}