package com.example.current_weather.domain.usecases

import com.example.current_weather.domain.CurrentWeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: CurrentWeatherRepository
) {
    suspend operator fun invoke(location: String, lang: String, key: String) =
        repository.getCurrentWeather(location, lang, key)
}