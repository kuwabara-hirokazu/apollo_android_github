package com.example.android_architecture_base_flow.ext

import com.example.android_architecture_base_flow.R
import com.example.android_architecture_base_flow.view.ui.Failure

fun Failure.toMessage(): Int {
    return when (execution) {
        else -> when (error) {
            else -> R.string.error_message_default
        }
    }
}
