package com.myxlultimate.component.organism.loginOptionCard

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import kotlinx.android.synthetic.main.organism_login_type_card.view.*
import kotlinx.android.synthetic.main.organism_login_type_card.view.containerView
import kotlinx.android.synthetic.main.organism_login_type_card.view.titleView

class LoginTypeCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var logo: Drawable? = null
        set(value) {
            field = value
            refreshView()
        }

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    var subTitle = ""
        set(value) {
            field = value
            refreshView()
        }

    var verticalLineColor = ContextCompat.getColor(context, R.color.colorPrimary)
        set(value) {
            field = value
            refreshView()
        }

    var onPress: (() -> Unit)? = null
        set(value) {
            field = value

            TouchFeedbackUtil.attach(containerView, value)
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun refreshView() {
        logoView.apply {
            imageSourceType = ImageSourceType.DRAWABLE
            imageSource = logo ?: ContextCompat.getDrawable(context, R.drawable.ic_xlprepaid_48px)
        }
        titleView.text = title
        subTitleView.text = subTitle
        IsEmptyUtil.setVisibility(subTitle, subTitleView)
        verticalLine.setBackgroundColor(verticalLineColor)
        invalidate()
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_login_type_card, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginTypeCardAttr)
        typedArray.run {
            logo = getDrawable(R.styleable.LoginTypeCardAttr_logo)
            title = getString(R.styleable.LoginTypeCardAttr_title) ?: ""
            subTitle = getString(R.styleable.LoginTypeCardAttr_subtitle) ?: ""
            verticalLineColor = getColor(
                R.styleable.LoginTypeCardAttr_verticalLineColor,
                ContextCompat.getColor(context, R.color.colorPrimary)
            )
        }
        typedArray.recycle()

        TouchFeedbackUtil.attach(containerView, onPress)
    }

}