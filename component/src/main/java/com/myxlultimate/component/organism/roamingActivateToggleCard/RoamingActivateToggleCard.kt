package com.myxlultimate.component.organism.roamingActivateToggleCard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_roaming_activate_toggle_card.view.*

class RoamingActivateToggleCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    var isChecked:Boolean = false
        get() {
            return switchView.isChecked
        }
        set(value) {
            field = value
            switchView.isChecked = value
        }

    var isAuto:Boolean = true
        set(value) {
            field = value

            enabledView.visibility = if(isAuto) GONE else VISIBLE
            disabledView.visibility = if(isAuto) VISIBLE else GONE

            if (isAuto){
                containerView.cardElevation = 0f
            }

            val backgroundTypedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(R.attr.colorBackgroundLight, backgroundTypedValue, true)

            ViewCompat.setBackgroundTintList(containerView,
                    if(isAuto)
                        ColorStateList.valueOf(backgroundTypedValue.data)
                    else
                        ColorStateList.valueOf(resources.getColor(R.color.mud_palette_basic_white))
            )

        }

    var imageSource:Drawable = resources.getDrawable(R.drawable.ic_roaming_aktif_otomatis_prepaid)
        set(value) {
            field = value
            disabledImageView.imageSource = field
        }
    // ----------------------------------------------------------------------------------

    var onCheckedChange : ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var isEnabledSwitch = false
    set(value) {
        field = value
        switchView.isEnabled = value
    }

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.organism_roaming_activate_toggle_card, this, true)

        switchView.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            onCheckedChange?.invoke(b)
        }



        val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.RoamingActivateToggleCardAttr)
        typedArray.run {
            isChecked = typedArray.getBoolean(R.styleable.RoamingActivateToggleCardAttr_isChecked, false)
            isAuto = typedArray.getBoolean(R.styleable.RoamingActivateToggleCardAttr_isAuto, false)
            imageSource = typedArray.getDrawable(R.styleable.RoamingActivateToggleCardAttr_imageSource) ?:  resources.getDrawable(R.drawable.ic_roaming_aktif_otomatis_prepaid)
        }
    }
}