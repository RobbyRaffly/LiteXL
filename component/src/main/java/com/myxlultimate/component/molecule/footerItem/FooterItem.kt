package com.myxlultimate.component.molecule.footerItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.molecule_footer_item.view.*

class FooterItem@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Button Properties
     */
    private var status = Status.INACTIVE


    /**
     * Variable
     */
    var title : CharSequence
        get() = FooterItemTitleEl.getText()
        set(value) {
            FooterItemTitleEl.setText(value as String)
        }

    var icon : Int = 0
        set(value) {
            field = value
            FooterItemIconEl.setIcon(value)
        }

    var counter : Int = 0
        set(value) {
            field = value
            FooterItemIconEl.setCounter(value)
        }

    var footerStatus : Boolean=false
        set(value) {
            field = value
            status = if (value) {
                Status.ACTIVE
            } else {
                Status.INACTIVE
            }
            FooterItemIconEl.setColor(status.color)
            FooterItemTitleEl.setColor(status.color)
        }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // SETUP
    // ----------------------------------------------------------------------------------

    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_footer_item, this, true)

        context.theme.obtainStyledAttributes(attrs, R.styleable.footerItemAttr, 0, 0).apply {
            try {
                getBoolean(R.styleable.footerItemAttr_isActive, false)?.let {
                    footerStatus = it
                }
                getString(R.styleable.footerItemAttr_title)?.let {
                    title = it
                }
                getResourceId(R.styleable.footerItemAttr_iconCode,0)?.let {
                    icon = it
                }
                getInteger(R.styleable.footerItemAttr_counter,0)?.let {
                    counter = it
                }
            }finally {
                recycle()
            }
        }
    }

}
