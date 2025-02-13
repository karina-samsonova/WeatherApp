package com.example.current_weather.domain.model

data class CurrentWeather(
    val locationName: String,
    val localtime: String,
    val last_updated: String,
    val temp_c: Double,
    val is_day: Int,
    val conditionText: String,
    val conditionIcon: String,
    val conditionCode: Int,
    val wind_kph: Double,
    val pressure_mb: Int,
    val humidity: Int,
    val feelslike_c: Double,
)
