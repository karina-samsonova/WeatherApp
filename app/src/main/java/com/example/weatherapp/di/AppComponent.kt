package com.example.weatherapp.di

import android.app.Application
import com.example.current_weather.di.CurrentWeatherComponent
import com.example.current_weather.presentation.ui.CurrentWeatherFragment
import com.example.weatherapp.MainActivity
import dagger.Component

@Component(modules = [AppModule::class], dependencies = [CurrentWeatherComponent::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: CurrentWeatherFragment)

    @Component.Builder
    interface Builder {
        fun currentWeatherComponent(currentWeatherComponent: CurrentWeatherComponent): Builder
        fun build(): AppComponent
    }
}