package com.myxlultimate.component.organism.lottieAnimation

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.airbnb.lottie.LottieDrawable
import com.myxlultimate.component.R
import com.myxlultimate.component.util.IsEmptyUtil
import kotlinx.android.synthetic.main.organism_lottie_animation.view.*

class LottieAnimation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val imageLottieDefault = "egg_without_label.json"

    var image = imageLottieDefault
        set(value) {
            field = value
            refreshView()
        }

    var imageUrl = ""
        set(value) {
            field = value
            refreshView()
        }

    var imageJsonString = ""
        set(value) {
            field = value
            refreshView()
        }

    var hasArrowLeftButton = false
        set(value) {
            field = value

            arrowLeftBtnView.visibility = if (value) View.VISIBLE else View.GONE
            arrowLeftBtnView.setPadding(0,0,0,0)
            if (value) arrowLeftBtnView.rotation = -180f
        }

    var hasArrowRightButton = false
        set(value) {
            field = value

            arrowRightBtnView.visibility = if (value) View.VISIBLE else View.GONE
            arrowRightBtnView.setPadding(0,0,0,0)
            if (value) arrowRightBtnView.rotation = 0f
        }

    var title = ""
        set(value) {
            field = value
            refreshView()
        }

    var hasTitle = true
        set(value) {
            field = value
            refreshView()
        }

    var autoPlay = true

    var animatorListener:Animator.AnimatorListener? = null
    set(value) {
        field = value
        if(field!=null)
            lottieView.addAnimatorListener(field)
        else
            lottieView.removeAllAnimatorListeners()
    }

    var repeatMode:Int = LottieDrawable.RESTART
    set(value) {
        field = value
        lottieView.repeatMode = field
    }

    var repeatCount:Int = LottieDrawable.INFINITE
        set(value) {
            field = value
            lottieView.repeatCount = field
        }

    fun playAnimation(fromStart:Boolean){
        if(fromStart){
            lottieView.progress = 0f
        }
        lottieView.playAnimation()
    }

    fun setArrowLeftRotation(rotation:Float){
        arrowLeftBtnView.rotation = rotation
    }

    fun setArrowRightRotation(rotation:Float){
        arrowRightBtnView.rotation = rotation
    }

    fun pauseAnimation(){
        lottieView.pauseAnimation()
    }

    // ----------------------------------------------------------------------------------

    var onArrowLeftPress: (() -> Unit)? = null
        set(value) {
            field = value

            arrowLeftBtnView.setOnClickListener {
                value?.let { it() }
                if (arrowLeftBtnView.rotation == -180f) {
                    arrowLeftBtnView.animate().setDuration(500).rotation(0f).start()
                } else {
                    arrowLeftBtnView.animate().setDuration(500).rotation(-180f).start()
                }
            }
        }

    var onArrowRightPress: (() -> Unit)? = null
        set(value) {
            field = value

            arrowRightBtnView.setOnClickListener {
                value?.let { it() }
                if (arrowRightBtnView.rotation == -180f) {
                    arrowRightBtnView.animate().setDuration(500).rotation(0f).start()
                } else {
                    arrowRightBtnView.animate().setDuration(500).rotation(-180f).start()
                }
            }
        }

    // ----------------------------------------------------------------------------------

    var onFailureListener: (() -> Unit)? = null

    // ----------------------------------------------------------------------------------

    init {
        this.visibility = View.VISIBLE

        LayoutInflater.from(context)
            .inflate(R.layout.organism_lottie_animation, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LottieAnimation)
        typedArray.run {
            image = getString(R.styleable.LottieAnimation_fileName) ?: "egg_without_label.json"
            imageUrl = getString(R.styleable.LottieAnimation_fileNameUrl) ?: ""
            hasArrowLeftButton = getBoolean(R.styleable.LottieAnimation_hasArrowLeftButton, false)
            hasArrowRightButton = getBoolean(R.styleable.LottieAnimation_hasArrowRightButton, false)
            hasTitle = getBoolean(R.styleable.LottieAnimation_hasTitle, true)
            title = getString(R.styleable.LottieAnimation_title) ?: ""
            autoPlay = getBoolean(R.styleable.LottieAnimation_autoPlay, true)
            repeatCount = getInt(R.styleable.LottieAnimation_repeatCount, LottieDrawable.INFINITE)
        }
        typedArray.recycle()
        if(!autoPlay)
            lottieView.pauseAnimation()

    }

    private fun refreshView() {
        lottieView?.apply {
            when {
                imageUrl.isNotEmpty() -> setAnimationFromUrl(imageUrl)
                imageJsonString.isNotEmpty() -> setAnimationFromJson(imageJsonString)
                else -> setAnimation(image)
            }
            setFailureListener {
                setAnimation(imageLottieDefault)
                onFailureListener?.invoke()
            }

            repeatCount = LottieDrawable.INFINITE
        }

        titleView.text = title
        textViewLayout.visibility = if (hasTitle) View.VISIBLE else View.GONE
        IsEmptyUtil.setVisibility(title, textViewLayout)
    }
}
