package com.myxlultimate.component.molecule.settingRow

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_setting_row.view.*

class SettingRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val ribbonLabel: String? = "",
        val iconCode: String,
        val iconDrawable: Any? = null,
        val hasBottomLine: Boolean = true,
        val redDotCount: Int = 0
    )

    var title = ""
        set(value) {
            field = value

            titleView.text = value
        }

    // ---------------------------------------------------------------------------------

    var ribbonLabel = ""
        set(value) {
            field = value
            if(value.isNotEmpty()) {
                ribbonLayoutView.visibility = View.VISIBLE
                ribbonLabelView.text = value
            } else {
                ribbonLayoutView.visibility = View.GONE
            }
        }
    // ---------------------------------------------------------------------------------

    var iconCode = ""
        set(value) {
            field = value

            textIconView.text = value
            IsEmptyUtil.setVisibility(value, textIconView)
        }

    // ----------------------------------------------------------------------------------

    var iconDrawable: Any? = null
        set(value) {
            field = value

            drawableIconView.imageSource = value
            IsEmptyUtil.setVisibility(value, drawableIconView)
        }

    // ----------------------------------------------------------------------------------

    var redDotCount: Int = 0
        set(value) {
            field = value
            if (value > 0) {
                redDotCountLayout.visibility = View.VISIBLE
                tvCount.text = value.toString()
            }
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var hasBottomLine = true
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, bottomLine)
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_setting_row, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingRow)
        typedArray.run {
            title = getString(R.styleable.SettingRow_title) ?: ""
            ribbonLabel = getString(R.styleable.SettingRow_ribbonLabel) ?:""
            iconCode = getString(R.styleable.SettingRow_iconCode) ?: ""
            iconDrawable = getDrawable(R.styleable.SettingRow_iconDrawable)
            hasBottomLine = getBoolean(R.styleable.SettingRow_hasBottomLine, true)
            redDotCount = getInt(R.styleable.SettingRow_redDotCount, 0)
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView, onPress)
    }
}