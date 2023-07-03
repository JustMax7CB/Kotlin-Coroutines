package com.example.weathercoroutines.data.repository

import android.graphics.BitmapFactory
import android.util.Log
import com.example.weathercoroutines.model.WeatherModel
import com.example.weathercoroutines.utils.Constants
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.Double
import java.net.HttpURLConnection
import java.net.URL
import kotlin.String
import kotlin.with

class WeatherApi {

    val TAG: String = "WEATHER API"


    fun weatherByCity(cityName: String): WeatherModel {
        Log.d(TAG, "Working on thread ${Thread.currentThread().name}")
        val url = URL("${Constants.WeatherAPI_BaseURL}&q=$cityName")
        val connection = url.openConnection() as HttpURLConnection
        with(connection) {
            setRequestProperty("Accept", "application/json")
            requestMethod = "GET"
        }
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().readLine()
            val jsonObject = JSONTokener(response).nextValue() as JSONObject
            val currentWeatherJson = jsonObject.getJSONObject("current")
            val celsiusDegrees = currentWeatherJson.getString("temp_c")
            val imageURL =
                "https:${currentWeatherJson.getJSONObject("condition").getString("icon")}"
            var image = BitmapFactory.decodeStream(java.net.URL(imageURL).openStream())
            Log.d(TAG, "Celsius: $celsiusDegrees")
            Log.d(TAG, "image url: $imageURL")
            return WeatherModel(
                cityName.replaceFirstChar { char -> char.uppercaseChar() },
                Double.parseDouble(celsiusDegrees),
                image
            )
        }
        return WeatherModel()
    }
}