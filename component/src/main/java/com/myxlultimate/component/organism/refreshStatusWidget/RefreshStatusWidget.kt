package com.myxlultimate.component.organism.refreshStatusWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismRefreshStatusWidgetBinding
import com.myxlultimate.component.util.TouchFeedbackUtil

class RefreshStatusWidget@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var binding: OrganismRefreshStatusWidgetBinding? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var icon = resources.getString(R.string.xl_information)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var information = ""
        set(value) {
            field = value
            refreshView()
        }


    // ----------------------------------------------------------------------------------

    var textColor = getColor(context,R.color.mud_palette_basic_dark_grey)
    set(value) {
        field = value
        refreshView()
    }
    // ----------------------------------------------------------------------------------

    var statusTextColor = getColor(context,R.color.mud_palette_primary_blue)
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var status = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var buttonTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onButtonPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.buttonView?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------

    var onIconInformationPress: (() -> Unit)? = null
        set(value) {
            field = value

            binding?.iconInformationView?.let { TouchFeedbackUtil.attach(it, value) }
        }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        binding?.apply {
            this.titleView.text = title
            this.iconInformationView.text = icon
            this.informationView.text = information
            this.buttonView.text = buttonTitle
            this.titleView.setTextColor(textColor)
            this.informationView.setTextColor(textColor)
            this.statusInformationView.text = status
            this.statusInformationView.setTextColor(statusTextColor)
        }
    }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    init {
        binding =
            OrganismRefreshStatusWidgetBinding.inflate(LayoutInflater.from(context), this, true)
    }

}