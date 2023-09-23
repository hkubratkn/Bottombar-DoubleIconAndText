package com.kapirti.baret.core.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.kapirti.baret.core.constants.EditType.FEEDBACK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class EditTypeRepository (private val context: Context) {
    companion object PreferencesKey {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "edit_type_pref")
        val EDIT_TYPE_KEY = stringPreferencesKey(name = "edit_type")
    }

    suspend fun saveEditTypeState(type: String) {
        context.dataStore.edit { preferences ->
            preferences[EDIT_TYPE_KEY] = type
        }
    }

    fun readEditTypeState(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val completeProfileTypeState = preferences[EDIT_TYPE_KEY] ?: FEEDBACK
                completeProfileTypeState
            }
    }
}