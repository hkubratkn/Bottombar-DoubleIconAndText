package com.kapirti.baret.core.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class CityRepository(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("cityScore")
        val CITY_SCORE = intPreferencesKey("city_score")
    }

    fun getCityScore(): Flow<Int> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val score = preferences[CITY_SCORE] ?: 17
                score
            }
    }

    suspend fun changeCity(city: Int) {
        context.dataStore.edit { preferences->
            preferences[CITY_SCORE] = city
        }
    }
}