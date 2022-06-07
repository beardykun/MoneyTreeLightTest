package com.example.moneytreelighttest.di

import com.example.moneytreelighttest.data.AccountsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideAccountsRepositoryImpl(): AccountsRepositoryImpl = AccountsRepositoryImpl
}