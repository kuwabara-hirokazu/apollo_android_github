package com.example.apollo_android_github.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.apollo_android_github.data.source.local.PreferencesData
import com.example.apollo_android_github.data.source.local.PreferencesDataImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        private const val DATA_STORE_KEY = "storeKey"
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
