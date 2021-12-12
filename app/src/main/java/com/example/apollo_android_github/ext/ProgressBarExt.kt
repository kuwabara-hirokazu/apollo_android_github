package com.example.apollo_android_github.ext

import android.view.View
import android.widget.ProgressBar
import com.example.apollo_android_github.view.ui.Loading

fun ProgressBar.switchLoading(loading: Loading) {
    visibility = if (loading.isLoading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
