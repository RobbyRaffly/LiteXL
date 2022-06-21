package com.myxlultimate.component.organism.missionListCard

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_mission_list_item_card.view.*

class MissionListItemCard constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val imageSourceType: ImageSourceType? = ImageSourceType.DRAWABLE,
        val title: String,
        val buttonTitle: String?,
        val information: String,
        val imageSource: Drawable?,
        val iconImage : Any? = R.drawable.ic_postpaid_egg_icon,
        val missionStatus: MissionStatus? = MissionStatus.NONE,
        val isDisabled: Boolean? = false
    )
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
        }

    // ----------------------------------------------------------------------------------

    var title = ""
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

    var information = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageIcon : Any? = getDrawable(context, R.drawable.ic_postpaid_egg_icon)
        set(value) {
            field = value ?: getDrawable(context, R.drawable.ic_postpaid_egg_icon)
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    var imageSource = getDrawable(context, R.drawable.ic_forever_login_home_new)

    // ----------------------------------------------------------------------------------

    var missionStatus = MissionStatus.NONE
        set(value) {
            field = value
            refreshView()
            setOnClick()
        }

    // ----------------------------------------------------------------------------------

    var isDisabled = false
        set(value) {
            field = value
            setOnClick()
        }
    // ----------------------------------------------------------------------------------

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value
            setOnClick()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_mission_list_item_card, this, true)


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MissionListItemCard)
        typedArray.run {
            imageSourceType =
                ImageSourceType.values()[getInt(R.styleable.MissionListItemCard_imageSourceType, 3)]
            title = getString(R.styleable.MissionListItemCard_title) ?: ""
            imageSource = getDrawable(R.styleable.MissionListItemCard_imageSource) ?: getDrawable(
                context,
                R.drawable.ic_forever_login_home_new
            )
            imageIcon = if (imageSourceType == ImageSourceType.DRAWABLE)
                    getDrawable(R.styleable.MissionListItemCard_imageIcon)
                else
                    getString(R.styleable.MissionListItemCard_imageIcon)
            information = getString(R.styleable.MissionListItemCard_information) ?: ""
            missionStatus = MissionStatus.values()[getInt(
                R.styleable.MissionListItemCard_missionStatus,
                0 // NONE
            )]
            buttonTitle = getString(R.styleable.MissionListItemCard_buttonTitle) ?: ""
            isDisabled = getBoolean(R.styleable.MissionListItemCard_isDisabled, false)
        }

        typedArray.recycle()
    }


    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        titleView.text = title
        buttonTitleView.text = buttonTitle
        informationView.text = information
        iconView.imageSource = imageIcon
        iconView.imageSourceType = imageSourceType
        iconView.visibility =
            if (missionStatus == MissionStatus.EGG_MISSION || missionStatus == MissionStatus.EGG_CHECKED) View.VISIBLE else View.GONE
        when (missionStatus) {
            MissionStatus.NONE -> {
                buttonTitleView.visibility = View.GONE
                imageView.visibility = View.GONE
            }
            MissionStatus.MISSION -> {
                buttonTitleView.visibility = View.VISIBLE
                imageView.visibility = View.GONE
            }
            MissionStatus.FAILED -> {
                buttonTitleView.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                imageView.imageSource = getDrawable(context, R.drawable.ic_fail_new)
            }
            MissionStatus.CHECKED -> {
                buttonTitleView.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                imageView.imageSource = getDrawable(context, R.drawable.ic_check_new)
            }
            MissionStatus.EGG_MISSION -> {
                imageView.visibility = View.VISIBLE
                imageView.imageSource = getDrawable(context, R.drawable.ic_arrow_right_prio)
            }
            MissionStatus.EGG_CHECKED -> {
                buttonTitleView.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                imageView.imageSource = getDrawable(context, R.drawable.ic_check_new)
            }
            else -> {
                imageView.imageSource = imageSource
                imageView.visibility = View.VISIBLE
                buttonTitleView.visibility = View.GONE
            }
        }
    }

    private fun setOnClick() {
        if (isDisabled || missionStatus == MissionStatus.FAILED) {
            titleView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
            informationView.setTextColor(getColor(context, R.color.mud_palette_basic_dark_grey))
            TouchFeedbackUtil.detach(cardView)
        } else if (missionStatus == MissionStatus.CHECKED || missionStatus == MissionStatus.NONE) {
            TouchFeedbackUtil.detach(cardView)
        } else {
            titleView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
            informationView.setTextColor(getColor(context, R.color.mud_palette_basic_black))
            TouchFeedbackUtil.attach(cardView, onPress)
        }
    }

}