package com.myxlultimate.component.organism.lockBalanceItem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_lock_balance_item.view.*

class LockBalanceItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    data class Data(
        val title : String = "",
        val information : String = "",
        val isEnabledSwitch : Boolean = true,
        val hasBottomLine : Boolean = true,
        var isChecked : Boolean = true
    )

    // ----------------------------------------------------------------------------------
    var isChecked:Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
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

    var onCheckedChange : ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var isEnabledSwitch = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var hasBottomLine = true
    set(value) {
        field = value
        refreshView()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.text = title
        informationView.text = information
        lineView.visibility = if(hasBottomLine) View.VISIBLE else View.GONE
        switchView.isEnabled = isEnabledSwitch
        removeCheckedChangeListener()
        switchView.isChecked = isChecked
        addCheckedChangeListener()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_lock_balance_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LockBalanceItem)
        typedArray.run {
            title = getString(R.styleable.LockBalanceItem_title)?:""
            information = getString(R.styleable.LockBalanceItem_information)?:""
            hasBottomLine = getBoolean(R.styleable.LockBalanceItem_hasBottomLine,true)
        }
        typedArray.recycle()

        addCheckedChangeListener()
    }

    fun removeCheckedChangeListener(){
        switchView.setOnCheckedChangeListener { _, _ ->  }
    }

    fun addCheckedChangeListener(){
        switchView.setOnCheckedChangeListener{ v, b ->
            removeCheckedChangeListener()
            v.isChecked = !v.isChecked
            addCheckedChangeListener()
            onCheckedChange?.invoke(b)
        }
    }
}