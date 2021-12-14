package com.example.apollo_android_github.ext

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImageUrl(imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("url")
    fun WebView.loadUrlForBinding(url: String?) {
        if (url.isNullOrEmpty()) return
        loadUrl(url)
    }
}
