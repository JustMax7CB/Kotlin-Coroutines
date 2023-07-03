package com.example.weathercoroutines.model

import android.graphics.Bitmap

data class WeatherModel(
    var cityName: String = "",
    var celsiusDegrees: Number = -1,
    var image: Bitmap? = null

)
