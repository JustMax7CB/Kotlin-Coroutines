package com.example.weathercoroutines.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercoroutines.data.repository.WeatherApi
import com.example.weathercoroutines.model.WeatherModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(weatherData: WeatherModel) : ViewModel() {

    private val weatherData = MutableLiveData<WeatherModel>()
    val readWeather: LiveData<WeatherModel> get() = weatherData

    private val TAG: String = "MainActivityViewModel"
    private val api: WeatherApi = WeatherApi()

    init {
        this.weatherData.value = weatherData
    }

    fun getWeatherByCity(cityName: String) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Caught Exception: $throwable")
        }
        viewModelScope.launch(Dispatchers.IO + handler) {
            Log.d(TAG, "Working on thread ${Thread.currentThread().name}")
            weatherData.postValue(api.weatherByCity(cityName))
        }
        Log.d(TAG, "Finished getWeatherByCity")
    }
}