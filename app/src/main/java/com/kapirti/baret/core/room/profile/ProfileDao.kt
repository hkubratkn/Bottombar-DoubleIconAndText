package com.kapirti.baret.core.room.profile

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_table WHERE id = 0")
    fun getProfile(): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(profile: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}