package com.myxlultimate.component.molecule.unsubscribePackageReason

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_unsubscribe_package_reason_item.view.*

class UnsubscribePackageReasonItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val BACKGROUND = context.getDrawable(R.drawable.circle_background)
    private val ACTIVE_BACKGROUND = context.getDrawable(R.drawable.bordered_circle_background)

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        val label: String,
        val iconImage: String,
        var isActive: Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value

            LabelView.text = value
        }

    // ----------------------------------------------------------------------------------

    var iconImage = ""
        set(value) {
            field = value

            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var deactivatable = false

    // ----------------------------------------------------------------------------------

    var isActive = false
        set(value) {
            if (deactivatable && field && !value) return

            field = value

            iconContainerView.background = if (value) ACTIVE_BACKGROUND else BACKGROUND

            onChange?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_unsubscribe_package_reason_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StopPackageReasonItemAttr)
        typedArray.run {
            label = getString(R.styleable.StopPackageReasonItemAttr_label)?: ""
            iconImage = getString(R.styleable.StopPackageReasonItemAttr_iconImage)?:""
            isActive = getBoolean(R.styleable.StopPackageReasonItemAttr_isActive, false)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView) { toggle() }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    fun toggle() {
        isActive = !isActive
    }
}