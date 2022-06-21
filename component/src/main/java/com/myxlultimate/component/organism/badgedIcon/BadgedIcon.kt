package com.myxlultimate.component.organism.badgedIcon

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_badged_icon.view.*

class BadgedIcon @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Mode and Style
     */
    private var mode = "LIGHT"
    private var size = 24

    /**
     * On Click Listener
     */
    private var onClickListener: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_badged_icon, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.badgedIconAttr)

        attrs.let {
            setMode(typedArray.getString(R.styleable.badgedIconAttr_mode))
            setCounter(typedArray.getInteger(R.styleable.badgedIconAttr_counter, 0))
            setIcon(typedArray.getResourceId(R.styleable.badgedIconAttr_iconCode, 0))
            setIconSize(typedArray.getInteger(R.styleable.badgedIconAttr_size, size))
            setColor(typedArray.getResourceId(R.styleable.badgedIconAttr_color, 0))
            setGapSize(typedArray.getInteger(R.styleable.badgedIconAttr_gapSize,0))
        }

        typedArray.recycle()

        setOnActionClick(onClickListener)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------

    /**
     * Mode and Style Setter
     */
    fun setMode(_mode: String?) {
        if (_mode != null) {
            mode = _mode
        }
        BadgedIconIcolEl.setMode(mode)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Set On CLick Listener
     */
    fun setOnActionClick(_onClickListener: (() -> Unit)?) {
        onClickListener = _onClickListener
        TouchFeedbackUtil.attach(BadgedIconIcolEl, onClickListener)
    }

    // ----------------------------------------------------------------------------------

    /**
     * Set Badged Icon Properties
     */
    // ----------------------------------------------------------------------------------

    fun setCounter(counter: Int) {
        BadgedIconNotificationTitleEl.setText(counter.toString())
        BadgedIconNotificationEl.visibility = if (counter == 0) View.GONE else View.VISIBLE
        if(counter > 99){

            var layoutParamsRatio = BadgedIconNotificationEl.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsRatio.width = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParamsRatio.height = 0
            BadgedIconNotificationEl.layoutParams = layoutParamsRatio
        }
    }

    fun setIcon(icon: Int) {
        BadgedIconIcolEl.setIcon(icon)
    }

    fun setIconSize(iconSize: Int) {
        BadgedIconIcolEl.setSize(iconSize)
    }

    fun setGapSize(gapSize: Int) {
        val scale = resources.displayMetrics.density
        val padding_in_px = (gapSize * scale + 0.5f).toInt()
        BadgedIconIcolEl.setPadding(0, padding_in_px, padding_in_px, 0)
    }

    fun setColor(color: Int) {
        BadgedIconIcolEl.setColor(color)
    }

}