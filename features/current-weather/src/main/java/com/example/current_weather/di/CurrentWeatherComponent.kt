package com.example.current_weather.di

import com.example.current_weather.presentation.ui.CurrentWeatherFragment
import com.example.network.di.NetworkComponent
import dagger.Component

@Component(modules = [DataModule::class, PresentationModule::class], dependencies = [NetworkComponent::class])
interface CurrentWeatherComponent {
    fun inject(fragment: CurrentWeatherFragment)

    @Component.Builder
    interface Builder {
        fun networkComponent(networkComponent: NetworkComponent): Builder
        fun build(): CurrentWeatherComponent
    }
}