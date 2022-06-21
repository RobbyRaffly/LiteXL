package com.myxlultimate.component.token.imageView

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Base64
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.myxlultimate.component.R

open class ImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {


    var oldImageSource: Any? = null

    var imageSourceType = ImageSourceType.ASSET

    // ----------------------------------------------------------------------------------

    var imageSource: Any? = null
        set(value) {
            field = value

            if (value == null || value == oldImageSource) return

            oldImageSource = imageSource

            skipDiskCache = false
            skipMemoryCache = false

            val imageData: Any? = when (imageSourceType) {
                ImageSourceType.ASSET -> {
                    Uri.parse("file:///android_asset/$value")
                }
                ImageSourceType.URL -> {
                    Uri.parse(value as String)
                }
                ImageSourceType.DRAWABLE -> {
                    value as Drawable
                }
                ImageSourceType.BASE64 -> {
                    skipDiskCache = true
                    skipMemoryCache = true
                    try {
                        Base64.decode(value as String, Base64.DEFAULT)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        ""
                    }
                }
                ImageSourceType.BITMAP -> {
                    setBitMap()
                }
            }

            var builder = Glide.with(context)
                .load(imageData)
                .skipMemoryCache(skipMemoryCache)
            if (placeholder != null)
                builder = builder.placeholder(placeholder!!)
            if (skipDiskCache)
                builder = builder.diskCacheStrategy(DiskCacheStrategy.NONE)
            builder.into(this)

        }

    var placeholder: Int? = null

    // ----------------------------------------------------------------------------------

    var skipMemoryCache: Boolean = false

    // ----------------------------------------------------------------------------------

    var skipDiskCache: Boolean = false

    // ----------------------------------------------------------------------------------

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageViewAttr)
        typedArray.run {
            imageSourceType =
                ImageSourceType.values()[getInt(R.styleable.CustomImageViewAttr_imageSourceType, 0)]
            imageSource = if (imageSourceType == ImageSourceType.DRAWABLE) {
                getDrawable(R.styleable.CustomImageViewAttr_imageSource)
            } else {
                getString(R.styleable.CustomImageViewAttr_imageSource)
            }
        }
        typedArray.recycle()
    }

    fun setBitMap() {
        var builder = Glide.with(context)
            .asBitmap()
            .load(imageSource)
            .skipMemoryCache(skipMemoryCache)
        if (placeholder != null)
            builder = builder.placeholder(placeholder!!)
        if (skipDiskCache)
            builder = builder.diskCacheStrategy(DiskCacheStrategy.NONE)
        builder
            .centerCrop()
            .into(this)
    }
}

