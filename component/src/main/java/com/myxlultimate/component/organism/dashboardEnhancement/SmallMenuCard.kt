package com.myxlultimate.component.organism.dashboardEnhancement

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_small_menu_card.view.*

class SmallMenuCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    data class Data(
        val title : String,
        val backgroundImageCardUrl: String,
        val backgroundMode: BackgroundColorMode
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var backgroundImageCardUrl = ""
        set(value) {
            field = value

            backgroundImage.apply {
                imageSource = value
            }
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

    var backgroundMode = BackgroundColorMode.LIGHT
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_small_menu_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmallMenuCard)
        typedArray.run {
            title = getString(R.styleable.SmallMenuCard_title)?:""
            backgroundImageCardUrl = getString(R.styleable.SmallMenuCard_backgroundImageCardUrl)?:""
            backgroundMode = BackgroundColorMode.values()[getInt(R.styleable.SmallMenuCard_backgroundMode, 0)]
        }
        typedArray.recycle()
    }

    private fun refreshView(){
        titleView.apply {
            text = title
            setTextColor(
                ContextCompat.getColor(
                    context,
                    backgroundMode.defaultTextColor
                )
            )
        }
    }
}