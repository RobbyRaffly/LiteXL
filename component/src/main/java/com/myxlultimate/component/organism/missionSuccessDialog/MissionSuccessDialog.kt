package com.myxlultimate.component.organism.missionSuccessDialog

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_mission_success_dialog.view.*

class MissionSuccessDialog constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    var description = ""
        set(value) {
            field = value
            refreshView()
        }

    var actionButtonLabel = ""
        set(value) {
            field = value
            refreshView()

        }



    var secondButtonLabel = ""
        set(value) {
            field = value
            refreshView()

        }

    var onActionCLicked: (() -> Unit)? = null

    var onSecondButtonClicked: (() -> Unit)? = null

    var hasBackground: Boolean = true
        set(value) {
            field = value
            if (hasBackground)
                layoutBackground.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_mission_success_dialog)
            else
                layoutBackground.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.mud_palette_basic_white
                    )
                )
        }


    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            ivHeader.imageSourceType = imageSourceType
        }

    var imageSource: Any? = null
        set(value) {
            field = value
            ivHeader.imageSource = imageSource

        }

    private fun refreshView() {
        btnAction.text = actionButtonLabel
        tvDescription.text = description
        tvTitle.text = title
        btnSecondary.text = secondButtonLabel
        IsEmptyUtil.setVisibility(value = secondButtonLabel, view = btnSecondary)
    }


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_mission_success_dialog, this, true)


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MissionSuccessDialog)
        typedArray.run {
            title = getString(R.styleable.MissionSuccessDialog_title) ?: ""
            description = getString(R.styleable.MissionSuccessDialog_description) ?: ""
            actionButtonLabel = getString(R.styleable.MissionSuccessDialog_actionButtonLabel)?:""
            secondButtonLabel = getString(R.styleable.MissionSuccessDialog_secondButtonLabel)?:""
            imageSourceType =
                ImageSourceType.values()[getInt(R.styleable.MissionSuccessDialog_imageSourceType, 3)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE)
                getDrawable(R.styleable.MissionSuccessDialog_imageSource)
            else
                getString(R.styleable.MissionSuccessDialog_imageSource)
            hasBackground = getBoolean(R.styleable.MissionSuccessDialog_hasBackground, true)
        }

        typedArray.recycle()

        btnAction.setOnClickListener {
            onActionCLicked?.invoke()
        }
        btnSecondary.setOnClickListener {
            onSecondButtonClicked?.invoke()
        }
    }
}