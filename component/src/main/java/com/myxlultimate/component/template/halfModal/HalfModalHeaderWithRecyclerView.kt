package com.myxlultimate.component.template.halfModal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.template_half_modal_with_recycler_view.view.*
import kotlinx.android.synthetic.main.template_header_modal.view.*

class HalfModalHeaderWithRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    val contentList: RecyclerView by lazy { findViewById<RecyclerView>(R.id.modalListContent) }

    var useHeader: Boolean = true
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, headerView)
        }

    var titlePage: String = ""
        set(value) {
            field = value

            headerTitleView.text = value
        }

    var buttonPrimaryText: String = ""
        set(value) {
            field = value

            buttonView.text = value
            IsEmptyUtil.setVisibility(value, buttonView)
        }

    var buttonPrimaryEnabled: Boolean = true
        set(value) {
            field = value

            buttonView.isEnabled = value
        }

    var headerRightIcon: Int = 0
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, headerRightIconView)
            if (value > 0) headerRightIconView.imageSource = value
        }

    var onRightHeaderIconClicked: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(headerRightIconView, value)
        }

    var onLeftHeaderIconClicked: () -> Unit = {}
        set(value) {
            field = value

            TouchFeedbackUtil.attach(headerLeftIconView, value)
        }

    var onButtonPrimaryClicked: () -> Unit = {}
        set(value) {
            field = value

            TouchFeedbackUtil.attach(buttonView, value)
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.template_half_modal_with_recycler_view, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.halfModalAttr)
        typedArray.run {
            headerRightIcon = getInt(R.styleable.halfModalAttr_headerRightIcon, 0)
            buttonPrimaryText = getString(R.styleable.halfModalAttr_buttonText) ?: ""
            titlePage = getString(R.styleable.halfModalAttr_headerTitle) ?: ""
            useHeader = getBoolean(R.styleable.halfModalAttr_hasHeader, true)
            buttonPrimaryEnabled =
                getBoolean(R.styleable.halfModalAttr_isButtonPrimaryEnabled, true)
        }

        typedArray.recycle()

        TouchFeedbackUtil.attach(headerLeftIconView, onLeftHeaderIconClicked)
        TouchFeedbackUtil.attach(buttonView, onButtonPrimaryClicked)
    }
}
