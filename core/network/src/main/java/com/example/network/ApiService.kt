package com.example.network

import com.example.network.model.CurrentWeatherResponseDto
import retrofit2.http.*
import javax.inject.Inject

interface ApiService {

    @GET("current.json")
    suspend fun getCurrentWeather(@Query("q") q: String, @Query("lang") lang: String, @Query("key") key: String): CurrentWeatherResponseDto

}