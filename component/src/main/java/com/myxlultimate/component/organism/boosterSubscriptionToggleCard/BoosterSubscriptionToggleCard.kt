package com.myxlultimate.component.organism.boosterSubscriptionToggleCard

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_booster_subscription_toggle_card.view.*

class BoosterSubscriptionToggleCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    var title:String = ""
        set(value) {
            field = value
            tvTitle.text = value
        }

    var description:String = ""
        set(value) {
            field = value
            tvDescription.text = value
        }

    var isChecked:Boolean = false
        get() {
            return switchView.isChecked
        }
        set(value) {
            field = value
            switchView.isChecked = value
        }

    var imageSource:Drawable = resources.getDrawable(R.drawable.ic_roaming_automatic)
        set(value) {
            field = value
            imageView.imageSource = field
        }
    // ----------------------------------------------------------------------------------

    var onCheckedChange : ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.organism_booster_subscription_toggle_card, this, true)

        switchView.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            onCheckedChange?.invoke(b)
        }

        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.recurringBoosterIcon, typedValue, true)


        val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.BoosterSubscriptionToggleCard)
        typedArray.run {
            isChecked = typedArray.getBoolean(R.styleable.BoosterSubscriptionToggleCard_isChecked, false)
            title = typedArray.getString(R.styleable.BoosterSubscriptionToggleCard_title)?:""
            description = typedArray.getString(R.styleable.BoosterSubscriptionToggleCard_description)?:""
        }
    }
}