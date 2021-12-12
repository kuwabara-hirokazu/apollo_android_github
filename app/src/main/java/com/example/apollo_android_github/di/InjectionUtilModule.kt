package com.example.apollo_android_github.di

import com.example.apollo_android_github.util.InjectionUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InjectionUtilModule {

    @Singleton
    @Provides
    @Named("ioDispatcher")
    fun provideDispatchersIo(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun provideInjectionUtil(@Named("ioDispatcher") ioDispatcher: CoroutineDispatcher): InjectionUtil {
        return InjectionUtil(ioDispatcher)
    }
}
