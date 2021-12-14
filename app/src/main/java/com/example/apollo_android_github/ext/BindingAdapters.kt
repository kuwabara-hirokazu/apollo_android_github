package com.example.apollo_android_github.ext

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
}
