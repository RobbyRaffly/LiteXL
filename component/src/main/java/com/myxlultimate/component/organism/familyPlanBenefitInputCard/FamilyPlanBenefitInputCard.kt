package com.myxlultimate.component.organism.familyPlanBenefitInputCard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.organism.familyPlanBenefitInputCard.adapter.BenefitInputCardItemAdapter
import com.myxlultimate.component.organism.familyPlanBenefitInputCard.enum.FamilyPlanBenefitInputMode
import kotlinx.android.synthetic.main.organism_family_plan_benefit_input_card.view.*
import java.util.*

class FamilyPlanBenefitInputCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    data class Data(
        var id: String = "",

        var isExpanded: Boolean = true,
        var isDisabled: Boolean = false,

        var name:String = "",
        var msisdn:String = "",
        var saveLabel:String="Save",
        var editLabel:String="Edit",

        var items:List<FamilyPlanBenefitInputCardItem.Data> = listOf(),
        var mode: FamilyPlanBenefitInputMode,
        // to get if the inputted value is valid
        var isValid:Boolean = true
    ) {
        fun getCurrentValue(dataType:String):Long?{
            return this.items.find { it.dataType == dataType }?.value
        }
    }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var mode:FamilyPlanBenefitInputMode = FamilyPlanBenefitInputMode.FORM
    set(value) {
        field = value

        if(mode == FamilyPlanBenefitInputMode.FORM){
            isExpanded = true
            tvSave.visibility = GONE
        }else{
            isExpanded = isExpanded
        }
    }

    /**
     * Setup label for Save"
     */
    var saveLabel = ""
        set(value) {
            field = value
            tvSave.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup label for Edit
     */
    var editLabel = ""
        set(value) {
            field = value
            tvEdit.text = value
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup name and initial character name in circle, eg : "Annisa Sanjaya", "AS"
     */
    var name = ""
        set(value) {
            field = value
            tvName.text = value
            tvInitialName.text = getInitialName(value)
        }

    // ----------------------------------------------------------------------------------

    /**
     * Setup msisdn and initial character msisdn in circle, eg : "0819289389939"
     */
    var msisdn = ""
        set(value) {
            field = value
            if (value.isEmpty()) {
                avatarView.visibility = View.VISIBLE
                tvInitialName.visibility = View.GONE
            } else {
                avatarView.visibility = View.GONE
                tvInitialName.visibility = View.VISIBLE
            }
        }

    // ----------------------------------------------------------------------------------

    var adapter = BenefitInputCardItemAdapter()

    var items = listOf<FamilyPlanBenefitInputCardItem.Data>()
    set(value) {
        field = value
        adapter.items = items
        adapter.notifyDataSetChanged()

        //refresh btnsave disabled
        isDisabled = isDisabled

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
                tvEdit.visibility = GONE
                tvSave.visibility = if(mode == FamilyPlanBenefitInputMode.TOGGLE) VISIBLE else GONE
            } else {
                tvEdit.visibility = VISIBLE
                tvSave.visibility = GONE
            }

            adapter.setExpanded(isExpanded)
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
                tvEdit.isEnabled = false
                tvSave.isEnabled = false
            } else {
                baseLayoutDisabled.visibility = View.GONE
                tvEdit.isEnabled = true
                tvSave.isEnabled = adapter.items.find { !it.isValid } == null
            }
        }

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    /**
     * VIEW LISTENER ATTRIBUTE
     */
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    var onSaveClicked: (() -> Unit)? = null

    var onEditClicked: (() -> Unit)? = null

    var onBenefitChange: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_family_plan_benefit_input_card, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FamilyPlanBenefitInputCard)
        typedArray.run {
            name = getString(R.styleable.FamilyPlanBenefitInputCard_name) ?: ""
            msisdn = getString(R.styleable.FamilyPlanBenefitInputCard_msisdn) ?: ""
            saveLabel = getString(R.styleable.FamilyPlanBenefitInputCard_saveLabel) ?: ""
            editLabel = getString(R.styleable.FamilyPlanBenefitInputCard_editLabel) ?: ""

            isExpanded = getBoolean(R.styleable.FamilyPlanOrganizerItem_isExpanded, true)
            isDisabled = getBoolean(R.styleable.FamilyPlanOrganizerItem_isDisabled, false)

            mode = FamilyPlanBenefitInputMode.values()[getInt(R.styleable.FamilyPlanBenefitInputCard_familyPlanBenefitInputCardMode,0)]
        }
        typedArray.recycle()

        tvSave.setOnClickListener {
            onSaveClicked?.invoke()
        }

        tvEdit.setOnClickListener {
            onEditClicked?.invoke()
        }


        adapter.onItemChange = { i, l ->
            // refresh btn save enabled
            isDisabled = isDisabled

            onBenefitChange?.invoke()
        }

        rvBenefit.adapter = adapter

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
    // ----------------------------------------------------------------------------------

    fun applyData(data:Data){

        isExpanded = data.isExpanded
        isDisabled = data.isDisabled

        name = data.name
        msisdn = data.msisdn

        saveLabel = data.saveLabel
        editLabel = data.editLabel
        mode = data.mode

        items = data.items
    }
}