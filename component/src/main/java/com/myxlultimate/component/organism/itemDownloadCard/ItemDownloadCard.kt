package com.myxlultimate.component.organism.itemDownloadCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_item_download_card.view.*

class ItemDownloadCard  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var title = ""
    set(value) {
        field = value
        titleView.text = value
    }

    var imageSourceType = ImageSourceType.DRAWABLE
    set(value) {
        field = value
        iconView.imageSourceType
    }

    var icon : Any? = null
    set(value) {
        field = value
        if(value == null) {
            iconView.imageSourceType = ImageSourceType.DRAWABLE
            iconView.imageSource = getDrawable(context,R.drawable.ic_download)
        } else {
            iconView.imageSourceType = imageSourceType
            iconView.imageSource = value
        }
    }

    var enableCard = true
        set(value) {
            field = value
            titleView.isEnabled = value
            if (!value) {
                iconView.imageSource = getDrawable(context,R.drawable.ic_download_disable)
            }
        }

    var onIconPress: (() -> Unit)? = null
    set(value) {
        field = value
        TouchFeedbackUtil.attach(cardView,value)
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_item_download_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemDownloadCard)
        typedArray.run {
            title = getString(R.styleable.ItemDownloadCard_title)?:""
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.ItemDownloadCard_imageSourceType, 2)]
            icon = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.ItemDownloadCard_imageSource) ?: getDrawable(context,R.drawable.ic_download)
            } else {
                getString(R.styleable.ItemDownloadCard_imageSource) ?: ""
            }
            enableCard = getBoolean(R.styleable.ItemDownloadCard_enableCard, true)
        }
        typedArray.recycle()

    }
}