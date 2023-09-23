package com.kapirti.baret.core.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kapirti.baret.core.constants.AssetType.CAR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class TypeRepository(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("typeValue")
        val TYPE_VALUE = stringPreferencesKey("type_value")
    }

    fun getType(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val score = preferences[TYPE_VALUE] ?: CAR
                score
            }
    }

    suspend fun changeType(city: String) {
        context.dataStore.edit { preferences->
            preferences[TYPE_VALUE] = city
        }
    }
}