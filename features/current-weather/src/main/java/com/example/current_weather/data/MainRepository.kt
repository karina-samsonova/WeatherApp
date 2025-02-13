package com.example.current_weather.data

import com.example.network.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getCurrentWeather(q: String, lang: String, key: String) = apiHelper.getCurrentWeather(q, lang, key)

}