package com.example.suitmediatest.di

import com.example.suitmediatest.service.RegresServiceInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideListRegres(retrofit: Retrofit) = retrofit.create(RegresServiceInstance::class.java)

}