package com.kapirti.baret.core.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class EditRepository (private val context: Context) {
    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("editValue")
        val EDIT_VALUE = booleanPreferencesKey("edit_value")
    }

    fun readEditState(): Flow<Boolean> {
        return context.dataStoree.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val editState = preferences[EDIT_VALUE] ?: false
                editState
            }
    }

    suspend fun completeF() {
        context.dataStoree.edit { preferences ->
            val valueFalse = false
            preferences[EDIT_VALUE] = valueFalse
        }
    }
    suspend fun completeT() {
        context.dataStoree.edit { preferences->
            val valueTrue = true
            preferences[EDIT_VALUE] = valueTrue
        }
    }
}