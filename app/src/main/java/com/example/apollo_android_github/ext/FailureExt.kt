package com.example.apollo_android_github.ext

import com.apollographql.apollo3.exception.ApolloHttpException
import com.example.apollo_android_github.R
import com.example.apollo_android_github.view.ui.Failure

fun Failure.toMessage(): Int {
    return when (execution) {
        else -> error.toMessage()
    }
}

fun Throwable.toMessage(): Int {
    return when (this) {
        is ApolloHttpException -> this.toMessage()
        else -> R.string.error_message_default
    }
}

fun ApolloHttpException.toMessage(): Int {
    return when (statusCode) {
        401 -> R.string.error_message_401
        500 -> R.string.error_message_500
        503 -> R.string.error_message_503
        504 -> R.string.error_message_504
        else -> R.string.error_message_default
    }
}
