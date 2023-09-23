package com.kapirti.baret.core.room.profile.di

import android.content.Context
import androidx.room.Room
import com.kapirti.baret.core.room.profile.ProfileDao
import com.kapirti.baret.core.room.profile.ProfileDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ProfileModule {
    @Provides
    fun provideProfileDao(profileDatabase: ProfileDatabase): ProfileDao {
        return profileDatabase.profileDao()
    }

    @Provides
    @Singleton
    fun provideProfileDatabase(@ApplicationContext context: Context): ProfileDatabase {
        return Room.databaseBuilder(
            context,
            ProfileDatabase::class.java,
            "ProfileDatabase"
        ).build()
    }
}