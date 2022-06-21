package com.myxlultimate.component.template.storeSegment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.myxlultimate.component.R
import kotlinx.android.synthetic.main.store_segment.view.*

/**
 * **StoreSegment** is a container for set of [View]
 *
 * ### Usage
 *
 * #### XML declaration
 *
 * ```xml
 *      <com.myxlultimate.component.template.storeSegment.StoreSegment
 *          android:id="@+id/storeSegmentView"
 *          android:layout_width="match_parent"
 *          android:layout_height="wrap_content"
 *          app:label="Spesial untuk Anda "
 *          app:action="View All"/>
 * ```
 *
 * #### Kotlin implementation
 *
 * In Kotlin implementation, [RecyclerView] can be accessed through [containerView] property of the [StoreSegment].
 *
 *
 * ```kotlin
 *      storeSegmentView.setActionClickListener {
 *             Toast.makeText(activity, "Test", Toast.LENGTH_LONG).show()
 *      }
 *
 *      val container = storeSegmentView.containerView
 *        container.apply {
 *        setHasFixedSize(true)
 *        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
 *        adapter = sampleAdapter
 *     }
 * ```
 */
class StoreSegment @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var onClickListener: (() -> Unit)? = null

    /**
     * Sets the label of [StoreSegment]
     *
     * @property label
     */
    var label: CharSequence?
        get() = labelTextView.text
        set(value) {
            labelTextView.text = value
        }

    /**
     * Sets the action item of [StoreSegment]
     *
     * @property action
     */
    var action: CharSequence?
        get() = actionTextView.getText()
        set(value) {
            if (!value.isNullOrEmpty()) actionTextView.setText(value.toString()) else actionTextView.visibility =
                View.GONE
        }

    /**
     * Exposes the [RecyclerView] inside the [StoreSegment]
     *
     * @property containerView
     */
    val containerView: RecyclerView
        get() = storeSegmentRecyclerView

    init {
        setPadding(
            resources.getDimensionPixelSize(R.dimen.store_segment_left_padding),
            resources.getDimensionPixelSize(R.dimen.store_segment_top_padding),
            resources.getDimensionPixelSize(R.dimen.store_segment_right_padding),
            resources.getDimensionPixelSize(R.dimen.store_segment_bottom_padding)
        )

        LayoutInflater.from(context).inflate(R.layout.store_segment, this, true)

        setBackgroundResource(R.color.mud_palette_basic_white)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StoreSegment,
            0, 0
        ).apply {

            try {
                getString(R.styleable.StoreSegment_label)?.let {
                    label = it
                }

                getString(R.styleable.StoreSegment_action)?.let {
                    action = it
                }
            } finally {
                recycle()
            }
        }

        setActionClickListener(onClickListener)
    }

    /**
     * Sets the onclick listener of the [action]
     *
     * @param _onClickListener
     */
    fun setActionClickListener(_onClickListener: (() -> Unit)?) {
        actionTextView.setOnClick(_onClickListener)
    }

    internal fun setViewAllText(charSequence: CharSequence) {
        actionTextView.setText(charSequence.toString())
    }

}

