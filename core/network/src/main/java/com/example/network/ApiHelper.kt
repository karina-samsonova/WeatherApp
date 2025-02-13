package com.example.network

class ApiHelper(private val apiService: ApiService) {

    suspend fun getCurrentWeather(q: String, lang: String, key: String) = apiService.getCurrentWeather(q, lang, key)

}