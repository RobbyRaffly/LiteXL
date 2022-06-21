package com.myxlultimate.component.organism.quotaDetailWidget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.enums.QuotaDetailType
import com.myxlultimate.component.molecule.quotaDetail.QuotaDetailItem
import com.myxlultimate.component.molecule.quotaDetail.adapters.RecycleViewAdapter
import com.myxlultimate.component.organism.profileSelector.ProfileMode
import com.myxlultimate.component.organism.profileSelector.ProfileSelector
import com.myxlultimate.component.token.imageView.ImageSourceType
import com.myxlultimate.component.util.IsEmptyUtil
import com.myxlultimate.component.util.ListUtil
import kotlinx.android.synthetic.main.organism_quota_detail_widget_quota_breakdown.view.*
import java.text.SimpleDateFormat
import java.util.*

class QuotaBreakdownQuotaDetailWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val TAG = this::class.java.simpleName

    data class Data(
        val items: MutableList<QuotaDetailItem.Data>,
        val quotaCode: String = "",
        var topLeftTitle: String = "",
        var subtitle: String = "",
        var bottomStatusDescription: String = "",
        var bottomNoteDescription: String = "",
        var name: String = "",
        var iconImage: String = "",
        var isDateFormatExpiredAt: Boolean = false,
        var expiredAt: Long = 0L,
        var remainingContractMonthStr: String? = null,
        var remainingContractMonth: Int = -1, // -1 month, 0 month, 1 month, 2 month, so on
        var hasBonus: Boolean = false,
        var bonusTitle: String = "",
        var isRecurring: Boolean = false,
        var quotaDetailType: QuotaDetailType = QuotaDetailType(),
        var profileName: String = "",
        var profileId: String = "",
        var profileAvatar: String = "",
        var profileMode: ProfileMode? = null,
        var quotaDetailWidgetMode: QuotaDetailWidgetMode = QuotaDetailWidgetMode.NORMAL,
        var primaryButtonRedStyleText: String? = null,
        var primaryButtonSecondaryStyleText: String? = null,
        var secondaryButtonText: String? = null,
        var statusFlagActiveText: String? = null,
        var statusFlagInactiveText: String? = null,
        var validityLabelDefault: String? = null,
        var validityLabelRecurring: String? = null,
        var validityDateText: String? = null,
        var iconViewValiditySource: String? = null,
        var isPrimaryButtonRedStyleVisible: Boolean = true,
        var isPrimaryButtonRedStyleEnabled: Boolean = true,
        var isPrimaryButtonSecondaryStyleVisible: Boolean = false,
        var isPrimaryButtonSecondaryStyleEnabled: Boolean = true,
        var isSecondaryButtonVisible: Boolean = false,
        var isHeaderIconRightVisible: Boolean = false,
        var isStatusFlagVisible: Boolean = false,
        var isStatusActive: Boolean = false,
        var isHeaderVisible: Boolean = true,
        var isQuotaActionLayoutVisible: Boolean = true,
        var failTitle: String? = "",
        var failedButtonTitle: String? = "",
        var slotFee: String? = "",
        var slotFeeLabel: String? = "",
        var slotFeeIcon: String? = "",
        var disableDetailItem: Boolean? = false,
        var isValidateDateVisible: Boolean? = false,
        var disableCard: Boolean? = false,
        var defaultValidityUnlimitedDateText: String? = null,
        var isTrashIconVisible: Boolean? = false,
        var isSolidButtonVisible: Boolean = false,
        var isSolidButtonEnabled: Boolean = true,
        var solidButtonText: String? = null,
        var isHeaderInformationVisible: Boolean = false,
        var informationValue1: String = "",
        var informationValue2: String = "",
        var bottomNoteDescriptionError: Boolean = false,
        var showTransferBottomBeard: Boolean = false,
        var transferQuotaBottomInformationTitle: String = "",
        var transferQuotaBottomButtonTitle: String = ""
    )

    // ----------------------------------------------------------------------------------

    private val profileSelector: ProfileSelector by lazy { findViewById<ProfileSelector>(R.id.profileSelectorView) }
    private val recycleAdapter by lazy { RecycleViewAdapter() }

    // ----------------------------------------------------------------------------------

    /**
     * =================
     * Items
     * =================
     * */
    var items = mutableListOf<QuotaDetailItem.Data>()
        set(value) {
            field = value
            recycleAdapter.items = value
            itemContainerView.visibility = if (items.isNotEmpty()) View.VISIBLE else View.GONE
            updateView()
        }

    /**
     * =================
     * Header
     * =================
     * */
    var isHeaderVisible = true
        set(value) {
            field = value
            updateView()
        }

    private var _isHeaderVisible = true
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, headerLayout)
            _isHeaderHorizontalLineVisible =
                (value || _isProfileSelectorLayoutVisible) && items.isNotEmpty()
        }

    private var _isHeaderHorizontalLineVisible = true
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, headerHorizontalLine)
        }

    var isHeaderInformationVisible: Boolean = false
        set(value) {
            field = value
            updateView()
        }

    var informationValue1: String = ""
        set(value) {
            field = value

            updateView()
        }

    var informationValue2: String = ""
        set(value) {
            field = value

            updateView()
        }

    /**
     * =================
     * QuotaDetailWidgetMode
     * =================
     * */
    var quotaDetailWidgetMode = QuotaDetailWidgetMode.NORMAL
        set(value) {
            field = value
            updateView()
        }

    private var _quotaDetailWidgetMode = QuotaDetailWidgetMode.NORMAL
        set(value) {
            field = value
            if (value == QuotaDetailWidgetMode.PROFILE || value == QuotaDetailWidgetMode.FAIL) {
                _isProfileSelectorLayoutVisible = true
                _isHeaderVisible = false
            } else {
                _isProfileSelectorLayoutVisible = false
            }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Avatar (Profile)
     * =================
     * */
    private var _isProfileSelectorLayoutVisible = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, profileSelectorView)
            _isHeaderHorizontalLineVisible = (value || _isHeaderVisible) && items.isNotEmpty()
        }

    var profileName = ""
        set(value) {
            field = value
            updateView()
        }

    private var _profileName = ""
        set(value) {
            field = value
            profileSelector.name = profileName
        }

    var profileId = ""
        set(value) {
            field = value
            updateView()
        }

    private var _profileId = ""
        set(value) {
            field = value
            profileSelector.id = profileId
        }

    var profileMode: ProfileMode? = null
        set(value) {
            field = value
        }

    private var _profileMode: ProfileMode = ProfileMode.INITIAL
        set(value) {
            field = value
            profileSelector.profileMode = value
        }

    var profileAvatar = ""
        set(value) {
            field = value
            updateView()
        }

    private var _profileAvatar = ""
        set(value) {
            field = value
            if (value.isEmpty()) {
                _profileMode = profileMode ?: ProfileMode.INITIAL
            } else {
                _profileMode = profileMode ?: ProfileMode.AVATAR
                profileSelector.avatar = value
            }
        }

    /**
     * =================
     * IsRecurring flag
     * =================
     * */
    var isRecurring = false
        set(value) {
            field = value
            updateView()
        }

    /**
     * =================
     * QuotaDetailType
     * =================
     * */
    var quotaDetailType = QuotaDetailType.PACKAGE_CANCELLABLE
        set(value) {
            field = value
            updateView()
        }

    private var _quotaDetailType = QuotaDetailType.PACKAGE_CANCELLABLE
        set(value) {
            field = value
            familyPlanAddSlot()

            when (value) {
                QuotaDetailType.CUSTOM -> custom()
                QuotaDetailType.PACKAGE_CANCELLABLE -> basicPackage(true)
                QuotaDetailType.PACKAGE_NON_CANCELLABLE, QuotaDetailType.FAMILY_PLAN_NON_UPGRADABLE -> basicPackage(
                    false
                )
                QuotaDetailType.EXPIRATION_UNLIMITED -> expirationUnlimited()
                QuotaDetailType.BOOSTER_RECURRING -> boosterRecurring()
                QuotaDetailType.BOOSTER_ONE_TIME, QuotaDetailType.BASIC_PLAN_SWITCHED -> basicPlanPackage(
                    false
                )
                QuotaDetailType.BASIC_PLAN_SWITCHABLE -> basicPlanPackage(true)
                QuotaDetailType.CONTRACT_PLAN -> contractPlan()
                QuotaDetailType.ROAMING_ACTIVE -> roamingActiveStatusPackage(true)
                QuotaDetailType.ROAMING_INACTIVE -> roamingActiveStatusPackage(false)
                QuotaDetailType.GROUPED_BOOSTER_NON_CANCELLABLE, QuotaDetailType.GROUPED_ADDON_NON_CANCELLABLE -> groupedBoosterPackage(
                    false
                )
                QuotaDetailType.GROUPED_BOOSTER_CANCELLABLE -> groupedBoosterPackage(true)
                QuotaDetailType.FTTH_UPGRADABLE -> ftthUpgradable()
                QuotaDetailType.FAMILY_PLAN_CANCELLABLE -> familyPlanPackage(true)
                QuotaDetailType.FAMILY_PLAN_NON_CANCELLABLE -> familyPlanPackage(false)
                QuotaDetailType.FAMILY_PLAN_MEMBER_DETAIL -> familyPlanMemberDetail()
                QuotaDetailType.FAMILY_PLAN_UPGRADABLE -> familyPlanUpgradable()
                QuotaDetailType.FAMILY_PLAN_ADD_SLOT -> familyPlanAddSlot(true)
            }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Status flag
     * =================
     * */
    var isActive = false
        set(value) {
            field = value
            updateView()
        }

    private var _isActive = false
        set(value) {
            field = value

            if (value) {
                statusFlagView.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.mud_palette_basic_green)
                statusFlagText = statusFlagActiveText
                    ?: context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_active)
            } else {
                statusFlagView.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.mud_palette_basic_medium_grey)
                statusFlagText = statusFlagInactiveText
                    ?: context.getString(R.string.organism_roaming_quota_summary_dashboard_widget_status_flag_inactive)
            }
        }

    private var statusFlagText = ""
        set(value) {
            field = value
            statusLabelView.text = value
        }

    var statusFlagActiveText: String? = null
        set(value) {
            field = value
            updateView()
        }

    var statusFlagInactiveText: String? = null
        set(value) {
            field = value
            updateView()
        }

    var isStatusFlagVisible = false
        set(value) {
            field = value
            updateView()
        }

    private var _isStatusFlagVisible = false
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, statusFlagView)

            if (topLeftTitleView.visibility == View.GONE && statusFlagView.visibility == View.GONE) {
                topLayout.visibility = View.GONE
            } else {
                topLayout.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Icon arrow right header
     * =================
     * */
    var isHeaderIconRightVisible = false
        set(value) {
            field = value
            updateView()
        }

    private var _isHeaderIconRightVisible = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, iconRightArrow)
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Icon arrow right header
     * =================
     * */
    var isTrashIconVisible = false
        set(value) {
            field = value
            updateView()
        }

    private var _isTrashIconVisible = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, trashIconView)
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Top left title
     * =================
     * */
    var topLeftTitle = ""
        set(value) {
            field = value
            updateView()
        }

    private var _topLeftTitle = ""
        set(value) {
            field = value

            topLeftTitleView.text = value
            IsEmptyUtil.setVisibility(value, topLeftTitleView)

            if (topLeftTitleView.visibility == View.GONE && statusFlagView.visibility == View.GONE) {
                topLayout.visibility = View.GONE
            } else {
                topLayout.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Subtitle
     * =================
     * */
    var subtitle = ""
        set(value) {
            field = value
            updateView()
        }

    private var _subtitle = ""
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, subtitleView)
            subtitleView.text = value
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Name
     * =================
     * */
    var name = ""
        set(value) {
            field = value
            updateView()
        }

    private var _name = ""
        set(value) {
            field = value

            nameView.text = value
            nameViewOther.text = value
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Icon image
     * =================
     * */
    var iconImage = ""
        set(value) {
            field = value
            updateView()
        }

    private var _iconImage = ""
        set(value) {
            field = value

            iconView.imageSource = value
            iconViewSmall.imageSource = value
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Quota validity
     * =================
     * */

    private var _isValidityLayoutVisible = true
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, validityLayout)
        }

    var iconViewValiditySource: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _iconViewValiditySource: String = ""
        set(value) {
            field = value
            iconViewValidity.imageSourceType = ImageSourceType.BASE64
            iconViewValidity.imageSource = value
            _isValidityLayoutVisible = getValidityLayoutVisibility()
        }

    private fun getValidityLayoutVisibility(): Boolean {
        return _iconViewValiditySource.isNotEmpty() || _validityLabelText.isNotEmpty() || _validityDateText.isNotEmpty()
    }

    var validityLabelDefault: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _validityLabelDefault: String? = null
        set(value) {
            field = value
            _validityLabelText = getValidityLabelText(value, validityLabelRecurring)
        }

    var validityLabelRecurring: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _validityLabelRecurring: String? = null
        set(value) {
            field = value
            _validityLabelText = getValidityLabelText(_validityLabelDefault, value)
        }

    private fun getValidityLabelText(
        validityLabelDefault: String?,
        validityLabelRecurring: String?
    ): String {
        return if (isRecurring) {
            validityLabelRecurring
                ?: context.getString(R.string.organism_quota_detail_dashboard_widget_extended_detail_label)
        } else {
            validityLabelDefault
                ?: context.getString(R.string.organism_quota_detail_dashboard_widget_active_until_detail_label)
        }
    }

    private var _validityLabelText = ""
        set(value) {
            field = value
            validityLabel.text = value
            _isValidityLayoutVisible = getValidityLayoutVisibility()
        }

    var validityDateText: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _validityDateText = ""
        set(value) {
            field = value
            validityDate.text = value
            _isValidityLayoutVisible = getValidityLayoutVisibility()
        }

    var defaultValidityUnlimitedDateText: String? = null
        set(value) {
            field = value
            updateView()
        }

    var isDateFormatExpiredAt = false
        set(value) {
            field = value
            updateView()
        }

    var slotFee = ""
        set(value) {
            field = value
            updateView()
        }

    private var _slotFee = ""
        set(value) {
            field = value
            slotFeeLayout.visibility = if (value.isNotEmpty()) View.VISIBLE else View.GONE
            slotPrice.text = value
        }

    var slotFeeLabel: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _slotFeeLabel = ""
        set(value) {
            field = value
            slotLabel.text =
                if (value.isNotEmpty()) value else context.getString(R.string.organism_quota_detail_dashboard_widget_slot_fee)
        }

    var slotFeeIcon: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _slotFeeIcon = ""
        set(value) {
            field = value

            iconViewSlot.imageSourceType = ImageSourceType.BASE64
            iconViewSlot.imageSource =
                if (value.isNotEmpty()) value else "iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAYAAABV7bNHAAAAAXNSR0IArs4c6QAAAERlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAASKADAAQAAAABAAAASAAAAACQMUbvAAAD/0lEQVR4Ae2avWpVURCF4yWmEEtRBJEklQ+QQrEIBi18hChaioW+Q55CsY82dmorGAu10s7SRIUoChZqYfxfHzhhszPn5M79M7mZBcPeZ/bstWdW9vnLuRMTiVQgFUgFUoFUIBVIBVKBVCAVSAVSgVQgFUgF9qQCHVW9KLsnW5f9lP3ZZUbO5E4N1EJNA8GsWJ7Ldpsg2+VLTdTWFyD4INtusd06Tm09i9TR5BdjLI79UdlJ1BrGBc0wkrr9pbEfhdXjHHPOlzHD7DddE8s1ydnLEx+1hnFfM2rCV/LNyyYLtjknbkO+A0XMsLusxZp1vuRmIGdyX5XVcdQaBlf8muiMw7LsxD1y4obtYs06X3KrsSBHHUetYXhbcn/FwmLe9r5axY3ikDXrwsmNHEtM6aCOo9YwahKOS8zowLvDfZJ/lKeX5cSarF3nTY7kWqKOqWsrYxv7bSTnNOujzIu53sg4/AHW9nIiV3I2eDE21nXrkZzW7Lsy7/Qj/omsp1um5g0CrE0OXu7kTO7U4I3LvRX7tro2PZBE8EbBJ2XvIpMUS1GnZGdlx2Tgreyh7KnstyyCowp+JjsemaTYNi1cKk/lJt+aGE64LO3O8xp+KWviZYyYKMhlTdbE6/mja3RNviLmI2H2iYklzfES9XzERkFOj2Uen+eL8m9L/F6MV2Thrak5TRdTL3Hz9XLxJzdyJFfjaWoVEoNH9FkUD2SXZFMxus3oafW+yTz+Nh9zmNsLyPWyjNypwVtH7hg8kskYhRt9U16Pm1P12j+j78XckL9fUIPHHeYdCEm1Klueu1zNfUs+xgz08dVxzC3jLD7a1rwchzEQkmrVwzqueb/Ld7CK4xAfb+N1PBz9oubk2AXPIKPEIWcxnp++On58rx2/x+GEDd/VtcqBVLwdxC7ZsTuorbZhCMT1Y6yvQXkXK7aUt4PG4Tnoi2r0aitK767rkZS+fJJuULoUif6KbJjvYrzNL8mi+O/vYqVQa8qeN+godvzbfNtTKQJEwPPMnv9/0J77j2LbDilPIetbfP5PWkqYKGVrAtHOyHbLV41pEi5Q1mT9Yri7rveP+fwuVmi3rr6pa+1CMW7dZSeOW/+owZqWp7XkVoMabNxaag3D+za/KpZ5WfnKMadjW8jaDflG+fGQtVjT1reW3AzkPC9bldm4tY3f5ttu8/zi4bbMAw9vmKEUzHx2itpxty1JR0EdXg58ejZ01ME8XJTzjjfQ5oOM386YyuPa9vz7IMSblXl3qnERi9qosS9AMI47iZr6FseU7aizKOMXolzxvZ+87PRdRc7kTg3UQk2JVCAVSAVSgVQgFUgFUoFUIBVIBVKBVCAVSAVSgVQgFUgFBqjAX850jnR/S0kEAAAAAElFTkSuQmCC"
        }

    var disableCard: Boolean? = false
        set(value) {
            field = value
            updateView()
        }

    private var _disableCard = false
        set(value) {
            field = value

            layoutContainer.alpha = if (value) 0.5f else 1f
        }

    var disableDetailItem: Boolean? = false
        set(value) {
            field = value
            updateView()
        }

    private var _disableDetailItem = false
        set(value) {
            field = value
            validityLayout.alpha = if (value) 0.5f else 1f
            slotFeeLayout.alpha = if (value) 0.5f else 1f
        }

    var isValidateDateVisible: Boolean? = false
        set(value) {
            field = value
            updateView()
        }

    private var _isValidateDateVisible = false
        set(value) {
            field = value
            validityLayout.visibility = if (value) View.GONE else View.VISIBLE
        }

    var expiredAt: Long = 0
        set(value) {
            field = value
            updateView()
        }

    private var _expiredAt: Long = 0
        set(value) {
            field = value
            val expiredDays = convertToDays(value)
            val format = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
            val formatTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val date = format.format(value * 1000L)
            val time = formatTime.format(value * 1000L)
            val graceDate = if (format.format(Date()) == date) time else date

            _validityDateText =
                if (isDateFormatExpiredAt) { // if needed to be date format, but remaining contract month
                    date
                } else {
                    when {
                        value == 0L -> {
                            defaultValidityUnlimitedDateText
                                ?: context.getString(R.string.organism_quota_detail_dashboard_widget_expiration_unlimited)
                        }
                        expiredDays < 0 -> {
                            graceDate
                        }
                        expiredDays < 1 -> {
                            time
                        }
                        expiredDays.toInt() == 1 -> {
                            if (!isRecurring) { // requirement as per Feb, 26 2021 for OTC
                                graceDate
                            } else {
                                context.getString(R.string.organism_quota_detail_dashboard_widget_expirated_tomorrow)
                            }
                        }
                        expiredDays < 8 -> {
                            if (!isRecurring) { // requirement as per Feb, 26 2021 for OTC
                                graceDate
                            } else {
                                "$expiredDays ${context.getString(R.string.organism_quota_detail_dashboard_widget_expirated_days)}"
                            }
                        }
                        else -> {
                            graceDate
                        }
                    }
                }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Contract plan validity
     * =================
     * */
    var remainingContractMonthStr: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _remainingContractMonthStr = ""
        set(value) {
            field = value
            remainingContractMonthDuration.text = value
        }

    var remainingContractMonth: Int = -1 // month
        set(value) {
            field = value
            updateView()
        }

    private var _remainingContractMonth: Int = -1 // month
        set(value) {
            field = value
            if (value < 0) {
                remainingContractMonthLayout.visibility = View.GONE
            } else {
                remainingContractMonthLayout.visibility = View.VISIBLE

                _remainingContractMonthStr = if (value == 0) {
                    val countDays = convertToDays(_expiredAt)
                    if (countDays == 0L) {
                        val todayUnit =
                            context.getString(R.string.organism_quota_detail_dashboard_widget_expirated_today)
                        todayUnit
                    } else {
                        val dayUnit =
                            context.getString(R.string.organism_quota_detail_dashboard_widget_expirated_days)
                        "$countDays $dayUnit"
                    }
                } else {
                    val monthUnit =
                        context.getString(R.string.organism_quota_detail_dashboard_widget_expirated_months)
                    "$value $monthUnit"
                }
            }
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Primary button
     * =================
     * */

    /**
     * Configure red style button
     * */
    var isPrimaryButtonRedStyleVisible = true
        set(value) {
            field = value
            updateView()
        }

    private var _isPrimaryButtonRedStyleVisible = false
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, cancelButtonView)
            _isPrimaryButtonLayoutVisible =
                value || _isPrimaryButtonSecondaryStyleVisible || _isSolidButtonVisible
        }

    var primaryButtonRedStyleText: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _primaryButtonRedStyleText =
        context.getString(R.string.organism_quota_detail_dashboard_widget_cancel_button_label)
        set(value) {
            field = value
            cancelButtonView.text = value
        }

    var isPrimaryButtonRedStyleEnabled = true
        set(value) {
            field = value
            updateView()
        }

    private var _isPrimaryButtonRedStyleEnabled = true
        set(value) {
            field = value
            cancelButtonView.isEnabled = value
        }

    /**
     * Configure secondary style button
     * */
    var isPrimaryButtonSecondaryStyleVisible = false
        set(value) {
            field = value
            updateView()
        }

    private var _isPrimaryButtonSecondaryStyleVisible = false
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, switchPlanButtonView)
            _isPrimaryButtonLayoutVisible =
                value || _isPrimaryButtonRedStyleVisible || _isSolidButtonVisible
        }

    var primaryButtonSecondaryStyleText: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _primaryButtonSecondaryStyleText =
        context.getString(R.string.organism_quota_detail_dashboard_widget_switch_plan_button_label)
        set(value) {
            field = value
            switchPlanButtonView.text = value
        }

    var isPrimaryButtonSecondaryStyleEnabled = true
        set(value) {
            field = value
            updateView()
        }

    private var _isPrimaryButtonSecondaryStyleEnabled = true
        set(value) {
            field = value
            switchPlanButtonView.isEnabled = value
        }

    /**
     * Configure solid button
     * */
    var isSolidButtonVisible = false
        set(value) {
            field = value
            updateView()
        }

    private var _isSolidButtonVisible = false
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, solidButtonView)
            _isPrimaryButtonLayoutVisible =
                value || _isPrimaryButtonRedStyleVisible || _isPrimaryButtonSecondaryStyleVisible
        }

    var solidButtonText: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _solidButtonText = ""
        set(value) {
            field = value
            solidButtonView.text = value
        }

    var isSolidButtonEnabled = true
        set(value) {
            field = value
            updateView()
        }

    private var _isSolidButtonEnabled = true
        set(value) {
            field = value
            solidButtonView.isEnabled = value
        }

    /**
     * Configure primary button layout
     * */
    private var _isPrimaryButtonLayoutVisible = true
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, primaryButtonLayout)
            _isButtonLayoutSeparatorVisible = value && _isSecondaryButtonVisible
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Secondary button
     * =================
     * */

    /**
     * Configure secondary button
     * */
    var isSecondaryButtonVisible = false
        set(value) {
            field = value
            updateView()
        }

    private var _isSecondaryButtonVisible = false
        set(value) {
            field = value

            IsEmptyUtil.setVisibility(value, secondaryButtonLayout)
            _isButtonLayoutSeparatorVisible = value && _isPrimaryButtonLayoutVisible
        }

    var secondaryButtonText: String? = null
        set(value) {
            field = value
            updateView()
        }

    private var _secondaryButtonText =
        context.getString(R.string.organism_quota_detail_dashboard_widget_switch_plan_button_label)
        set(value) {
            field = value
            secondaryButtonView.text = value
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Invisible separator between primary and secondary
     * =================
     * */
    private var _isButtonLayoutSeparatorVisible = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, lineView)
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Bonus
     * =================
     * */
    var hasBonus = false
        set(value) {
            field = value
            updateView()
        }

    private var _hasBonus = false
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, bonusCardView)
        }

    var bonusTitle = ""
        set(value) {
            field = value
            updateView()
        }

    private var _bonusTitle = ""
        set(value) {
            field = value
            bonusCardView.title = value
        }


    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Bottom
     * =================
     * */
    var bottomStatusDescription = ""
        set(value) {
            field = value
            updateView()
        }

    private var _bottomStatusDescription = ""
        set(value) {
            field = value

            bottomStatus.text = value
            IsEmptyUtil.setVisibility(value, bottomLayout)
        }

    var bottomNoteDescription = ""
        set(value) {
            field = value
            updateView()
        }

    private var _bottomNoteDescription = ""
        set(value) {
            field = value

            bottomNoteStatus.text = value
            IsEmptyUtil.setVisibility(value, bottomNoteLayout)
        }

    var bottomNoteDescriptionError = false
        set(value) {
            field = value
            updateView()
        }

    private var _bottomNoteDescriptionError = false
        set(value) {
            field = value

            ivWarningNoteStatus.imageSource = ContextCompat.getDrawable(
                context,
                if (value) {
                    R.drawable.ic_icon_system_warning_red
                } else {
                    R.drawable.ic_icon_system_warning
                }
            )

            bottomNoteStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (value)
                        R.color.mud_palette_basic_red
                    else
                        R.color.mud_palette_basic_dark_grey
                )
            )
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Quota action layout
     * =================
     * */
    var isQuotaActionLayoutVisible = true
        set(value) {
            field = value
            updateView()
        }

    private var _isQuotaActionLayoutVisible = true
        set(value) {
            field = value
            IsEmptyUtil.setVisibility(value, quotaActionLayout)
        }

    // ----------------------------------------------------------------------------------
    /**
     * =================
     * Fail Layout
     * =================
     * */

    var failTitle =
        resources.getString(R.string.organism_quota_detail_widget_quota_breakdown_failed_text)
        set(value) {
            field = value
            failTitleView.text = value
        }

    // ----------------------------------------------------------------------------------

    var failedButtonTitle =
        resources.getString(R.string.organism_quota_detail_widget_quota_breakdown_failed_button_text)
        set(value) {
            field = value
            failButtonView.text = value
        }

    // ----------------------------------------------------------------------------------

    var onFailedButtonClicked: (() -> Unit)? = null
        set(value) {
            field = value
            failButtonView.setOnClickListener {
                onFailedButtonClicked?.let { it() }
            }
        }

    var transferQuotaButtonTitle = resources.getString(R.string.transfer_quota_button_title)
        set(value) {
            field = value
            setUpTransferQuota()
        }

    var transferQuotaInformationTitle =
        resources.getString(R.string.transfer_quota_information_title)
        set(value) {
            field = value
            setUpTransferQuota()
        }

    var showTransferQuotaBeard = false
        set(value) {
            field = value
            setUpTransferQuota()
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------


    /**
     * =================
     * Action listeners
     * =================
     * */
    var onStopButtonPress: (() -> Unit)? = null
    var onChangePlanPress: (() -> Unit)? = null
    var onBuyAgainPlanPress: (() -> Unit)? = null
    var onHeaderLayoutPress: (() -> Unit)? = null
    var onBonusCardPress: (() -> Unit)? = null
    var onTrashIconClick: (() -> Unit)? = null
    var onSolidButtonPress: (() -> Unit)? = null
    var onTransferQuotaButtonPress: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_quota_detail_widget_quota_breakdown, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.QuotaBreakdownQuotaDetailWidgetAttr)
        typedArray.run {
            topLeftTitle =
                getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_topLeftTitle) ?: ""
            subtitle =
                getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_subtitle) ?: ""
            bottomStatusDescription =
                getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_bottomStatusDescription)
                    ?: ""
            bottomNoteDescription =
                getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_bottomNoteDescription)
                    ?: ""
            name = getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_name) ?: ""
            iconImage = getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_iconImage) ?: ""
            isRecurring =
                getBoolean(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_isRecurring, false)
            expiredAt =
                getInt(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_expiredAt, 0).toLong()
            remainingContractMonthStr =
                getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_remainingContractMonthStr)
                    ?: ""
            remainingContractMonth = getInt(
                R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_remainingContractMonth,
                -1
            )
            hasBonus = getBoolean(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_hasBonus, false)
            bonusTitle = getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_bonusTitle) ?: ""
            quotaDetailType = QuotaDetailType.values()[getInt(
                R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_quotaDetailType,
                0
            )]
            profileName =
                getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_profileName) ?: ""
            profileId = getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_profileId) ?: ""
            profileAvatar =
                getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_imageSource) ?: ""
            quotaDetailWidgetMode = QuotaDetailWidgetMode.values()[getInt(
                R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_quotaDetailWidgetMode,
                0
            )]
            primaryButtonSecondaryStyleText =
                getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_primaryButtonSecondaryStyleText)
                    ?: ""
            slotFee = getString(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_slotFee) ?: ""
            disableDetailItem =
                getBoolean(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_disableDetailItem, false)
            isValidateDateVisible =
                getBoolean(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_isValidateDateVisible, false)
            disableCard =
                getBoolean(R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_disableCard, false)
            isTrashIconVisible = getBoolean(
                R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_isTrashIconVisible,
                false
            )
            isHeaderInformationVisible = getBoolean(
                R.styleable.QuotaBreakdownQuotaDetailWidgetAttr_isInformationHeader,
                false
            )
        }
        typedArray.recycle()

        itemContainerView.apply {
            addItemDecoration(ListUtil.getListGapDecorator(context, 16))
            adapter = recycleAdapter.also { it.items = items }
        }

        headerLayout.setOnClickListener {
            if (quotaDetailType == QuotaDetailType.GROUPED_BOOSTER_CANCELLABLE ||
                quotaDetailType == QuotaDetailType.GROUPED_BOOSTER_NON_CANCELLABLE ||
                quotaDetailType == QuotaDetailType.GROUPED_ADDON_NON_CANCELLABLE ||
                quotaDetailType == QuotaDetailType.FAMILY_PLAN_CANCELLABLE ||
                quotaDetailType == QuotaDetailType.FAMILY_PLAN_NON_CANCELLABLE ||
                quotaDetailType == QuotaDetailType.CUSTOM
            )
                onHeaderLayoutPress?.let { it() }
        }

        cancelButtonView.setOnClickListener {
            onStopButtonPress?.let { it() }
        }

        switchPlanButtonView.setOnClickListener {
            onChangePlanPress?.let { it() }
        }

        secondaryButtonView.setOnClickListener {
            onBuyAgainPlanPress?.let { it() }
        }

        bonusCardView.onItemPress = {
            onBonusCardPress?.invoke()
        }

        trashIconView.setOnClickListener {
            onTrashIconClick?.invoke()
        }

        solidButtonView.setOnClickListener {
            onSolidButtonPress?.invoke()
        }

        informationItemView.apply {
            imageWarning = getDrawable(context, R.drawable.ic_neutral_information_icon)
            isColorWhite = true
        }

        shareQuotaButton.setOnClickListener {
            onTransferQuotaButtonPress?.invoke()
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child !is QuotaDetailItem) {
            super.addView(child, index, params)
        } else {
            items.add(
                QuotaDetailItem.Data(
                    child.name,
                    child.quotaType,
                    child.iconImage,
                    child.information,
                    child.remaining,
                    child.total,
                    child.isUnlimited,
                    child.isFup,
                    child.fupText,
                    child.fupLimitRule,
                    child.lowThreshold,
                    child.disableDetailItem
                )
            )

            itemContainerView.visibility = View.VISIBLE
            headerHorizontalLine.visibility = View.VISIBLE
        }
    }

    private fun convertToDays(timestamp: Long): Long {
        // please use timeStamp in SECOND not milisecond
        val currentTimestamp = System.currentTimeMillis() / 1000

        return (timestamp - currentTimestamp) / 86400


    }

    /**
     * =================================
     * Update view (keep sync)
     * =================================
     * */
    private fun updateView() {
        setValidity()
        setBasicInfo()
        setButton()
        setHeader()
        setBottomStatusDescription()
        setBottomStatusDescriptionError()
        setBottomNoteDescription()
        setQuotaActionLayout()
        setStatusFlag()
        setAvatar()
        setFailedMode()
        setSlotFee()
        setEnable()
        setValidateDateVisibility()
        setQuotaDetailType() // setQuotaDetailType() should be at the end
        setInformationHeader()
    }

    private fun setFailedMode() {
        if (quotaDetailWidgetMode == QuotaDetailWidgetMode.FAIL) {
            failLayout.visibility = View.VISIBLE
            normalModeLayout.visibility = View.GONE
            setAvatar()
        } else {
            failLayout.visibility = View.GONE
            normalModeLayout.visibility = View.VISIBLE
        }
    }

    private fun setValidity() {
        _expiredAt = expiredAt
        iconViewValiditySource?.also {
            _iconViewValiditySource = it
        }
        validityDateText?.also {
            _validityDateText = it
        }
        _validityLabelDefault = validityLabelDefault
        _validityLabelRecurring = validityLabelRecurring
        remainingContractMonthStr?.also {
            _remainingContractMonthStr = it
        }
        _remainingContractMonth = remainingContractMonth
    }

    private fun setEnable() {
        disableDetailItem?.also {
            _disableDetailItem = it
        }
        disableCard?.also {
            _disableCard = it
        }
    }

    private fun setValidateDateVisibility() {
        isValidateDateVisible?.also {
            _isValidateDateVisible = it
        }
    }

    private fun setSlotFee() {
        _slotFee = slotFee
        slotFeeLabel?.also {
            _slotFeeLabel = it
        }
        slotFeeIcon?.also {
            _slotFeeIcon = it
        }
    }

    private fun setBasicInfo() {
        _name = name
        _iconImage = iconImage
        _subtitle = subtitle
    }

    private fun setButton() {
        primaryButtonRedStyleText?.also {
            _primaryButtonRedStyleText = it
        }
        primaryButtonSecondaryStyleText?.also {
            _primaryButtonSecondaryStyleText = it
        }
        secondaryButtonText?.also {
            _secondaryButtonText = it
        }
        solidButtonText?.also {
            _solidButtonText = it
        }
        _isPrimaryButtonRedStyleVisible = isPrimaryButtonRedStyleVisible
        _isPrimaryButtonSecondaryStyleVisible = isPrimaryButtonSecondaryStyleVisible
        _isSecondaryButtonVisible = isSecondaryButtonVisible
        _isPrimaryButtonRedStyleEnabled = isPrimaryButtonRedStyleEnabled
        _isPrimaryButtonSecondaryStyleEnabled = isPrimaryButtonSecondaryStyleEnabled
        _isSolidButtonVisible = isSolidButtonVisible
        _isSolidButtonEnabled = isSolidButtonEnabled
        _hasBonus = hasBonus
        _bonusTitle = bonusTitle
    }

    private fun setHeader() {
        _topLeftTitle = topLeftTitle
        _isHeaderIconRightVisible = isHeaderIconRightVisible
        _isHeaderVisible = isHeaderVisible
        _isTrashIconVisible = isTrashIconVisible
    }

    private fun setBottomStatusDescription() {
        _bottomStatusDescription = bottomStatusDescription
    }

    private fun setBottomStatusDescriptionError() {
        _bottomNoteDescriptionError = bottomNoteDescriptionError
    }

    private fun setBottomNoteDescription() {
        _bottomNoteDescription = bottomNoteDescription
    }

    private fun setQuotaActionLayout() {
        _isQuotaActionLayoutVisible = isQuotaActionLayoutVisible
    }

    private fun setStatusFlag() {
        _isActive = isActive
        _isStatusFlagVisible = isStatusFlagVisible // default hide
    }

    private fun setAvatar() {
        _profileName = profileName
        _profileId = profileId
        _profileAvatar = profileAvatar
        _quotaDetailWidgetMode = quotaDetailWidgetMode
    }

    private fun setQuotaDetailType() {
        _quotaDetailType =
            quotaDetailType // should be at the most bottom part to override the default setup above
    }

    /**
     * =================================
     * Quota detail predefined type list
     * =================================
     * */
    private fun custom() {
        // No strict rules
    }

    private fun basicPackage(_isPrimaryButtonRedStyleVisible: Boolean) {
        this._isPrimaryButtonRedStyleVisible = _isPrimaryButtonRedStyleVisible
        _isPrimaryButtonSecondaryStyleVisible = false
        _primaryButtonRedStyleText =
            context.getString(R.string.organism_quota_detail_dashboard_widget_cancel_button_label)
    }

    private fun expirationUnlimited() {
        _isPrimaryButtonRedStyleVisible = false
        _isPrimaryButtonSecondaryStyleVisible = false
        _expiredAt = 0L
    }

    private fun boosterRecurring() {
        _isPrimaryButtonRedStyleVisible = true
        _isPrimaryButtonSecondaryStyleVisible = false
//        _validityLabelDefault = context.getString(R.string.organism_quota_detail_dashboard_widget_ended_detail_label)
//        _validityLabelRecurring = context.getString(R.string.organism_quota_detail_dashboard_widget_extended_detail_label)
        _primaryButtonRedStyleText =
            context.getString(R.string.organism_quota_detail_dashboard_widget_cancel_booster_button_label)
    }

    private fun basicPlanPackage(isSwitchable: Boolean) {
        _isPrimaryButtonRedStyleVisible = false
        _isPrimaryButtonSecondaryStyleVisible = isSwitchable
        _primaryButtonSecondaryStyleText =
            context.getString(R.string.organism_quota_detail_dashboard_widget_switch_plan_button_label)
//        _validityLabelDefault = context.getString(R.string.organism_quota_detail_dashboard_widget_ended_detail_label)
//        _validityLabelRecurring = context.getString(R.string.organism_quota_detail_dashboard_widget_extended_detail_label)
    }

    private fun contractPlan() {
        _isPrimaryButtonRedStyleVisible = false
        _isPrimaryButtonSecondaryStyleVisible = false
        _isQuotaActionLayoutVisible = false
        _topLeftTitle =
            context.getString(R.string.organism_quota_detail_dashboard_widget_contract_plan)
    }

    private fun roamingActiveStatusPackage(_isActive: Boolean) {
        _isPrimaryButtonRedStyleVisible = false
        _isPrimaryButtonSecondaryStyleVisible = false
        this._isActive = _isActive
        _isStatusFlagVisible = true
        _validityLabelDefault =
            context.getString(R.string.organism_quota_detail_dashboard_widget_valid_until_detail_label)
    }

    private fun groupedBoosterPackage(_isPrimaryButtonRedStyleVisible: Boolean) {
        this._isPrimaryButtonRedStyleVisible = _isPrimaryButtonRedStyleVisible
        _isPrimaryButtonSecondaryStyleVisible = false
        _primaryButtonRedStyleText =
            context.getString(R.string.organism_quota_detail_dashboard_widget_cancel_booster_button_label)
//        _validityLabelDefault = context.getString(R.string.organism_quota_detail_dashboard_widget_ended_detail_label)
//        _validityLabelRecurring = context.getString(R.string.organism_quota_detail_dashboard_widget_extended_detail_label)
        _isHeaderIconRightVisible = true
    }

    private fun ftthUpgradable() {
        _isPrimaryButtonRedStyleVisible = false
        _isPrimaryButtonSecondaryStyleVisible = true
        _primaryButtonSecondaryStyleText =
            context.getString(R.string.organism_quota_detail_dashboard_widget_upgrade_plan_button_label)
//        _validityLabelDefault = context.getString(R.string.organism_quota_detail_dashboard_widget_ended_detail_label)
//        _validityLabelRecurring = context.getString(R.string.organism_quota_detail_dashboard_widget_extended_detail_label)
    }

    private fun familyPlanPackage(_isPrimaryButtonRedStyleVisible: Boolean) {
        this._isPrimaryButtonRedStyleVisible = _isPrimaryButtonRedStyleVisible
        _isPrimaryButtonSecondaryStyleVisible = false
        _isHeaderIconRightVisible = true
    }

    private fun familyPlanMemberDetail() {
        _isPrimaryButtonRedStyleVisible = false
        _isPrimaryButtonSecondaryStyleVisible = false
        _isHeaderVisible = false
    }

    private fun familyPlanUpgradable() {
        _isPrimaryButtonRedStyleVisible = true
        _isPrimaryButtonSecondaryStyleVisible = false
        _isSecondaryButtonVisible = true
        _secondaryButtonText =
            context.getString(R.string.organism_quota_detail_dashboard_widget_change_package)
    }

    private fun familyPlanAddSlot(isFamPlanAddSlot: Boolean? = false) {

        val params = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        isFamPlanAddSlot?.let {
            if (it) {
                familyPlanMemberDetail()
            }
            params.setMargins(0, if (it) 0 else 56, 0, 0)
            layoutContainer.layoutParams = params
        }
    }

    private fun setInformationHeader() {
        informationHeaderLayout.visibility =
            if (isHeaderInformationVisible) View.VISIBLE else View.GONE
        informationLabel1.visibility =
            if (informationValue1.isNotEmpty()) View.VISIBLE else View.GONE
        informationLabel2.visibility =
            if (informationValue2.isNotEmpty()) View.VISIBLE else View.GONE
        informationLabel1.text = informationValue1
        informationLabel2.text = informationValue2

    }

    private fun setUpTransferQuota() {
        shareQuotaButton.text = transferQuotaButtonTitle
        informationItemView.title = transferQuotaInformationTitle
        transferQuotaLayout.visibility = if (showTransferQuotaBeard) View.VISIBLE else View.GONE
    }
}
