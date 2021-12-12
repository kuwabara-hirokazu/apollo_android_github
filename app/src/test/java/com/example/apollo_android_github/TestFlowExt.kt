package com.example.apollo_android_github

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.mockito.kotlin.KStubbing

fun <T> Flow<T>.await(): T {
    return runBlocking { first() }
}

fun <T> givenForSuspend(mock: T, stubbing: KStubbing<T>.() -> Unit) {
    stubbing(KStubbing(mock))
}
