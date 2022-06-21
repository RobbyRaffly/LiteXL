package com.myxlultimate.component.organism.bizOnItem.bizOnActivation

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.TireMode
import com.myxlultimate.component.token.imageView.ImageSourceType
import kotlinx.android.synthetic.main.organism_banner_tier_card.view.*

class BannerTierCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val imageIcon: Int,
        val imageSourceType: ImageSourceType,
        val title: String,
        val label: String,
        val description: String,
        val tireMode: TireMode
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var imageIcon = ""
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.BASE64
        set(value) {
            field = value

            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var description = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var label = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var tireMode = TireMode.NOT_PASSED_LEVEL
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        backgroundView.imageSourceType = imageSourceType
        backgroundView.imageSource = imageIcon
        titleText.text = title
        descriptionText.text = description
        availabilityLabelView.text = label
        when (tireMode) {
            TireMode.CURRENT_LEVEL -> {
                availabilityIcon.visibility = View.GONE
                availabilityView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.mud_palette_basic_green)
            }
            TireMode.PASSED_LEVEL -> {
                availabilityIcon.apply {
                    visibility = View.VISIBLE
                    imageSource = context.getDrawable(R.drawable.ic_tick)
                }
                availabilityView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.primaryBlue)
            }
            TireMode.NOT_PASSED_LEVEL -> {
                availabilityIcon.apply {
                    visibility = View.VISIBLE
                    imageSource = context.getDrawable(R.drawable.ic_lock)
                }
                availabilityView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.mud_palette_basic_medium_grey)
            }
        }

    }

    private fun imageAttribute(@AttrRes iconAttribute: Int): Drawable? {
        return ContextCompat.getDrawable(context, TypedValue().apply {
            context.theme.resolveAttribute(iconAttribute, this, true)
        }.resourceId)
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_banner_tier_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerTireCardAttr)
        typedArray.run {
            title = getString(R.styleable.BannerTireCardAttr_title) ?: ""
            description = getString(R.styleable.BannerTireCardAttr_description) ?: ""
            label = getString(R.styleable.BannerTireCardAttr_label) ?: ""
            tireMode = TireMode.values()[getInt(R.styleable.BannerTireCardAttr_tireMode, 2)]
        }

    }
}