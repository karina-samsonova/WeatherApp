package com.example.current_weather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.current_weather.presentation.viewModel.CurrentWeatherViewModel
import com.example.current_weather.presentation.viewModel.CurrentWeatherViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrentWeatherViewModel::class)
    abstract fun bindCurrentWeatherViewModel(viewModel: CurrentWeatherViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CurrentWeatherViewModelFactory): ViewModelProvider.Factory
}