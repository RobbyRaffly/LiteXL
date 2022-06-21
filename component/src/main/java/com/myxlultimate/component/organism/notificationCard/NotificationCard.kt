package com.myxlultimate.component.organism.notificationCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_notification_card.view.*

class NotificationCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val title: String,
        val imageSourceType: ImageSourceType,
        val image: Any?,
        val isRead: Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var isRead = false
        set(value) {
            field = value

            circleNotificationView.visibility = if(!value) View.VISIBLE else View.GONE

            titleReadView.visibility = if(value) View.VISIBLE else View.GONE
            titleUnreadView.visibility = if(!value) View.VISIBLE else View.GONE
        }


    // ----------------------------------------------------------------------------------

    var title = ""
        set(value) {
            field = value

            titleUnreadView.text = value
            titleReadView.text = value
        }

    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value

            iconView.imageSourceType = value
        }

    // ----------------------------------------------------------------------------------

    var image :Any? = null
        set(value) {
            field = value

            iconView.imageSource = value
        }

    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(contentView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_notification_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NotificationCardAttr)
        typedArray.run {
            title = getString(R.styleable.NotificationCardAttr_title) ?: ""
            imageSourceType = ImageSourceType.values()[getInt(R.styleable.NotificationCardAttr_imageSourceType, 2)]
            image = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.CustomImageViewAttr_imageSource)
            } else {
                getString(R.styleable.CustomImageViewAttr_imageSource)
            }
            isRead = getBoolean(R.styleable.NotificationCardAttr_isRead,false)
        }

        TouchFeedbackUtil.attach(contentView, onPress)
        TouchFeedbackUtil.attach(contentView) { toggle() }
    }

    fun toggle() {
        if(!isRead) {
            isRead = !isRead
        }
    }
}