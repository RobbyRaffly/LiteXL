package com.myxlultimate.component.organism.popUpInformationCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_pop_up_information_card.view.*

class PopUpInformationCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var isBackgroundWhite = false
    set(value) {
        field = value
        if(isBackgroundWhite)
            backgroundView.background = ContextCompat.getDrawable(context,R.drawable.pop_up_information_card_background_white)
        else
            backgroundView.background = ContextCompat.getDrawable(context,R.drawable.pop_up_information_card_background)
    }

    var useDarkBlueColor = false
    set(value) {
        field = value
        if(useDarkBlueColor){
            backgroundView.background = ContextCompat.getDrawable(context, R.drawable.pop_up_information_card_background_blue_black)
        }
    }

    var elevation = 0
        set(value) {
            field = value
            backgroundView.cardElevation = elevation.toFloat()
            val layoutParam:LinearLayout.LayoutParams = backgroundView.layoutParams as LayoutParams
            layoutParam.setMargins(elevation, elevation, elevation, elevation)
            backgroundView.layoutParams = layoutParam

        }

    var title = ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var titleColor = R.color.mud_palette_basic_black
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

    var buttonTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSource : Int = 0
    set(value) {
        field = value
        if (value != 0) {
            imageView.imageSource = getDrawable(context, value)
        }
    }

    // ----------------------------------------------------------------------------------

    var onActionButtonPress: (() -> Unit)? = null
        set(value) {
            field = value
            TouchFeedbackUtil.attach(buttonView, value)
        }

    var isShimmerOn: Boolean = false
        set(value) {
            field = value
            if (value) {
                shimmerLayout.startShimmer()
            } else {
                shimmerLayout.stopShimmer()
            }
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isCustomColorBackground = false
        set(value) {
            field = value
            setUpCustomBackground()
        }

    var customBackgroundColor = R.color.mud_palette_primary_blue
        set(value) {
            field = value
            setUpCustomBackground()
        }

    var descriptionTextColor = R.color.mud_palette_basic_black
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------


    var buttonColor = R.color.mud_palette_basic_black
        set(value) {
            field = value
            refreshView()
            setUpCustomBackground()
        }
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.visibility = if(title.isEmpty()) View.GONE else View.VISIBLE
        titleView.text = title
        titleView.setTextColor(getColor(context,titleColor))
        descriptionView.text = description
        buttonView.visibility = if(buttonTitle.isEmpty()) View.GONE else View.VISIBLE
        buttonView.text = buttonTitle
        shimmerLayout.visibility = if(isShimmerOn) View.VISIBLE else View.GONE
        originalView.visibility = if(isShimmerOn) View.GONE else View.VISIBLE
        descriptionView.setTextColor(getColor(context,descriptionTextColor))
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_pop_up_information_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PopUpInformationCard)
        typedArray.run {
            isBackgroundWhite = getBoolean(R.styleable.PopUpInformationCard_isBackgroundWhite,false)
            title = getString(R.styleable.PopUpInformationCard_title)?:""
            description = getString(R.styleable.PopUpInformationCard_description)?:""
            buttonTitle = getString(R.styleable.PopUpInformationCard_buttonTitle)?:""
            imageSource = getResourceId(R.styleable.PopUpInformationCard_imageSource,0)
            elevation = getDimensionPixelOffset(R.styleable.PopUpInformationCard_shadowWidth,0)
            isShimmerOn = getBoolean(R.styleable.PopUpInformationCard_isShimmerOn,false)
            titleColor = getResourceId(R.styleable.PopUpInformationCard_titleTextColor,R.color.mud_palette_basic_black)
        }
        typedArray.recycle()
    }

    private fun setUpCustomBackground() {
        if (isCustomColorBackground) {
            backgroundView.backgroundTintList =
                ContextCompat.getColorStateList(context, customBackgroundColor)
            buttonView.setTextColor(getColor(context, buttonColor))
        } else {
            if (isBackgroundWhite)
                backgroundView.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.pop_up_information_card_background_white
                )
            else
                backgroundView.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.pop_up_information_card_background
                )
        }
    }
}