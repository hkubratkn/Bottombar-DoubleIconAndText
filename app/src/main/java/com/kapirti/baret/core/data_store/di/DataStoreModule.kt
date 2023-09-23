package com.kapirti.baret.core.data_store.di

import android.content.Context
import com.kapirti.baret.core.data_store.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun provideOnBoardingRepository(@ApplicationContext context: Context): OnBoardingRepository {
        return OnBoardingRepository(context = context)
    }

    @Singleton
    @Provides
    fun provideEditRepository(@ApplicationContext context: Context): EditRepository {
        return EditRepository(context = context)
    }

    @Singleton
    @Provides
    fun provideEditTypeRepository(@ApplicationContext context: Context): EditTypeRepository {
        return EditTypeRepository(context = context)
    }

    @Singleton
    @Provides
    fun provideCityRepository(@ApplicationContext context: Context): CityRepository {
        return CityRepository(context = context)
    }
    @Singleton
    @Provides
    fun provideTypeRepository(@ApplicationContext context: Context): TypeRepository {
        return TypeRepository(context = context)
    }
    @Singleton
    @Provides
    fun provideRentSellRepository(@ApplicationContext context: Context): RentSellRepository {
        return RentSellRepository(context = context)
    }
}