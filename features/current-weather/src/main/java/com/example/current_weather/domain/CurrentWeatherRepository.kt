package com.example.current_weather.domain

import com.example.current_weather.domain.model.CurrentWeather

interface CurrentWeatherRepository {

    suspend fun getCurrentWeather(location: String, lang: String, key: String): CurrentWeather
}