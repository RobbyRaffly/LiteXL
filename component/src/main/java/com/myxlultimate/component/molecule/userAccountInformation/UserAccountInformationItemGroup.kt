package com.myxlultimate.component.molecule.userAccountInformation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.molecule_quota_summary_group.view.*

class UserAccountInformationItemGroup @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var itemCounter = 0

    // ----------------------------------------------------------------------------------

    private var lineQuantity = 0

    // ----------------------------------------------------------------------------------

    var children = mutableListOf<UserAccountInformationItem>()


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.molecule_user_account_information_item_group, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserAccountInformationGroupAttr)
        typedArray.run {
            lineQuantity = getInt(R.styleable.UserAccountInformationGroupAttr_lineNumber,0)
        }
        typedArray.recycle()
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (mainView == null) {
            super.addView(child, index, params)
        } else {
            if (child != null && child is UserAccountInformationItem) {

                val layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0,resources.getDimension(R.dimen.userAccountInformationItemGroupPadding).toInt(),0,resources.getDimension(R.dimen.userAccountInformationItemGroupPadding).toInt())
                children.add(child)
                mainView.addView(child, index, layoutParams)

                if (itemCounter < lineQuantity) {
                    val view = View(context)
                    val layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        4
                    )
                    view.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mud_palette_basic_light_grey
                        )
                    )
                    mainView.addView(view, layoutParams)
                    itemCounter++
                }
            }
        }
    }
}