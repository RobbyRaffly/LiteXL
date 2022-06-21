package com.myxlultimate.component.organism.familyPlanGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismFamilyGroupCardBinding
import com.myxlultimate.component.util.TouchFeedbackUtil
import com.myxlultimate.component.util.toGone
import com.myxlultimate.component.util.toVisible

class FamilyPlanGroupCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var _binding: OrganismFamilyGroupCardBinding? = null
    private val binding get() = _binding!!

    var groupName: String = ""
        get() = binding.tvGroupName.text.toString()
        set(value) {
            field = value
            binding.tvGroupName.text = value
        }

    var availableSlot: String = ""
        get() = binding.tvAvailableSlot.text.toString()
        set(value) {
            field = value
            binding.tvAvailableSlot.text = value
        }

    var sharedQuota: String = ""
        get() = binding.tvSharedQuota.text.toString()
        set(value) {
            field = value
            binding.tvSharedQuota.text = value
        }

    var isOrganizer: Boolean = false
        get() = binding.tvStatusMembership.text == context.getString(R.string.family_group_organizer)
        set(value) {
            field = value
            binding.tvStatusMembership.text = if (value) {
                context.getString(R.string.family_group_organizer)
            } else {
                context.getString(R.string.family_group_member)
            }
        }

    var membershipStatus: String = ""
        get() = binding.tvStatusMembership.text.toString()
        set(value) {
            field = value
            binding.tvStatusMembership.text = value
        }

    var isPrimary: Boolean = false
        get() = binding.cpGroupStatus.isVisible && binding.cpGroupStatus.text == context.getString(R.string.family_group_status_primary)
        set(value) {
            field = value
            if (value) {
                binding.cpGroupStatus.apply {
                    toVisible()
                    text = context.getString(R.string.family_group_status_primary)
                }
            } else {
                binding.cpGroupStatus.toGone()
            }
        }

    var isLowQuota: Boolean = false
        set(value) {
            field = value
            with(binding) {
                if (value) {
                    tvSharedQuota.setTextColor(ContextCompat.getColor(context, R.color.basicRed))
                    if (isOrganizer) {
                        tvAdditionalText.toVisible()
                    } else {
                        tvAdditionalText.toGone()
                    }
                } else {
                    tvSharedQuota.setTextColor(ContextCompat.getColor(context, R.color.basicBlack))
                }
            }
        }

    var isNoRemainingQuota: Boolean = false
        set(value) {
            field = value
            with(binding) {
                if (value) {
                    cpGroupStatus.apply {
                        toVisible()
                        text = context.getString(R.string.family_group_no_quota)
                        setTextColor(ContextCompat.getColorStateList(context, R.color.basicDarkGrey))
                        chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.basicLightGrey)
                    }
                    tvSharedQuota.setTextColor(ContextCompat.getColor(context, R.color.basicRed))
                    if (isOrganizer) {
                        tvAdditionalText.toVisible()
                    } else {
                        tvAdditionalText.toGone()
                    }
                } else {
                    cpGroupStatus.toGone()
                    tvSharedQuota.setTextColor(ContextCompat.getColor(context, R.color.basicBlack))
                }
            }
        }

    var isQuotaLimitExceeded: Boolean = false
        set(value) {
            field = value
            with(binding) {
                if (value) {
                    cpGroupStatus.apply {
                        toVisible()
                        text = context.getString(R.string.family_group_usage_limit_exceeded)
                        setTextColor(ContextCompat.getColorStateList(context, R.color.basicDarkGrey))
                        chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.basicLightGrey)
                    }
                } else {
                    cpGroupStatus.toGone()
                }
            }
        }

    var groupStatus: String = ""
        get() = binding.cpGroupStatus.text.toString()
        set(value) {
            field = value
            binding.cpGroupStatus.apply {
                if (value.isNotEmpty()) {
                    text = value
                    toVisible()
                }
            }
        }

    var isShowAdditionalText: Boolean = false
        get() = binding.tvAdditionalText.isVisible
        set(value) {
            field = value
            binding.tvAdditionalText.apply {
                if (value) toVisible() else toGone()
            }
        }

    var additionalText: String = context.getString(R.string.family_group_additional_text)
        get() = binding.tvAdditionalText.text.toString()
        set(value) {
            field = value
            binding.tvAdditionalText.apply {
                toVisible()
                text = value
            }
        }

    var groupState: GroupState = GroupState.NORMAL
        set(value) {
            field = value
            with(binding) {
                if (value == GroupState.NORMAL) {
                    tvStatusMembership.toVisible()
                    imgHamburger.toGone()
                } else {
                    tvStatusMembership.toGone()
                    imgHamburger.toVisible()
                }
            }
        }

    fun setOnClickListener(onClick: (View) -> Unit) {
        binding.cvMain.setOnClickListener {
            onClick.invoke(it)
        }
    }

    fun setOnBottomTextClickListener(onClick: (() -> Unit)?) {
        with(binding) {
            if (tvAdditionalText.isVisible) {
                TouchFeedbackUtil.attach(tvAdditionalText, onClick)
            }
        }
    }

    init {
        _binding = OrganismFamilyGroupCardBinding.inflate(LayoutInflater.from(context), this, true)
        attrs?.let {
            val attr = context.obtainStyledAttributes(it, R.styleable.FamilyPlanGroupCard, 0, 0)
            runCatching {
                groupName = attr.getString(R.styleable.FamilyPlanGroupCard_groupName).orEmpty()
                availableSlot = attr.getString(R.styleable.FamilyPlanGroupCard_availableSlot).orEmpty()
                sharedQuota = attr.getString(R.styleable.FamilyPlanGroupCard_sharedQuota).orEmpty()
                groupStatus = attr.getString(R.styleable.FamilyPlanGroupCard_groupStatus).orEmpty()
                membershipStatus = attr.getString(R.styleable.FamilyPlanGroupCard_statusMembership).orEmpty()
                additionalText = attr.getString(R.styleable.FamilyPlanGroupCard_additionalText) ?: context.getString(R.string.family_group_additional_text)
                isPrimary = attr.getBoolean(R.styleable.FamilyPlanGroupCard_isPrimary, false)
                isOrganizer = attr.getBoolean(R.styleable.FamilyPlanGroupCard_isOrganizer, false)
                isShowAdditionalText = attr.getBoolean(R.styleable.FamilyPlanGroupCard_isShowAdditionalText, false)
                groupState = GroupState.values()[attr.getInt(R.styleable.FamilyPlanGroupCard_groupState, 0)]
            }.onSuccess { attr.recycle() }
        }
        binding.root
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}