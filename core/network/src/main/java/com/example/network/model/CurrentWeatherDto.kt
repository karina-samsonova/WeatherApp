package com.example.network.model

import com.example.network.model.ConditionDto

data class CurrentWeatherDto(
    val last_updated: String,
    val temp_c: Double,
    val is_day: Int,
    val condition: ConditionDto,
    val wind_kph: Double,
    val pressure_mb: Int,
    val humidity: Int,
    val feelslike_c: Double,
)
