package com.kapirti.baret.core.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kapirti.baret.core.constants.Constants.RENT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class RentSellRepository(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("rentSell")
        val RENT_SELL = stringPreferencesKey("rent_sell")
    }

    fun getRentSell(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val score = preferences[RENT_SELL] ?: RENT
                score
            }
    }

    suspend fun changeRentSell(value: String) {
        context.dataStore.edit { preferences->
            preferences[RENT_SELL] = value
        }
    }
}