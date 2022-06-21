package com.myxlultimate.component.organism.voucherCard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.organism_voucher_manual_input.view.*

class VoucherInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs){

    // ----------------------------------------------------------------------------------

    var voucherNumber = ""
        set(value) {
            field = value

            if (value.isNotEmpty()) {
                val lengthNumber = value.length
                when {
                    lengthNumber <= splitNumber -> fieldVoucher1.text = value
                    lengthNumber <= splitNumber*2 -> {
                        fieldVoucher1.text = value.substring(0, splitNumber)
                        fieldVoucher2.text = value.substring(splitNumber)
                    }
                    lengthNumber <= splitNumber*3 -> {
                        fieldVoucher1.text = value.substring(0, splitNumber)
                        fieldVoucher2.text = value.substring(splitNumber, splitNumber*2)
                        fieldVoucher3.text = value.substring(splitNumber*2)
                    }
                    lengthNumber <= splitNumber*4 -> {
                        fieldVoucher1.text = value.substring(0, splitNumber)
                        fieldVoucher2.text = value.substring(splitNumber, splitNumber*2)
                        fieldVoucher3.text = value.substring(splitNumber*2, splitNumber*3)
                        fieldVoucher4.text = value.substring(splitNumber*3)
                    }
                    else -> {
                        fieldVoucher1.text = value.substring(0, splitNumber)
                        fieldVoucher2.text = value.substring(splitNumber, splitNumber*2)
                        fieldVoucher3.text = value.substring(splitNumber*2, splitNumber*3)
                        fieldVoucher4.text = value.substring(splitNumber*3, splitNumber*4)
                    }
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var isRedBorder = false
        set(value) {
            // === set colorPrimary
            val typedValue = TypedValue()
            context.theme?.resolveAttribute(R.attr.colorPrimary, typedValue, true)
            // =====
            field = value
            if (value) {
                fieldVoucher1.setBoxSrokeColor(R.color.redDot, R.color.redDot)
                fieldVoucher2.setBoxSrokeColor(R.color.redDot, R.color.redDot)
                fieldVoucher3.setBoxSrokeColor(R.color.redDot, R.color.redDot)
                fieldVoucher4.setBoxSrokeColor(R.color.redDot, R.color.redDot)
            } else {
                fieldVoucher1.setBoxSrokeColor(typedValue.resourceId, R.color.mud_palette_basic_light_grey)
                fieldVoucher2.setBoxSrokeColor(typedValue.resourceId, R.color.mud_palette_basic_light_grey)
                fieldVoucher3.setBoxSrokeColor(typedValue.resourceId, R.color.mud_palette_basic_light_grey)
                fieldVoucher4.setBoxSrokeColor(typedValue.resourceId, R.color.mud_palette_basic_light_grey)
            }
        }

    // ----------------------------------------------------------------------------------

    var splitNumber = 4

    var value = ""

    // ----------------------------------------------------------------------------------

    var onChangeText: ((String) -> Unit)? = null

    // ----------------------------------------------------------------------------------
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_voucher_manual_input, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoucherInputAttr)
        typedArray.run {
            voucherNumber = getString(R.styleable.VoucherInputAttr_voucherNumber)?: ""
            splitNumber = getInt(R.styleable.VoucherInputAttr_splitNumber, 4)
            isRedBorder = getBoolean(R.styleable.VoucherInputAttr_isRedBorder, false)
        }
        typedArray.recycle()

        setListenerField()
        setStrokeAndImeAction()
    }

    private fun setListenerField() {
        // ============================= Set field voucher 1
        fieldVoucher1.setOnTextChange {
            if (it.length > splitNumber) {
                val lengthNumber = it.length
                var lengthNextNumber = 0
                when {
                    lengthNumber <= splitNumber*2 -> {
                        fieldVoucher2.text = it.substring(splitNumber)
                        fieldVoucher2.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher2.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher2.requestFocus()
                        }
                    }
                    lengthNumber <= splitNumber*3 -> {
                        fieldVoucher2.text = it.substring(splitNumber, splitNumber*2)
                        fieldVoucher3.text = it.substring(splitNumber*2)
                        fieldVoucher3.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher3.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher3.requestFocus()
                        }
                    }
                    lengthNumber <= splitNumber*4 -> {
                        fieldVoucher2.text = it.substring(splitNumber, splitNumber*2)
                        fieldVoucher3.text = it.substring(splitNumber*2, splitNumber*3)
                        fieldVoucher4.text = it.substring(splitNumber*3)
                        fieldVoucher4.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher4.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher4.requestFocus()
                        }
                    }
                    else -> {
                        fieldVoucher2.text = it.substring(splitNumber, splitNumber*2)
                        fieldVoucher3.text = it.substring(splitNumber*2, splitNumber*3)
                        fieldVoucher4.text = it.substring(splitNumber*3, splitNumber*4)
                        fieldVoucher4.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher4.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher4.requestFocus()
                        }
                    }
                }
                fieldVoucher1.text = it.substring(0, splitNumber)
            }
            setValue()
        }

        fieldVoucher1.getEditText()?.setOnEditorActionListener { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_NEXT) {
                fieldVoucher2.text?.also { textChild ->
                    fieldVoucher2.getEditText()?.also { editText ->
                        editText.setSelection(textChild.length)
                    }
                }
            }
            false
        }

        // ============================= Set field voucher 2
        fieldVoucher2.setOnTextChange {
            if (it.length > splitNumber) {
                val lengthNumber = it.length
                var lengthNextNumber = 0
                when {
                    lengthNumber <= splitNumber*2 -> {
                        fieldVoucher3.text = it.substring(splitNumber)
                        fieldVoucher3.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher3.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher3.requestFocus()
                        }
                    }
                    lengthNumber <= splitNumber*3 -> {
                        fieldVoucher3.text = it.substring(splitNumber, splitNumber*2)
                        fieldVoucher4.text = it.substring(splitNumber*2)
                        fieldVoucher4.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher4.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher4.requestFocus()
                        }
                    }
                    else -> {
                        fieldVoucher3.text = it.substring(splitNumber, splitNumber*2)
                        fieldVoucher4.text = it.substring(splitNumber*2, splitNumber*3)
                        fieldVoucher4.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher4.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher4.requestFocus()
                        }
                    }
                }
                fieldVoucher2.text = it.substring(0, splitNumber)
            }
            setValue()
        }
        fieldVoucher2.getEditText()?.setOnKeyListener { _, keyCode, _ ->
            fieldVoucher2.text?.also { textParent ->
                fieldVoucher2.getEditText()?.also {
                    if (keyCode == KeyEvent.KEYCODE_DEL && (textParent.isEmpty() || it.selectionStart == 0)) {
                        fieldVoucher1.requestFocus()
                        fieldVoucher1.text?.also { textChild ->
                            fieldVoucher1.getEditText()?.also { editText ->
                                editText.setSelection(textChild.length)
                            }
                        }
                    }
                }
            }
            false
        }

        fieldVoucher2.getEditText()?.setOnEditorActionListener { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_NEXT) {
                fieldVoucher3.text?.also { textChild ->
                    fieldVoucher3.getEditText()?.also { editText ->
                        editText.setSelection(textChild.length)
                    }
                }
            }
            false
        }

        // ============================= Set field voucher 3
        fieldVoucher3.setOnTextChange {
            if (it.length > splitNumber) {
                val lengthNumber = it.length
                var lengthNextNumber = 0
                when {
                    lengthNumber <= splitNumber*2 -> {
                        fieldVoucher4.text = it.substring(splitNumber)
                        fieldVoucher4.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher4.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher4.requestFocus()
                        }
                    }
                    else -> {
                        fieldVoucher4.text = it.substring(splitNumber, splitNumber*2)
                        fieldVoucher4.text?.also { text ->
                            lengthNextNumber = if (text.isNotEmpty()) text.length else 0
                            fieldVoucher4.getEditText()?.setSelection(lengthNextNumber)
                            fieldVoucher4.requestFocus()
                        }
                    }
                }
                fieldVoucher3.text = it.substring(0, splitNumber)
            }
            setValue()
        }
        fieldVoucher3.getEditText()?.setOnKeyListener { _, keyCode, _ ->
            fieldVoucher3.text?.also { textParent ->
                fieldVoucher3.getEditText()?.also {
                    if (keyCode == KeyEvent.KEYCODE_DEL && (textParent.isEmpty() || it.selectionStart == 0)) {
                        fieldVoucher2.requestFocus()
                        fieldVoucher2.text?.also { textChild ->
                            fieldVoucher2.getEditText()?.also { editText ->
                                editText.setSelection(textChild.length)
                            }
                        }
                    }
                }
            }
            false
        }

        fieldVoucher3.getEditText()?.setOnEditorActionListener { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_NEXT) {
                fieldVoucher4.text?.also { textChild ->
                    fieldVoucher4.getEditText()?.also { editText ->
                        editText.setSelection(textChild.length)
                    }
                }
            }
            false
        }

        // ============================= Set field voucher 4
        fieldVoucher4.setOnTextChange {
            if (it.length > splitNumber) {
                fieldVoucher4.text = it.substring(0, splitNumber)
                fieldVoucher4.text?.also { text ->
                    fieldVoucher4.getEditText()?.setSelection(text.length)
                }
            }
            setValue()
        }
        fieldVoucher4.getEditText()?.setOnKeyListener { _, keyCode, _ ->
            fieldVoucher4.text?.also {
                fieldVoucher4.getEditText()?.also {
                    if (keyCode == KeyEvent.KEYCODE_DEL && it.selectionStart == 0) {
                        fieldVoucher3.requestFocus()
                        fieldVoucher3.text?.also { textChild ->
                            fieldVoucher3.getEditText()?.also { editText ->
                                editText.setSelection(textChild.length)
                            }
                        }
                    }
                }
            }
            false
        }
    }

    private fun setValue() {
        value = "${fieldVoucher1.text}${fieldVoucher2.text}${fieldVoucher3.text}${fieldVoucher4.text}"
        onChangeText?.let { it(value) }
    }

    private fun setStrokeAndImeAction() {
        fieldVoucher1.setBoxStrokeWidth(4)
        fieldVoucher2.setBoxStrokeWidth(4)
        fieldVoucher3.setBoxStrokeWidth(4)
        fieldVoucher4.setBoxStrokeWidth(4)
        fieldVoucher1.getEditText()?.imeOptions = EditorInfo.IME_ACTION_NEXT
        fieldVoucher2.getEditText()?.imeOptions = EditorInfo.IME_ACTION_NEXT
        fieldVoucher3.getEditText()?.imeOptions = EditorInfo.IME_ACTION_NEXT
        fieldVoucher4.getEditText()?.imeOptions = EditorInfo.IME_ACTION_DONE
    }

}