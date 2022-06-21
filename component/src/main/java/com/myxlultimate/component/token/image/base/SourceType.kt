package com.myxlultimate.component.token.image.base

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

enum class SourceType() {
    ASSET() {
        override fun setSource(imageView: ImageView, context: Context, base: Base, source: Any?) {
            if (source == null) {
                return
            }

            val image = source as String
            val imageData = Uri.parse("file:///android_asset/$image")

            setImage(imageView, base, imageData)
        }
    },
    URL() {
        override fun setSource(imageView: ImageView, context: Context, base: Base, source: Any?) {
            if (source == null) {
                return
            }

            val image = source as Int
            val imageData = ContextCompat.getDrawable(context, image)

            setImage(imageView, base, imageData)
        }
    },
    DRAWABLE() {
        override fun setSource(imageView: ImageView, context: Context, base: Base, source: Any?) {
            if (source == null) {
                return
            }

            val image = source as String
            val imageData = Uri.parse(image)

            setImage(imageView, base, imageData)
        }
    };

    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------

    abstract fun setSource(imageView: ImageView, context: Context, base: Base, source: Any?)

    // ----------------------------------------------------------------------------------

    protected fun setImage(imageView: ImageView, base: Base, imageData: Any?) {
        Glide
            .with(base)
            .load(imageData)
            .into(imageView)
    }
}