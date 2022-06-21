package com.myxlultimate.component.organism.packageCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.BackgroundColorMode
import com.myxlultimate.component.enums.SizeMode
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_box_card.view.*
import kotlinx.android.synthetic.main.organism_box_card.view.containerView

class FamilyPackageSquareCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    data class Data(
        val backgroundImage: String,
        var sizeMode: SizeMode = SizeMode.FULL,
        val name: String,
        val description: String,
        val showLabelPromo: Boolean,
        val label: String,
        val backgroundImageCardUrl: String,
        val backgroundMode: BackgroundColorMode,
        val rightImage: String = "",
        val showTopLabel: Boolean = false,
        val rightBase64Icon: String = "",
        val topLabelEndColor: String? = null,
        val topLabelStartColor: String? = null
    )

    var backgroundImage = ""
        set(value) {
            field = value

            imageView_set_center.imageSource = value
        }

    var rightImage = ""
        set(value) {
            field = value
        }

    var sizeMode = SizeMode.FULL
        set(value) {
            field = value
            refreshView()
        }

    var name = ""
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
    var backgroundMode = BackgroundColorMode.LIGHT
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        txt_entertainment_type.apply {
            text = name
        }
    }

    var backgroundImageCardUrl = ""
        set(value) {
            field = value
        }

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    var showLabelPromo = false
        set(value) {
            field = value
            refreshLabel()
        }

    var label = "Promo"
        set(value) {
            field = value
            refreshLabel()
        }

    var topLabelStartColor: String? = null
        set(value) {
            field = value
//            setUpBackgroundRibbon()
        }


    // ----------------------------------------------------------------------------------

    var showTopLabel = false
        set(value) {
            field = value
            refreshLabel()
        }

    private fun refreshLabel() {
    }

    var imageScaleType = ImageView.ScaleType.CENTER_CROP
        set(value) {
            field = value
            imageView_set_center.scaleType = value
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_box_card, this, true)

        imageView_set_center.scaleType = imageScaleType
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.boxCardFamilyAttr)
        typedArray.run {
            backgroundImage = getString(R.styleable.boxCardFamilyAttr_backgroundImage) ?: ""
            backgroundImageCardUrl =
                getString(R.styleable.boxCardFamilyAttr_imageItem) ?: ""
            name = getString(R.styleable.boxCardFamilyAttr_name) ?: ""
        }

        TouchFeedbackUtil.attach(containerView, onPress)
    }

}