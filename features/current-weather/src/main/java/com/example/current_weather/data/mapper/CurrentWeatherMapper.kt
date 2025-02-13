package com.example.current_weather.data.mapper

import com.example.current_weather.domain.model.CurrentWeather
import com.example.network.model.CurrentWeatherResponseDto
import javax.inject.Inject

class CurrentWeatherMapper @Inject constructor() {

    fun mapDtoToEntity(dto: CurrentWeatherResponseDto) = CurrentWeather(
        locationName = dto.location.name,
        localtime = dto.location.localtime,
        last_updated = dto.current.last_updated,
        temp_c = dto.current.temp_c,
        is_day = dto.current.is_day,
        conditionText = dto.current.condition.text,
        conditionIcon = dto.current.condition.icon,
        conditionCode = dto.current.condition.code,
        wind_kph = dto.current.wind_kph,
        pressure_mb = dto.current.pressure_mb,
        humidity = dto.current.humidity,
        feelslike_c = dto.current.feelslike_c
    )
}