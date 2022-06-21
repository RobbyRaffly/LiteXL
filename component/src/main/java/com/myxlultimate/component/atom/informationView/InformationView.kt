package com.myxlultimate.component.atom.informationView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.atom_information_view.view.*

class InformationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            tvWarning.text = value
        }

    var imageWarning: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_icon_system_warning)
        set(value) {
            field = value

            value?.let {
                ivWarning.setImageDrawable(it)
            }
        }

    var colorTextError = false
        set(value) {
            field = value
            setTextColor()
        }

    var isColorWhite = false
        set(value) {
            field = value
            setTextColor()
        }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.atom_information_view, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InformationView)
        typedArray.run {
            title = getString(R.styleable.InformationView_title) ?: ""
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun setTextColor() {
        if (colorTextError) {
            tvWarning.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.mud_palette_basic_red
                )
            )
        } else if (isColorWhite) {
            tvWarning.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.mud_palette_basic_white
                )
            )
        } else {
            tvWarning.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.mud_palette_basic_dark_grey
                )
            )
        }
    }
}