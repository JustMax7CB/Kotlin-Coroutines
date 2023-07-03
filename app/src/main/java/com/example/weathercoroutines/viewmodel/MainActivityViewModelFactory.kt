package com.example.weathercoroutines.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathercoroutines.model.WeatherModel
import java.lang.IllegalArgumentException

class MainActivityViewModelFactory(private val weatherData: WeatherModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(weatherData) as T
        }
        throw IllegalArgumentException("View Model Factory Exception")
    }
}