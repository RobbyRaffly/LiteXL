package com.myxlultimate.component.template.halfModal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.template_half_modal_header.view.*

class HalfModalHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var headerMode = HalfModalHeaderMode.NORMAL
        set(value) {
            field = value

            headerLayout.isVisible = value == HalfModalHeaderMode.BOOSTER
            headerView.isVisible = value != HalfModalHeaderMode.BOOSTER
        }

    // ----------------------------------------------------------------------------------


    var headerTitle = ""
        set(value) {
            field = value
            headerTitleView.text = value
            nameView.text = value
        }

    // ----------------------------------------------------------------------------------

    var headerInformation = ""
        set(value) {
            field = value
            updateView()
        }

    // ----------------------------------------------------------------------------------

    var headerImageIcon = ""
        set(value) {
            field = value
            updateView()
        }

    // ----------------------------------------------------------------------------------

    var headerIcon = resources.getString(R.string.xl_close)
        set(value) {
            field = value
            headerIconView.text = value
        }

    // ----------------------------------------------------------------------------------

    var onIconButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(headerIconView, value)
        }

    // ----------------------------------------------------------------------------------

    var isHeaderRightIconVisible: Boolean = false
        set(value) {
            field = value
            updateView()
        }

    // ----------------------------------------------------------------------------------

    var headerRightIcon: Int = R.drawable.ic_trash
        set(value) {
            field = value
            updateView()
        }

    // ----------------------------------------------------------------------------------

    var onHeaderRightIconPress: (() -> Unit)? = null
        set(value) {
            field = value
            updateView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_half_modal_header, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.halfModalAttr)
        attrs.let {
            headerIcon =
                typedArray.getString(R.styleable.halfModalAttr_headerIcon) ?: resources.getString(
                    R.string.xl_close
                )
            headerTitle = typedArray.getString(R.styleable.halfModalAttr_headerTitle) ?: ""
            headerRightIcon =
                typedArray.getInt(R.styleable.halfModalAttr_headerRightIcon, R.drawable.ic_trash)
            isHeaderRightIconVisible =
                typedArray.getBoolean(R.styleable.halfModalAttr_isHeaderRightIconVisible, false)
            headerMode = HalfModalHeaderMode.values()[typedArray.getInt(
                R.styleable.halfModalAttr_halfModalMode,
                0
            )]
            headerInformation = typedArray.getString(R.styleable.halfModalAttr_instruction) ?: ""
            headerImageIcon = typedArray.getString(R.styleable.halfModalAttr_imageIcon) ?: ""
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(headerIconView, onIconButtonPress)

        updateView()
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (containerView == null) {
            super.addView(child, index, params)
        } else {
            containerView.addView(child, index, params)
        }
    }
    // ----------------------------------------------------------------------------------

    private fun updateView() {
        iconView.imageSource = headerImageIcon
        subtitleView.text = headerInformation
        IsEmptyUtil.setVisibility(headerInformation, subtitleView)
        IsEmptyUtil.setVisibility(isHeaderRightIconVisible, headerRightIconView)
        IsEmptyUtil.setVisibility(isHeaderRightIconVisible, trashIconView)
        headerRightIconView.imageSource = getDrawable(context, headerRightIcon)
        trashIconView.imageSource = getDrawable(context, headerRightIcon)
        headerRightIconView.setOnClickListener {
            onHeaderRightIconPress?.invoke()
        }
        trashIconView.setOnClickListener {
            onHeaderRightIconPress?.invoke()
        }
    }
}