package com.kapirti.baret.core.room.recent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentDao {
    @Query("SELECT * FROM recent_table")
    fun recents(): Flow<List<Recent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recent: Recent)

    @Delete
    suspend fun delete(recent: Recent)
}