package com.example.apollo_android_github.util

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

class InjectionUtil(
    @Named("ioDispatcher") val ioDispatcher: CoroutineDispatcher
)
