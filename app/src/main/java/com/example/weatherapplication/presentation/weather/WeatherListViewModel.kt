package com.example.weatherapplication.presentation.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.model.retrofit.RetrofitInstance
import com.example.weatherapplication.model.data.Weather1
import com.example.weatherapplication.WeatherApi
import com.example.weatherapplication.model.remote.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherListViewModel : ViewModel() {

    var weatherList = MutableLiveData<List<Weather1>>()
    val repository = WeatherRepository()

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        val listOfCities = listOf<Int>(498817, 7264, 2807300, 498817, 498817)

        CoroutineScope(Dispatchers.Main).launch {
            weatherList.value = repository.fetchWeatherCities(listOfCities)
        }
    }

}