package com.example.current_weather.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.current_weather.data.repository.CurrentWeatherRepositoryImpl
import com.example.current_weather.di.CurrentWeatherComponent
import com.example.current_weather.di.DaggerCurrentWeatherComponent
import com.example.current_weather.domain.model.CurrentWeather
import com.example.current_weather.domain.usecases.GetCurrentWeatherUseCase
import com.example.network.BuildConfig
import com.example.network.LoadingState
import com.example.network.di.DaggerNetworkComponent
import com.example.network.di.NetworkComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val key = BuildConfig.API_KEY

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    val loadingStateLiveData = MutableLiveData<LoadingState>()

    var isReady = false

    fun getCurrentWeather(location: String, lang: String) {
        loadingStateLiveData.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weather = getCurrentWeatherUseCase(location, lang, key)
                _currentWeather.postValue(weather)
                isReady = true
                loadingStateLiveData.postValue(LoadingState.SUCCESS)
            } catch (e: Exception) {
                loadingStateLiveData.postValue(LoadingState.ERROR)
            }
        }
    }
}