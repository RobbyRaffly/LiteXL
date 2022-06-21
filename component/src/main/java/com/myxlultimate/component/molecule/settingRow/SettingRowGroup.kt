package com.myxlultimate.component.molecule.settingRow

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.settingRow.adapter.SettingRowGroupAdapter
import kotlinx.android.synthetic.main.molecule_setting_row_group.view.*

class SettingRowGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var items = mutableListOf<SettingRow.Data>()
        set(value) {
            field = value
            settingRowGroupAdapter.items = value
        }

    // ----------------------------------------------------------------------------------

    var onItemPress: ((SettingRow.Data, Int) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    private val settingRowGroupAdapter by lazy {
        SettingRowGroupAdapter { data, index ->
            onItemPress?.let {
                it(data, index)
            }
        }
    }

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_setting_row_group, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingRowGroup)
        typedArray.run {
            title = getString(R.styleable.SettingRowGroup_title) ?: ""
        }
        typedArray.recycle()

        rvSettingRowGroup.apply {
            val params: ViewGroup.LayoutParams = layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = params
            adapter = settingRowGroupAdapter.also {
                it.items = items
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is SettingRow) {
            super.addView(child, index, params)
        } else {
            items.add(
                SettingRow.Data(
                    child.title,
                    child.ribbonLabel,
                    child.iconCode,
                    child.iconDrawable,
                    child.hasBottomLine
                )
            )
            settingRowGroupAdapter.items = items
        }
    }
}