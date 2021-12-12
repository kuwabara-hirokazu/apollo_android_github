package com.example.android_architecture_base_flow.ext

import android.view.View
import android.widget.ProgressBar
import com.example.android_architecture_base_flow.view.ui.Loading

fun ProgressBar.switchLoading(loading: Loading) {
    visibility = if (loading.isLoading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
