package com.myxlultimate.component.atom.inputField

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.myxlultimate.component.R

class EmptySubmitSearchView : SearchView {
    var tvSearch: SearchAutoComplete? = null
    var listener: OnQueryTextListener? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun setOnQueryTextListener(listener: OnQueryTextListener) {
        super.setOnQueryTextListener(listener)
        this.listener = listener
        tvSearch = findViewById(R.id.search_src_text)
        tvSearch?.typeface = ResourcesCompat.getFont(context, R.font.museo_sans_500)
        tvSearch?.textSize = 14f
        tvSearch?.setHintTextColor(ContextCompat.getColor(context, R.color.greySuit))
        val searchHintIcon = findViewById<ImageView?>(R.id.search_mag_icon)
        searchHintIcon?.setImageResource(R.drawable.ic_search_filter)
        tvSearch?.setOnEditorActionListener { v, actionId, event ->
            listener.onQueryTextSubmit(query.toString())
            true
        }
    }
}