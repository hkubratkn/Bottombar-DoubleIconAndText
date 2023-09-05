package com.kapirti.baret.core.room.recent.di

import android.content.Context
import androidx.room.Room
import com.kapirti.baret.core.room.recent.RecentDao
import com.kapirti.baret.core.room.recent.RecentDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecentModule {
    @Provides
    fun provideRecentDao(
        recentDatabase: RecentDatabase
    ): RecentDao {
        return recentDatabase.recentDao()
    }

    @Provides
    @Singleton
    fun provideRecentDatabase(
        @ApplicationContext context: Context
    ): RecentDatabase {
        return Room.databaseBuilder(
            context,
            RecentDatabase::class.java,
            "recent_database"
        ).build()
    }
}