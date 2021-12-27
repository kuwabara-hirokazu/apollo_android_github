package com.example.apollo_android_github.ext

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.apollo_android_github.R
import com.example.apollo_android_github.data.source.remote.ApolloApiException
import com.example.apollo_android_github.view.ui.Failure
import com.google.android.material.snackbar.Snackbar

fun Fragment.showError(view: View, failure: Failure) {
    val message = when (failure.error is ApolloApiException) {
        true -> failure.error.messages.joinToString { it }
        false -> getString(failure.toMessage())
    }
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        .setAction(R.string.retry) { failure.retry() }
        .show()
}

fun Fragment.showToast(@StringRes resourceId: Int) {
    Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show()
}
