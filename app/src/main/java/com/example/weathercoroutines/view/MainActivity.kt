package com.example.weathercoroutines.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weathercoroutines.R
import com.example.weathercoroutines.databinding.ActivityMainBinding
import com.example.weathercoroutines.model.WeatherModel
import com.example.weathercoroutines.viewmodel.MainActivityViewModel
import com.example.weathercoroutines.viewmodel.MainActivityViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var TAG: String = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainActivityViewModelFactory(WeatherModel())
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        observe()
        binding.apply {
            buttonSubmit.setOnClickListener {
                progressCircular.visibility = View.VISIBLE
                imageViewWeather.setImageDrawable(null)
                getWeather()
            }
        }

    }

    private fun observe() {
        viewModel.readWeather.observe(this, Observer {
            binding.apply {

                progressCircular.visibility = View.INVISIBLE
                textViewCityName.text = it.cityName.toString()
                textViewDegrees.text = "${it.celsiusDegrees.toString()} °C"
                imageViewWeather.setImageBitmap(it.image)
                binding.editTextCityInput.setText("")
            }
        })
    }

    private fun getWeather() {
        lifecycleScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Working on thread ${Thread.currentThread().name}")
            viewModel.getWeatherByCity(binding.editTextCityInput.text.toString())
        }
        Log.d(TAG, "Finished getWeather")
    }
}