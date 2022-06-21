package com.myxlultimate.component.organism.transactionCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.ConverterUtil
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_transaction_payment_method_option_card.view.*

class TransactionPaymentMethodOptionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val code: String = "",
        val name: String = "",
        val information: String = "",
        val cashbackPercentage: Int = 0,
        val iconImage: String = "",
        val imageSourceType: ImageSourceType = ImageSourceType.ASSET,
        val balance: Int = 0,
        val isBalanceVisible: Boolean = false,
        val inactiveRadio: Boolean = false,
        val isBalanceEnough: Boolean = true,
        var isCardSelected: Boolean = false,
        var hasTopUpButton: Boolean = false,
        val description: String = "",
        val balanceString: String? = "",
        val ribbonTitle: String? = "",
        val promoStringTitle: String? = "",
        val promoButtonTitle: String? = "",
        val bottomInformation: String? = "",
        val bottomInformationUnderCardView: String? = "",
        val buttonLabel: String = ""
    )

    // ----------------------------------------------------------------------------------

    var code = "" // payment method code

    // ----------------------------------------------------------------------------------

    var detachTouchFeedback = false
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var isBalanceVisible = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var inactiveRadio = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isBalanceEnough = true
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var isCardSelected = false
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var name = ""
        set(value) {
            field = value
            refreshView()
        }
    // ----------------------------------------------------------------------------------

    var cashbackPercentage = 0
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

    var iconImage = ""
        set(value) {
            field = value

            iconView.imageSource = value
        }


    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.ASSET
        set(value) {
            field = value

            iconView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var balance = 0
        set(value) {
            field = value
            refreshView()
        }

    var balanceString: String = ""
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

    var ribbonTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var promoStringTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var promoButtonTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var bottomInformation = ""
        set(value) {
            field = value
            refreshView()
        }

    var bottomInformationUnderCardView = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var onChange: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onTopUpButtonPress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------
    var onButtonPress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------
    var onDetailPromoButtonPress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var buttonLabel = ""
        set(value) {
            field = value
            refreshView()
        }

    var hasTopUpButton: Boolean = false
        set(value) {
            field = value
            refreshView()
        }

    private fun refreshView() {

        setDetachTouchFeedback()

        // radio check button listener
        setIsCardSelected()

        // payment name
        setName()

        // payment balance
        setBalanceView()

        // cashback
        setCashbackPercentage()

        // information
        setInformation()

        // description
        setDescription()

        // ribbon
        setRibbonTitle()

        // promo section
        setPromoStringTitle()
        setPromoButtonTitle()

        // button top up
        setButtonTopUpVisibility()
        setButtonTitleView()

        // radio button
        setRadioContainerVisibility()
        setRadioButtonInactiveViewVisibility()
        setRadioButtonCheckVisibility()
        setRadioButtonDisabledViewVisibility()

        // set bottom information warning
        setBottomInformationView()
        setBottomInformationWarning()
        setBottomInformationWarning2()

        updateToggleCapability()
    }

    private fun setDetachTouchFeedback() {
        if (detachTouchFeedback) {
            TouchFeedbackUtil.detach(buttonTopUpView)
            TouchFeedbackUtil.detach(cardView)
        } else {
            TouchFeedbackUtil.attach(buttonTopUpView) {
                onTopUpButtonPress?.invoke()
            }
            TouchFeedbackUtil.attach(promoButtonText) {
                onDetailPromoButtonPress?.invoke()
            }
            TouchFeedbackUtil.attach(buttonTitleView) {
                onButtonPress?.invoke()
            }
        }
    }

    private fun setIsCardSelected() {
        onChange?.let { it(isCardSelected) }
    }

    private fun setName() {
        nameView.text = name
    }

    private fun setCashbackPercentage() {
        cashbackView.apply {
            text = ("Cashback $cashbackPercentage%")
        }
        IsEmptyUtil.setVisibility(cashbackPercentage, bottomView)
    }

    private fun setInformation() {
        if (information.isNotEmpty()) {
            informationView.text = information
        }
        IsEmptyUtil.setVisibility(information, informationView)
    }

    private fun setRadioContainerVisibility() {
        radioContainerView.isVisible = !hasTopUpButton && !inactiveRadio
    }

    private fun setRadioButtonDisabledViewVisibility() {
        radioButtonDisabledView.isVisible = !isBalanceEnough
    }

    private fun setRadioButtonInactiveViewVisibility() {
        radioButtonInactiveView.isVisible = isBalanceEnough
    }

    private fun setRadioButtonCheckVisibility() {
        radioButtonSetterView.isVisible = isBalanceEnough && isCardSelected
    }

    private fun setBalanceView() {
        balanceView.apply {
            isVisible = isBalanceVisible
            setTextColor(
                getColor(
                    context,
                    if (!isBalanceEnough)
                        R.color.mud_palette_basic_red
                    else
                        R.color.mud_palette_basic_black
                )
            )
            text = if (balanceString.isNotEmpty())
                balanceString
            else
                context.getString(
                    R.string.organism_transaction_payment_method_option_card_balance
                ) + " " + ConverterUtil.convertDelimitedNumber(balance.toLong(), true)
        }
    }

    private fun setDescription() {
        IsEmptyUtil.setVisibility(description, descriptionView)
        tvDescription.text = description
    }

    private fun setRibbonTitle() {
        layoutRibbon.isVisible = ribbonTitle.isNotEmpty()
        viewGapRibbon.isVisible = ribbonTitle.isEmpty()

        ribbonLabel.text = ribbonTitle
    }

    private fun setBottomInformationView() {
        bottomInformationView.isVisible =
            promoButtonTitle.isNotEmpty() || bottomInformation.isNotEmpty()
    }

    private fun setPromoStringTitle() {
        bottomInformationView.isVisible = promoStringTitle.isNotEmpty()
        promoBubbleTitle.isVisible = promoStringTitle.isNotEmpty()
        promoBubbleTitle.text = promoStringTitle
    }

    private fun setPromoButtonTitle() {
        promoButtonText.isVisible = promoButtonTitle.isNotEmpty()
        promoInformationView.isVisible = promoButtonTitle.isNotEmpty()
        promoButtonText.text = promoButtonTitle
    }

    private fun setButtonTopUpVisibility() {
        buttonTopUpView.isVisible = hasTopUpButton
        buttonTopUpView.text = buttonLabel
    }

    private fun setButtonTitleView() {
        buttonTitleView.isVisible = buttonLabel.isNotEmpty()
        buttonTitleView.text = buttonLabel
    }

    private fun setBottomInformationWarning() {
        bottomInformationWarning.apply {
            title = bottomInformation
            isVisible = bottomInformation.isNotEmpty()
        }
    }

    private fun setBottomInformationWarning2() {
        bottomInformationWarningUnderCardView.apply {
            title = bottomInformationUnderCardView
            isVisible = bottomInformationUnderCardView.isNotEmpty()
        }
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_payment_method_option_card, this, true)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.TransactionPaymentMethodOptionCardAttr
        )
        typedArray.run {
            name = getString(R.styleable.TransactionPaymentMethodOptionCardAttr_name) ?: ""
            imageSourceType =
                ImageSourceType.values()[getInt(
                    R.styleable.TransactionPaymentMethodOptionCardAttr_imageSourceType,
                    0 // ASSET
                )]
            iconImage =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_iconImage) ?: ""
            isBalanceVisible =
                getBoolean(R.styleable.TransactionPaymentMethodOptionCardAttr_isBalance, false)
            balance = getInt(R.styleable.TransactionPaymentMethodOptionCardAttr_balance, 0)
            isBalanceEnough =
                getBoolean(R.styleable.TransactionPaymentMethodOptionCardAttr_isBalanceEnough, true)
            isCardSelected =
                getBoolean(R.styleable.TransactionPaymentMethodOptionCardAttr_isCardSelected, false)
            cashbackPercentage =
                getInt(R.styleable.TransactionPaymentMethodOptionCardAttr_cashbackPercentage, 0)
            inactiveRadio =
                getBoolean(R.styleable.TransactionPaymentMethodOptionCardAttr_inactiveRadio, false)
            information =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_information) ?: ""
            buttonLabel =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_buttonLabel) ?: ""
            description =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_description) ?: ""
            ribbonTitle =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_ribbonLabel) ?: ""
            hasTopUpButton =
                getBoolean(R.styleable.TransactionPaymentMethodOptionCardAttr_hasTopUpButton, false)
            detachTouchFeedback =
                getBoolean(R.styleable.TransactionPaymentMethodOptionCardAttr_detachFeedback, false)
            balanceString =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_balanceString) ?: ""
            promoStringTitle =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_promoTitle) ?: ""
            promoButtonTitle =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_promoButtonTitle) ?: ""
            bottomInformation =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_bottomInformation)
                    ?: ""
            bottomInformationUnderCardView =
                getString(R.styleable.TransactionPaymentMethodOptionCardAttr_bottomInformationUnderCardView)
                    ?: ""
        }
        typedArray.recycle()

        refreshView()
    }

    private fun updateToggleCapability() {
        if (!hasTopUpButton && isBalanceEnough && !inactiveRadio) {
            TouchFeedbackUtil.attach(cardView) {
                toggle()
            }
        } else {
            TouchFeedbackUtil.attach(cardView) {
                // detach cardView on click listener
            }
        }
    }


    private fun toggle() {
        if (!isCardSelected) {
            isCardSelected = true
        }
    }
}