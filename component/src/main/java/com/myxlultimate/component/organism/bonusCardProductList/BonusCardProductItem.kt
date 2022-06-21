package com.myxlultimate.component.organism.bonusCardProductList

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.molecule_setting_row.view.*
import kotlinx.android.synthetic.main.organism_bonus_card_product_item.view.*
import kotlinx.android.synthetic.main.organism_bonus_card_product_item.view.containerView

class BonusCardProductItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val bonusCardProductMode: BonusCardProductMode = BonusCardProductMode.RADIOBUTTON,
        val imageSwitch : Int = R.drawable.ic_tones_with_base_icon,
        val title : String = "",
        val information : String = "",
        val isChecked : Boolean = false,
        val icon : String = "",
        var isCardSelected : Boolean = false,
        var isCardDisabled : Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var bonusCardMode = BonusCardProductMode.RADIOBUTTON
        set(value) {
            field = value
            setMode()
        }


    // ----------------------------------------------------------------------------------
    var imageSwitch = R.drawable.ic_tones_with_base_icon
        set(value) {
            field = value
            imageViewSwitch.imageSource = getDrawable(context, value)
        }

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

    var isChecked: Boolean = false
        get() {
            return switchView.isChecked
        }
        set(value) {
            field = value
            switchView.isChecked = value
        }

    // ----------------------------------------------------------------------------------

    var icon = ""
        set(value) {
            field = value
            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var isCardSelected = false
        set(value) {
            field = value
            radioButtonSetterView.visibility = if (isCardSelected) View.VISIBLE else View.GONE
            onChange?.let { it(value) }
            setMode()
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------

    var inactiveRadio = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleSwitchView.text = title
        titleBonusView.text = title
        informationSwitchView.text = information
        if (inactiveRadio) {
            radioContainerView.apply {
                visibility = View.GONE
            }
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_bonus_card_product_item, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BonusCardProductItem)
        typedArray.run {
           bonusCardMode =
               BonusCardProductMode.values()[getInt(
                   R.styleable.BonusCardProductItem_BonusCardProductMode,
                   1 // RADIOBUTTON
               )]
            title = getString(R.styleable.BonusCardProductItem_title)?:""
            information = getString(R.styleable.BonusCardProductItem_information)?:""
            imageSwitch = getResourceId(R.styleable.BonusCardProductItem_imageSwitchIcon,R.drawable.ic_tones_with_base_icon)
            isChecked = getBoolean(R.styleable.BonusCardProductItem_isChecked,false)
            icon = getString(R.styleable.BonusCardProductItem_imageSource)?:""

        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView, onPress)
    }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    private fun setMode() {
        if (bonusCardMode == BonusCardProductMode.SWITCH) {
            switchLayout.visibility = View.VISIBLE
            radioButtonLayout.visibility = View.GONE
        } else {
            switchLayout.visibility = View.GONE
            radioButtonLayout.visibility = View.VISIBLE
        }

        if (bonusCardMode == BonusCardProductMode.RADIOBUTTON && !inactiveRadio) {
            TouchFeedbackUtil.attach(containerView) {
                toggle()
            }
        } else if (bonusCardMode == BonusCardProductMode.SWITCH) {
            TouchFeedbackUtil.detach(containerView)
        }
    }

    fun toggle() {
        if (!isCardSelected) {
            isCardSelected = true
        }
    }

}