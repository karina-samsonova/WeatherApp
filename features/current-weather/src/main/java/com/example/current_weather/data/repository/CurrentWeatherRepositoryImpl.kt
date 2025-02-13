package com.example.current_weather.data.repository

import com.example.current_weather.data.mapper.CurrentWeatherMapper
import com.example.current_weather.domain.CurrentWeatherRepository
import com.example.current_weather.domain.model.CurrentWeather
import com.example.network.ApiHelper
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val mapper: CurrentWeatherMapper
): CurrentWeatherRepository {

    override suspend fun getCurrentWeather(
        location: String,
        lang: String,
        key: String
    ): CurrentWeather = mapper.mapDtoToEntity(apiHelper.getCurrentWeather(location, lang, key))
}