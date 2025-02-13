package com.example.network.model

import com.example.network.model.CurrentWeatherDto
import com.example.network.model.LocationDto

data class CurrentWeatherResponseDto(
    val location: LocationDto,
    val current: CurrentWeatherDto
)
