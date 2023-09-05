package com.kapirti.baret.model.service.module

import com.kapirti.baret.model.service.*
import com.kapirti.baret.model.service.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds abstract fun provideFirestoreService(impl: FirestoreServiceImpl): FirestoreService

  //  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

//    @Binds abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}