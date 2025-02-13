package com.example.current_weather.di

import com.example.current_weather.data.mapper.CurrentWeatherMapper
import com.example.current_weather.data.repository.CurrentWeatherRepositoryImpl
import com.example.current_weather.domain.CurrentWeatherRepository
import com.example.network.ApiHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    fun bindCurrentWeatherRepository(impl: CurrentWeatherRepositoryImpl): CurrentWeatherRepository

}