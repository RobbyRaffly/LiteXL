package com.myxlultimate.component.organism.akrabEditMemberCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismAkrabEditMemberCardBinding
import com.myxlultimate.component.util.TouchFeedbackUtil
import com.myxlultimate.component.util.toGone
import com.myxlultimate.component.util.toVisible

class AkrabEditMemberCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var _binding: OrganismAkrabEditMemberCardBinding? = null
    private val binding get() = _binding!!

    var limitInfoText: String = context.getString(R.string.family_akrab_change_member_limit_info)
        get() = binding.tvLimitInfo.text.toString()
        set(value) {
            field = value
            binding.tvLimitInfo.text = value
        }

    var isDisabled: Boolean = false
        set(value) {
            field = value
            with(binding) {
                if (value) {
                    tvLimitInfo.toVisible()
                    tvChangeMember.setTextColor(ContextCompat.getColorStateList(context, R.color.basicMediumGrey))
                    tvIconChangeMember.setTextColor(ContextCompat.getColorStateList(context, R.color.basicMediumGrey))
                    tvIconChangeMember.background.setTint(ContextCompat.getColor(context, R.color.basicLightGrey))
                    ivReplace.setColorFilter(ContextCompat.getColor(context, R.color.basicMediumGrey), android.graphics.PorterDuff.Mode.SRC_IN)
                } else {
                    tvLimitInfo.toGone()
                }
            }
        }

    var isPaidSlot: Boolean = false
        set(value) {
            field = value
            with(binding) {
                if (value) {
                    line2.toVisible()
                    clRemoveSlot.toVisible()
                } else {
                    line2.toGone()
                    clRemoveSlot.toGone()
                }
            }
        }

    fun setOnChangeMemberListener(onClick: () -> Unit) {
        if (!isDisabled) {
            TouchFeedbackUtil.attach(binding.clChangeMember, onClick)
        }
    }

    fun setOnRemoveMemberListener(onClick: () -> Unit) {
        TouchFeedbackUtil.attach(binding.clRemoveMember, onClick)
    }

    fun setOnRemoveSlotListener(onClick: () -> Unit) {
        if (isPaidSlot) {
            TouchFeedbackUtil.attach(binding.clRemoveSlot, onClick)
        }
    }

    init {
        _binding =
            OrganismAkrabEditMemberCardBinding.inflate(LayoutInflater.from(context), this, true)
        attrs?.let {
            val attr = context.obtainStyledAttributes(it, R.styleable.AkrabEditMemberCard, 0, 0)
            runCatching {
                limitInfoText = attr.getString(R.styleable.AkrabEditMemberCard_limitInfoText) ?: context.getString(R.string.family_akrab_change_member_limit_info)
                isDisabled = attr.getBoolean(R.styleable.AkrabEditMemberCard_isDisabled, false)
                isPaidSlot = attr.getBoolean(R.styleable.AkrabEditMemberCard_isPaidSlot, false)
            }.onSuccess { attr.recycle() }
        }
        binding.root
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}