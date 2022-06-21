package com.myxlultimate.component.organism.transactionHistoryDetailFooter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.myxlultimate.component.R
import com.myxlultimate.component.token.imageView.ImageSourceType
import kotlinx.android.synthetic.main.organism_transaction_history_detail_footer.view.*

class TransactionHistoryDetailFooter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    data class Data(
        val text: String = "",
        val imageSourceType: ImageSourceType = ImageSourceType.ASSET,
        val imageSource: String = ""
    )

    var text = ""
        set(value) {
            field = value
            cardText.text = value
        }

    var imageSourceType = ImageSourceType.ASSET
        set(value) {
            field = value
            cardIcon.imageSourceType = value
        }

    var imageSource: Any? = null
        set(value) {
            field = value
            cardIcon.imageSource = value
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.organism_transaction_history_detail_footer, this, true)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TransactionHistoryDetailFooter)

        typedArray.run {
            text = getString(R.styleable.TransactionHistoryDetailFooter_text) ?: ""
            imageSourceType =
                ImageSourceType.values()[getInt(
                    R.styleable.TransactionHistoryDetailFooter_imageSourceType,
                    0
                )]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.TransactionHistoryDetailFooter_imageSource)
            } else {
                getString(R.styleable.TransactionHistoryDetailFooter_imageSource)
            }
        }
    }
}