package com.myxlultimate.component.organism.familyMemberCardItem

import android.content.Context
import android.graphics.drawable.Drawable
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.isGone
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.profileSelector.ProfileMode
import com.myxlultimate.component.organism.profileSelector.ProfileSelector
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.TouchFeedbackUtil
import com.myxlultimate.component.util.toGone
import com.myxlultimate.component.util.toVisible
import kotlinx.android.synthetic.main.organism_family_member_card_item.view.*

class FamilyMemberCardItem @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val TAG = this::class.java.simpleName

    data class Data(
        var cardMode: CardMode = CardMode.PROFILE,
        var cardListenerMode: CardListenerMode = CardListenerMode.CLICK,
        var isDisabled: Boolean = false,
        var imageScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
        var profileName: String = "",
        var profileId: String = "",
        var customValidity: String = "",
        var profileMode: ProfileMode? = null,
        var imageSourceType: ImageSourceType = ImageSourceType.DRAWABLE,
        var profileAvatar: Any? = null,
        var addMemberButtonText: String = "",
        var hasCloseButton: Boolean = false,
        @ColorRes var endButtonViewColor: Int = R.color.mud_palette_primary_blue,
        var radioDeactivatable: Boolean = false,
        var isRadioActive: Boolean = false,
        @ColorRes var memberStateColor: Int = R.color.mud_palette_primary_blue,
        var memberStatus: String = "",
        var addMemberTitle: String? = null,
        var validityMemberText: String = ""
    )

    // ----------------------------------------------------------------------------------

    val profileSelector: ProfileSelector by lazy { findViewById<ProfileSelector>(R.id.profileSelectorView) }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Mode
     * =================
     * */

    /**
     * Card mode
     * */
    var cardMode = CardMode.PROFILE
        set(value) {
            field = value
            updateView()
        }

    private var _cardMode = CardMode.PROFILE
        set(value) {
            field = value
            addMemberView.visibility = if (value == CardMode.ADD) View.VISIBLE else View.GONE
            haveAccountView.visibility = if (value == CardMode.PROFILE || value == CardMode.REMOVE || value == CardMode.NEW) View.VISIBLE else View.GONE
            removeMemberView.visibility = if (value == CardMode.REMOVE && memberStatus.isNotEmpty() || value == CardMode.NEW) View.VISIBLE else View.GONE
            memberStatusView.visibility = if (value == CardMode.PROFILE) View.VISIBLE else View.GONE
            setUpTouchFeedBackUtil()
        }

    var cardListenerMode = CardListenerMode.CLICK
        set(value) {
            field = value
            updateView()
        }

    private var _cardListenerMode = CardListenerMode.CLICK
        set(value) {
            field = value
            when (value) {
                CardListenerMode.RADIO -> {
                    radioButtonView.visibility = View.VISIBLE
                    nextButtonView.visibility = View.GONE
                }
                CardListenerMode.CHECKBOX -> {
                    radioButtonView.toGone()
                    nextButtonView.toGone()
                }
                else -> {
                    radioButtonView.visibility = View.GONE
                    nextButtonView.visibility = if (_cardMode == CardMode.PROFILE) View.VISIBLE else View.GONE
                }
            }
            setUpTouchFeedBackUtil()
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * General
     * =================
     * */
    var isDisabled = false
        set(value) {
            field = value
            updateView()
        }

    private var _isDisabled = false
        set(value) {
            field = value
            //MEMBER INFO
            profileSelector.textColor = R.color.basicBlack
            profileSelector.isDisabled = value

            if (value) {
                val colorGrey = getColor(context, R.color.mud_palette_basic_dark_grey)

                // ADD MEMBER
                addMemberTitleView.setTextColor(colorGrey)
                validityMemberView.setTextColor(colorGrey)
                addIconView.setTextColor(colorGrey)
                addMemberButtonView.setTextColor(colorGrey)

                //REMOVE ACCOUNT
                memberStateView.setTextColor(colorGrey)

            } else {
                val colorBlack = getColor(context, R.color.mud_palette_basic_black)

                // ADD MEMBER
                addMemberTitleView.setTextColor(colorBlack)
                validityMemberView.setTextColor(colorBlack)
                addIconView.setTextColor(colorBlack)
                addMemberButtonView.setTextColor(getColor(context, endButtonViewColor))

                //REMOVE ACCOUNT
                memberStateView.setTextColor(getColor(context, memberStateColor))
            }
            setUpTouchFeedBackUtil()
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Avatar
     * =================
     * */

    /**
     * image scale type
     * */
    var imageScaleType = ImageView.ScaleType.CENTER_CROP
        set(value) {
            field = value
            updateView()
        }

    private var _imageScaleType = ImageView.ScaleType.CENTER_CROP
        set(value) {
            field = value
            profileSelector.imageScaleType = value
        }

    /**
     * Profile name
     * */
    var profileName = ""
        set(value) {
            field = value
            updateView()
        }

    private var _profileName = ""
        set(value) {
            field = value
            profileSelector.name = value
        }

    /**
     * Profile id
     * */
    var profileId = ""
        set(value) {
            field = value
            updateView()
        }

    private var _profileId = ""
        set(value) {
            field = value
            profileSelector.id = value
        }

    /**
     * Profile custom validity
     * */
    var customValidity = ""
        set(value) {
            field = value
            updateView()
        }

    private var _customValidity = ""
        set(value) {
            field = value
            profileSelector.customValidity = value
        }

    /**
     * Profile mode
     * */
    var profileMode: ProfileMode? = null

    private var _profileMode: ProfileMode = ProfileMode.INITIAL
        set(value) {
            field = value
            profileSelector.profileMode = value
        }

    /**
     * Profile image source type
     * */
    var imageSourceType = ImageSourceType.DRAWABLE

    private var _imageSourceType = ImageSourceType.DRAWABLE
        set(value) {
            field = value
            profileSelector.imageSourceType = value
        }

    /**
     * Profile avatar
     * */
    var profileAvatar: Any? = null
        set(value) {
            field = value
            updateView()
        }

    private var _profileAvatar: Any? = null
        set(value) {
            field = value
            if (value == null) {
                _profileMode = profileMode?: ProfileMode.INITIAL
            } else {
                _profileMode = profileMode?: ProfileMode.AVATAR
                _imageSourceType = if (value is Drawable) {
                    ImageSourceType.DRAWABLE
                } else {
                    imageSourceType
                }
                profileSelector.avatar = value
            }
        }


    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Button
     * =================
     * */
    var addMemberButtonText = ""
        set(value) {
            field = value
            addMemberButtonView.text = value
            IsEmptyUtil.setVisibility(value, addMemberButtonView)
        }

    var hasCloseButton = false
        set(value) {
            field = value
            closeBtnView.visibility = if (value) View.VISIBLE else View.GONE
        }

    var endButtonViewColor = R.color.mud_palette_primary_blue
        set(value) {
            field = value
            updateView()
        }

    var memberStatusTextColor = R.color.mud_palette_basic_dark_grey
        set(value) {
            field = value
            memberStatusView.setTextColor(getColor(context, value))
        }

    private var _endButtonViewColor = R.color.mud_palette_primary_blue
        set(value) {
            field = value
            if (!_isDisabled) {
                addMemberButtonView.setTextColor(getColor(context, value))
            }
        }

    var radioDeactivatable = false

    var isRadioActive = false
        set(value) {
            if (radioDeactivatable && field && !value) return
            field = value
            IsEmptyUtil.setVisibility(value, selectedView)
            onChangePress?.let { it(value) }
        }

    var isChecked = false
        set(value) {
            field = value
            imgChecked.isGone = !isChecked
            if (isChecked) {
                cardView.strokeWidth = 4
                cardView.strokeColor = getColor(context, R.color.mud_palette_primary_blue)
            } else {
                cardView.strokeWidth = 0
            }
            onChangePress?.let { it(value) }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Member state
     * =================
     * */
    var memberStateColor = R.color.mud_palette_primary_blue
        set(value) {
            field = value
            updateView()
        }

    private var _memberStateColor = R.color.mud_palette_primary_blue
        set(value) {
            field = value
            if (!_isDisabled) {
                memberStateView.setTextColor(getColor(context, value))
                closeBtnView.setColorFilter(getColor(context, value))
            }
        }

    var memberStatus = ""
        set(value) {
            field = value
            updateView()
        }

    private var _memberStatus = ""
        set(value) {
            field = value
            memberStatusView.text = value
            memberStateView.text = value
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Text
     * =================
     * */
    var addMemberTitle = resources.getString(R.string.organism_no_account_card_instruction)
        set(value) {
            field = value
            updateView()
        }

    private var _addMemberTitle = resources.getString(R.string.organism_no_account_card_instruction)
        set(value) {
            field = value
            addMemberTitleView.text = value
        }

    var validityMemberText = ""
        set(value) {
            field = value
            updateView()
        }

    private var _validityMemberText = ""
        set(value) {
            field = value
            validityMemberView.text = value
            IsEmptyUtil.setVisibility(value, validityMemberView)
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * New Fam Member UI
     * =================
     * */

    var topRightDrawable = getDrawable(context, R.drawable.ic_red_remove_button)
    set(value) {
        field = value
        updateView()
    }

    var showTopRighticon = false
    set(value) {
        field = value
        updateView()
    }

    var showBottomRightIcon = false
    set(value) {
        field = value
        updateView()
    }

    var bottomRightIconText = resources.getString(R.string.xl_reload)
    set(value) {
        field = value
        updateView()
    }

    var bottomRightTextColor =R.color.mud_palette_primary_blue
    set(value) {
        field = value
        updateView()
    }

    var showBottomBeard = false
    set(value) {
        field = value
        updateView()
    }

    var bottomBeardTitle = ""
    set(value) {
        field = value
        updateView()
    }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Action listeners
     * =================
     * */
    var onPress: (() -> Unit)? = null

    var removeDisabledPress = false
        set(value) {
            field = value
            setUpTouchFeedBackUtil()
        }

    var onChangePress: ((Boolean) -> Unit)? = null

    var onTopRightIconPressed : (() -> Unit)? = null
    set(value) {
        field = value
        TouchFeedbackUtil.attach(topRightIconView,onTopRightIconPressed)
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_member_card_item, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FamilyMemberCardItem)
        typedArray.run {
            imageSourceType = ImageSourceType.values()[getInt(
                R.styleable.FamilyMemberCardItem_imageSourceType,
                2
            )]
            profileName = typedArray.getString(R.styleable.FamilyMemberCardItem_name) ?: ""
            profileId = typedArray.getString(R.styleable.FamilyMemberCardItem_id) ?: ""
            profileAvatar = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.FamilyMemberCardItem_avatar)
            } else
                getString(R.styleable.FamilyMemberCardItem_avatar)?: ""
            memberStatus = getString(R.styleable.FamilyMemberCardItem_memberStatus) ?: ""
            customValidity = getString(R.styleable.FamilyMemberCardItem_customValidity) ?: ""
            cardMode = CardMode.values()[getInt(R.styleable.FamilyMemberCardItem_cardModeFamily, 0)]
            cardListenerMode = CardListenerMode.values()[getInt(R.styleable.FamilyMemberCardItem_cardListenerMode, 0)]
            isDisabled = getBoolean(R.styleable.FamilyMemberCardItem_isDisabled, false)
            endButtonViewColor = getResourceId(
                R.styleable.FamilyMemberCardItem_endButtonViewColor,
                R.color.mud_palette_primary_blue
            )
            memberStateColor = getResourceId(
                R.styleable.FamilyMemberCardItem_memberStateColor,
                R.color.mud_palette_primary_blue
            )
            hasCloseButton = getBoolean(R.styleable.FamilyMemberCardItem_hasCloseButton, false)
            addMemberTitle = getString(R.styleable.FamilyMemberCardItem_memberTitle)
                ?: resources.getString(R.string.organism_no_account_card_instruction)
            addMemberButtonText =
                getString(R.styleable.FamilyMemberCardItem_addMemberButtonText) ?: ""
            validityMemberText =
                getString(R.styleable.FamilyMemberCardItem_validityMemberTitle) ?: ""
            removeDisabledPress =
                getBoolean(R.styleable.FamilyMemberCardItem_removeDisabledPress, false)
            isRadioActive = getBoolean(R.styleable.FamilyMemberCardItem_isRadioActive, false)
            memberStatusTextColor = getResourceId(
                R.styleable.FamilyMemberCardItem_memberStatusTextColor,
                R.color.mud_palette_basic_dark_grey
            )
        }
        typedArray.recycle()

    }

    private fun updateView() {
        _cardMode = cardMode
        _cardListenerMode = cardListenerMode
        _isDisabled = isDisabled

        _imageScaleType = imageScaleType
        _customValidity = customValidity
        _profileName = profileName
        _profileId = profileId
        _profileAvatar = profileAvatar

        _endButtonViewColor = endButtonViewColor
        _memberStateColor = memberStateColor
        _memberStatus = memberStatus

        _addMemberTitle = addMemberTitle
        _validityMemberText = validityMemberText

        topRightIconView.imageSource = topRightDrawable
        bottomRightIcon.text = bottomRightIconText
        bottomRightIcon.setTextColor( getColor(context,bottomRightTextColor))

        bottomBeardTitleView.text = bottomBeardTitle
        bottomBeardLayout.visibility = if(showBottomBeard) View.VISIBLE else View.GONE
        topRightIconView.visibility = if(showTopRighticon) View.VISIBLE else View.GONE
        bottomRightIcon.visibility = if(showBottomRightIcon) View.VISIBLE else View.GONE
    }

    private fun setUpTouchFeedBackUtil() {
        if (_isDisabled || removeDisabledPress) {
            TouchFeedbackUtil.detach(cardView)
            TouchFeedbackUtil.detach(bottomViewRemove)
        } else {
            if (_cardMode == CardMode.PROFILE || _cardMode == CardMode.ADD) {
                TouchFeedbackUtil.detach(bottomViewRemove)
                TouchFeedbackUtil.attach(cardView) {
                    when(_cardListenerMode) {
                        CardListenerMode.CLICK -> {
                            onPress?.invoke()
                        }
                        CardListenerMode.CHECKBOX -> {
                            isChecked = !isChecked
                        }
                        else -> {
                            isRadioActive = !isRadioActive
                        }
                    }
                }
            } else {
                TouchFeedbackUtil.detach(cardView)
                TouchFeedbackUtil.attach(bottomViewRemove) {
                    onPress?.invoke()
                }
            }
        }
    }
}