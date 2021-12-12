package com.example.android_architecture_base_flow.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.android_architecture_base_flow.util.Signal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferencesDataImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesData {

    companion object {
        // Key
    }

    /**
     * 書き込み
     */
    private fun <T> writePreferences(key: Preferences.Key<T>, value: T): Flow<Signal> =
        flow {
            dataStore.edit { pref ->
                pref[key] = value
            }
            emit(Signal)
        }

    /**
     * 読み込み
     */
    private fun <T> readPreferences(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { pref ->
                pref[key] ?: defaultValue
            }

    /**
     * 削除
     */
    private fun <T> removePreferences(key: Preferences.Key<T>): Flow<Signal> =
        flow {
            dataStore.edit { pref ->
                pref.remove(key)
            }
            emit(Signal)
        }
}
