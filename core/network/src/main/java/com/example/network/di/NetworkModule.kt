package com.example.network.di

import com.example.network.ApiHelper
import com.example.network.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val BASE_URL = "http://api.weatherapi.com/v1/"

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(): ApiService {
        return provideRetrofit().create(ApiService::class.java)
    }

    @Provides
    fun provideApiHelper(): ApiHelper {
        return ApiHelper(provideApiService())
    }
}