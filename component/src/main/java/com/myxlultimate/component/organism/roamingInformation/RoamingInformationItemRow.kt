package com.myxlultimate.component.organism.roamingInformation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import kotlinx.android.synthetic.main.organism_roaming_information_item_row.view.*

class RoamingInformationItemRow@JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val imageSourceType: ImageSourceType?=ImageSourceType.BASE64,
        val imageSource :Any? = null
    )
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            titleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value
            iconView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var imageSource: Any? = null
        set(value) {
            field = value
            iconView.imageSource = value
        }
    // ----------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------
    /**
     * Main Setup for init
     */
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_roaming_information_item_row, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoamingInformationItemRow)
        typedArray.run {
            title = getString(R.styleable.RoamingInformationItemRow_title)?:""
            imageSourceType =
                ImageSourceType.values()[getInt(R.styleable.RoamingInformationItemRow_imageSourceType, 3)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.RoamingInformationItemRow_imageSource)
            } else {
                getString(R.styleable.RoamingInformationItemRow_imageSource)
            }
        }
        typedArray.recycle()
    }
}