package com.example.weatherapp.di

import android.app.Application
import com.example.current_weather.di.DaggerCurrentWeatherComponent
import com.example.network.di.DaggerNetworkComponent

class MainApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val networkComponent = DaggerNetworkComponent.builder()
            .build()

        val currentWeatherComponent = DaggerCurrentWeatherComponent.builder()
            .networkComponent(networkComponent)
            .build()

        appComponent = DaggerAppComponent.builder()
            .currentWeatherComponent(currentWeatherComponent)
            .build()
    }
}