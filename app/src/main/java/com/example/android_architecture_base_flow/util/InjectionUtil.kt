package com.example.android_architecture_base_flow.util

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

class InjectionUtil(
    @Named("ioDispatcher") val ioDispatcher: CoroutineDispatcher
)
