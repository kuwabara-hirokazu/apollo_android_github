package com.example.apollo_android_github.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.apollographql.apollo.ApolloClient
import com.example.apollo_android_github.BuildConfig
import com.example.apollo_android_github.data.source.local.PreferencesData
import com.example.apollo_android_github.data.source.local.PreferencesDataImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        private const val DATA_STORE_KEY = "storeKey"
        private const val Authorization_KEY = "Authorization"
        private const val GITHUB_SERVER_URL = "https://api.github.com/graphql"
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(Authorization_KEY, "Bearer ${BuildConfig.GITHUB_KEY}")
                .build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(GITHUB_SERVER_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATA_STORE_KEY) }
        )
    }

    @Singleton
    @Provides
    fun providePreferencesData(dataStore: DataStore<Preferences>): PreferencesData {
        return PreferencesDataImpl(dataStore)
    }
}
