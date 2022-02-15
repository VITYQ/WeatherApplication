package com.example.weatherapplication.presentation.detailsweather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.model.data.WeatherResponse
import com.example.weatherapplication.model.remote.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsWeatherViewModel: ViewModel() {

    val weatherLiveData = MutableLiveData<WeatherResponse>()


    fun fetchWeather(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            weatherLiveData.value = WeatherRepository().fetchWeatherDetails(id)
            Log.d("checkcityid", weatherLiveData.value.toString())
        }
    }
}