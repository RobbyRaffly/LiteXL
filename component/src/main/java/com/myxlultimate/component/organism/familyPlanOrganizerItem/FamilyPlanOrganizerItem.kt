package com.myxlultimate.component.organism.familyPlanOrganizerItem

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.familyPlanOrganizerItem.enum.InternetQuotaLimitMode
import kotlinx.android.synthetic.main.molecule_package_benefit_item.view.*
import kotlinx.android.synthetic.main.organism_family_plan_organizer_allocate_item.view.*
import kotlinx.android.synthetic.main.organism_family_plan_organizer_allocate_item.view.statusLabelView
import kotlinx.android.synthetic.main.organism_family_plan_organizer_allocate_item.view.unlimitedFlagView
import java.util.*

class FamilyPlanOrganizerItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        var id: String = "",

        var title: String = "",
        var subtitle: String = "",
        var buttonLabel: String = "",

        var quotaAccessPermissionLabel: String = "",
        var quotaAccessPermissionCheckInit: Boolean = false,

        var quotaAccessNoLimit: String = "",
        var quotaAccessNoLimitCheckInit: Boolean = false,

        var allocateQuota: String = "",
        var allocateQuotaInitialValue: Int = 0,
        var allocateQuotaUnit: String = "",
        var allocateQuotaErrorMessage: String = "",

        var internetQuotaLimitLabel: String = "",
        var internetQuotaLimitMode: InternetQuotaLimitMode = InternetQuotaLimitMode.ON,
        var internetQuotaLimitValue: String = "",

        var isExpanded: Boolean = true,
        var isDisabled: Boolean = false,

        var parentTotalQuota: Long = 0,
        var parentRemainingQuota: Long = 0,
        var memberQuotaAllocated: Long = 0,
        var memberQuotaUsed: Long = 0,

        var isReservedMember: Boolean = false
    )

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * FIRST ROW
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Setup name and initial character name in circle, eg : "Annisa Sanjaya", "AS"
     */
    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup text for subtitle below title, eg : "Kuota Terpakai : 2GB"
     */
    var subtitle = ""
        set(value) {
            field = value
            tvUsedQuota.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup button label text for textview/button save, eg : "Save" or "Simpan"
     */
    var buttonLabel = ""
        set(value) {
            field = value
            tvSave.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Set button enabled/disable
     */
    var isButtonEnable: Boolean? = null
        set(value) {
            value?.let {
                field = it
                tvSave.isEnabled = it
            }

        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * SECOND ROW
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Setup text for textview, eg : "Izin Akses Kuota"
     */
    var quotaAccessPermissionLabel = ""
        set(value) {
            field = value
            tvQuotaAccess.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup value state init for "Izin Akses Kuota" switch
     */
    var quotaAccessPermissionCheckInit = false
        set(value) {
            field = value
            swQuotaAccess.isChecked = value
            if (value) {
                clBaseQuotaAccessNoLimit.visibility = View.VISIBLE
                clBaseQuotaAllocate.visibility = View.VISIBLE
            } else {
                clBaseQuotaAccessNoLimit.visibility = View.GONE
                clBaseQuotaAllocate.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * THIRD ROW
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Setup text for textview, eg : "Akses Kuota Tanpa Batas"
     */
    var quotaAccessNoLimit = ""
        set(value) {
            field = value
            tvQuotaAccessNoLimit.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup value state init for "Akses Kuota Tanpa Batas" switch
     */
    var quotaAccessNoLimitCheckInit = false
        set(value) {
            field = value
            swQuotaAccessNoLimit.isChecked = value
            if (value && swQuotaAccess.isChecked) {
                lineBaseQuotaAccess.visibility = View.VISIBLE
                lineBaseQuotaAccessNoLimit.visibility = View.GONE
                clBaseQuotaAllocate.visibility = View.GONE
            } else if (!value && swQuotaAccess.isChecked) {
                lineBaseQuotaAccess.visibility = View.VISIBLE
                lineBaseQuotaAccessNoLimit.visibility = View.VISIBLE
                clBaseQuotaAllocate.visibility = View.VISIBLE
            } else {
                lineBaseQuotaAccess.visibility = View.GONE
                lineBaseQuotaAccessNoLimit.visibility = View.GONE
                clBaseQuotaAllocate.visibility = View.GONE
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * FOURTH ROW
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Setup text for textview, eg : "Alokasi Data"
     */
    var allocateQuota = ""
        set(value) {
            field = value
            tvAllocateQuota.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup initial value for quota (original allocation) member in textfield, eg : 160,
     * 120, 60, etc
     */
    var allocateQuotaInitialValue = 0
        set(value) {
            field = value
            otfQuotaAllocate.text = value.toString()
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup text for unit label textview, eg : "GB"
     */
    var allocateQuotaUnit = ""
        set(value) {
            field = value
            tvUnit.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup text for error allocate textview, eg : "Melebihi batas pembagian" or "Limit
     * dibawah kuota terpakai"
     */
    var allocateQuotaErrorMessage = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                tvErrorAllocateQuota.visibility = View.VISIBLE
                tvErrorAllocateQuota.text = value
                otfQuotaAllocate.isError = true
            } else {
                tvErrorAllocateQuota.visibility = View.GONE
                otfQuotaAllocate.isError = false
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * BATAS KOUTA INTERNET COMPONENT
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Setup text for textview, eg : "Batas Kuota Internet"
     */
    var internetQuotaLimitLabel = ""
        set(value) {
            field = value
            tvLimitInternetQuota.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup mode for" "Batas Kuota Internet" for determining which base should be shown,
     * wheter "Tanpa Batas", "Dimatikan", or static value eg : "20GB"
     */
    var internetQuotaLimitMode = InternetQuotaLimitMode.ON
        set(value) {
            field = value
            when (value) {
                InternetQuotaLimitMode.ON -> {
                    tvInternetQuotaLimitValue.visibility = View.VISIBLE
                    isTurnOffQuotaView.visibility = View.GONE
                    unlimitedFlagView.visibility = View.GONE
                }
                InternetQuotaLimitMode.OFF -> {
                    tvInternetQuotaLimitValue.visibility = View.GONE
                    isTurnOffQuotaView.visibility = View.VISIBLE
                    unlimitedFlagView.visibility = View.GONE
                }
                InternetQuotaLimitMode.UNLIMITED -> {
                    tvInternetQuotaLimitValue.visibility = View.GONE
                    isTurnOffQuotaView.visibility = View.GONE
                    unlimitedFlagView.visibility = View.VISIBLE
                }
            }
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup value label for "Batas Kuota Internet" value
     */
    var internetQuotaLimitValue = ""
        set(value) {
            field = value
            when (internetQuotaLimitMode) {
                InternetQuotaLimitMode.ON -> {
                    /**
                     * Set value text label, eg : "20GB", "60GB", etc
                     */
                    tvInternetQuotaLimitValue.text = value
                }
                InternetQuotaLimitMode.OFF -> {
                    /**
                     * Set value text label, eg : "Dimatikan" or "Turn Off"
                     */
                    isTurnOffQuotaViewLabel.text = value
                }
                InternetQuotaLimitMode.UNLIMITED -> {
                    /**
                     * Set value text label, eg : "Tanpa Batas" or "Unlimited"
                     */
                    statusLabelView.text = value
                }
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * CARD STATE
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    /**
     * Setup state for the card to determining which view/row should be shown
     */
    var isExpanded = true
        set(value) {
            field = value
            if (value) {
                clBaseQuotaAccess.visibility = View.VISIBLE
                clBaseQuotaAccessNoLimit.visibility = View.VISIBLE
                clBaseQuotaAllocate.visibility = View.VISIBLE
                clBaseLimitInternetQuota.visibility = View.GONE
            } else {
                clBaseQuotaAccess.visibility = View.GONE
                clBaseQuotaAccessNoLimit.visibility = View.GONE
                clBaseQuotaAllocate.visibility = View.GONE
                clBaseLimitInternetQuota.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup state disable mode for the card
     */
    var isDisabled = false
        set(value) {
            field = value
            if (value) {
                baseLayoutDisabled.visibility = View.VISIBLE
            } else {
                baseLayoutDisabled.visibility = View.GONE
            }
        }

    var isReservedMember = false
        set(value) {
            field = value
            refreshView()
        }

    private fun refreshView() {
        if (isReservedMember || title.isEmpty()) {
            ivAvatar.visibility = View.VISIBLE
            tvInitialName.visibility = View.GONE
        } else {
            ivAvatar.visibility = View.GONE
            tvInitialName.visibility = View.VISIBLE
            tvInitialName.text = getInitialName(title)
        }
        tvName.text = title
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * QUOTA ATTRIBUTE
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    val parentTotalQuota: Long = 0

    // ----------------------------------------------------------------------------------

    val parentRemainingQuota: Long = 0

    // ----------------------------------------------------------------------------------

    val memberQuotaAllocated: Long = 0

    // ----------------------------------------------------------------------------------

    val memberQuotaUsed: Long = 0

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * VIEW LISTENER ATTRIBUTE
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var onAccessQuotaChecked: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onQuotaAccessNoLimitChecked: ((Boolean) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onAllocateQuotaChange: ((String) -> Unit)? = null

    // ----------------------------------------------------------------------------------

    var onButtonLabelClick: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_plan_organizer_allocate_item, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FamilyPlanOrganizerItem)
        typedArray.run {
            title = getString(R.styleable.FamilyPlanOrganizerItem_title) ?: ""
            subtitle = getString(R.styleable.FamilyPlanOrganizerItem_subtitle) ?: ""
            buttonLabel = getString(R.styleable.FamilyPlanOrganizerItem_buttonLabel) ?: ""

            quotaAccessPermissionLabel =
                getString(R.styleable.FamilyPlanOrganizerItem_quotaAccessPermissionLabel) ?: ""
            quotaAccessPermissionCheckInit =
                getBoolean(
                    R.styleable.FamilyPlanOrganizerItem_quotaAccessPermissionCheckInit,
                    false
                )

            quotaAccessNoLimit =
                getString(R.styleable.FamilyPlanOrganizerItem_quotaAccessNoLimit) ?: ""
            quotaAccessNoLimitCheckInit =
                getBoolean(R.styleable.FamilyPlanOrganizerItem_quotaAccessNoLimitCheckInit, false)

            allocateQuota = getString(R.styleable.FamilyPlanOrganizerItem_allocateQuota) ?: ""
            allocateQuotaInitialValue =
                getInt(R.styleable.FamilyPlanOrganizerItem_allocateQuotaInitialValue, 0)
            allocateQuotaUnit =
                getString(R.styleable.FamilyPlanOrganizerItem_allocateQuotaUnit) ?: ""
            allocateQuotaErrorMessage =
                getString(R.styleable.FamilyPlanOrganizerItem_allocateQuotaErrorMessage) ?: ""

            internetQuotaLimitLabel =
                getString(R.styleable.FamilyPlanOrganizerItem_internetQuotaLimitLabel) ?: ""
            internetQuotaLimitMode = InternetQuotaLimitMode.values()[getInt(
                R.styleable.FamilyPlanOrganizerItem_internetQuotaLimitMode,
                0
            )]
            internetQuotaLimitValue =
                getString(R.styleable.FamilyPlanOrganizerItem_internetQuotaLimitValue) ?: ""

            isExpanded = getBoolean(R.styleable.FamilyPlanOrganizerItem_isExpanded, true)
            isDisabled = getBoolean(R.styleable.FamilyPlanOrganizerItem_isDisabled, false)
            isReservedMember =
                getBoolean(R.styleable.FamilyPlanOrganizerItem_isReservedMember, false)
        }
        typedArray.recycle()

        tvSave.setOnClickListener {
            onButtonLabelClick?.invoke()
        }

        otfQuotaAllocate.setOnTextChange { onTextChanged() }

        swQuotaAccess.setOnCheckedChangeListener { a: CompoundButton, b: Boolean ->
            if (a.isPressed) // only invoke when press action occurred
                onAccessQuotaChecked?.invoke(b)

            if (b && swQuotaAccessNoLimit.isChecked.not()) {
                lineBaseQuotaAccess.visibility = View.VISIBLE
                lineBaseQuotaAccessNoLimit.visibility = View.VISIBLE
                clBaseQuotaAccessNoLimit.visibility = View.VISIBLE
                clBaseQuotaAllocate.visibility = View.VISIBLE
            } else if (b && swQuotaAccessNoLimit.isChecked) {
                lineBaseQuotaAccess.visibility = View.VISIBLE
                lineBaseQuotaAccessNoLimit.visibility = View.GONE
                clBaseQuotaAccessNoLimit.visibility = View.VISIBLE
                clBaseQuotaAllocate.visibility = View.GONE
            } else {
                lineBaseQuotaAccess.visibility = View.GONE
                lineBaseQuotaAccessNoLimit.visibility = View.GONE
                clBaseQuotaAccessNoLimit.visibility = View.GONE
                clBaseQuotaAllocate.visibility = View.GONE
            }
        }

        swQuotaAccessNoLimit.setOnCheckedChangeListener { a: CompoundButton, b: Boolean ->
            if (a.isPressed) // only invoke when press action occurred
                onQuotaAccessNoLimitChecked?.invoke(b)

            if (b) {
                lineBaseQuotaAccessNoLimit.visibility = View.GONE
                clBaseQuotaAllocate.visibility = View.GONE
            } else {
                lineBaseQuotaAccessNoLimit.visibility = View.VISIBLE
                clBaseQuotaAllocate.visibility = View.VISIBLE
            }
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    private fun getInitialName(name: String): String {
        var lastLetter = ""
        var firstLetter = ""
        var initial = ""
        if (name.isNotEmpty()) {
            firstLetter = name.substring(0, 1).toUpperCase(Locale.getDefault())
        }

        if (name.isNotEmpty() && name.split(" ").size > 1) {
            lastLetter = try {
                name.substring(name.lastIndexOf(" ") + 1, name.lastIndexOf(" ") + 2)
                    .toUpperCase(Locale.getDefault())
            } catch (err: StringIndexOutOfBoundsException) {
                ""
            }
        }
        initial = firstLetter + lastLetter
        return initial
    }

    // ----------------------------------------------------------------------------------

    fun setText(text: String) {
        otfQuotaAllocate.text = text
    }

    // ----------------------------------------------------------------------------------

    fun getValue(): Int {
        if (otfQuotaAllocate.text?.isNotEmpty()!!) {
            return otfQuotaAllocate.text.toString().toInt()
        }
        return 0
    }

    // ----------------------------------------------------------------------------------

    fun onTextChanged() {
        otfQuotaAllocate.setOnTextChange {}
        var cursorPos = otfQuotaAllocate.getEditText()?.selectionStart ?: 0
        val formatted = (otfQuotaAllocate.text.toString().toLongOrNull() ?: 0L).toString()
        cursorPos -= (otfQuotaAllocate.text.toString().length - formatted.length)
        if (cursorPos < 0)
            cursorPos = 0
        else if (cursorPos > formatted.length)
            cursorPos = formatted.length

        otfQuotaAllocate.text = formatted
        otfQuotaAllocate.getEditText()?.setSelection(cursorPos)

        onAllocateQuotaChange?.invoke(otfQuotaAllocate.text.toString())

        otfQuotaAllocate.setOnTextChange {
            onTextChanged()
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
}