package com.myxlultimate.component.organism.footerMenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.molecule.footerItem.FooterItem
import com.myxlultimate.component.molecule.footerItem.data.FooterEntity
import kotlinx.android.synthetic.main.organism_footer_menu.view.*


/**
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 * SLIDER INDICATOR COMPONENT
 * --------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------
 */
class FooterMenu @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // PROPERTIES
    // ----------------------------------------------------------------------------------

    /**
     * Slide Properties
     */
    private var numberOfIcon = 1
    private var activeIndex = 0
    private var listOfFooter = mutableListOf<FooterEntity>()

    /**
     * Variables
     */
    var footerMenuNumberofIcon : Int
    get() =  numberOfIcon
    set(value) {
        numberOfIcon = value
        setFooterItems()
    }

    var footerMenuActiveIndex : Int
    get() = activeIndex
    set(value) {
        activeIndex = value
        setFooterItems()
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
            .inflate(R.layout.organism_footer_menu, this, true)
        setData()

        context.theme.obtainStyledAttributes(attrs, R.styleable.footerMenuAttr, 0, 0).apply {
            try {
                getInteger(R.styleable.footerMenuAttr_numberOfIcon,numberOfIcon)?.let {
                    footerMenuNumberofIcon = it
                }
                getInteger(R.styleable.footerMenuAttr_activeIndex,activeIndex)?.let {
                    footerMenuActiveIndex = it
                }
            }finally {
                recycle()
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // METHODS
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Set Footer Items
     */
    private fun setFooterItems() {
        val layoutParent = FooterMenuEl
        layoutParent.removeAllViews()

        for (index in 0 until numberOfIcon) {
            val footer = FooterItem(context)
            footer.title = (listOfFooter[index].title)
            footer.icon = (listOfFooter[index].icon)
            footer.counter = (listOfFooter[index].counter)

            if(index == activeIndex){
                footer.footerStatus = (true)

            }
            if (index != numberOfIcon - 1) {
                val layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    1.0F
                )
                footer.layoutParams = layoutParams
            }
            layoutParent.addView(footer)
        }
    }

    private fun setData() {
        listOfFooter.add(FooterEntity(R.string.xl_home,"Dashboard",0))
        listOfFooter.add(FooterEntity(R.string.xl_store,"XL Store",0))
        listOfFooter.add(FooterEntity(R.string.xl_heart,"XL Care",0))
        listOfFooter.add(FooterEntity(R.string.xl_profile,"Profile",0))

    }

}
