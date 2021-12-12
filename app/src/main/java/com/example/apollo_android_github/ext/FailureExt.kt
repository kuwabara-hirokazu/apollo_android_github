package com.example.apollo_android_github.ext

import com.example.apollo_android_github.R
import com.example.apollo_android_github.view.ui.Failure

fun Failure.toMessage(): Int {
    return when (execution) {
        else -> when (error) {
            else -> R.string.error_message_default
        }
    }
}
