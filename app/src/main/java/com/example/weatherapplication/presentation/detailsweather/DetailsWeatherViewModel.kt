package com.example.weatherapplication.presentation.detailsweather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.model.data.Hourly
import com.example.weatherapplication.model.data.WeatherResponse
import com.example.weatherapplication.model.remote.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

class DetailsWeatherViewModel: ViewModel() {

    val weatherLiveData = MutableLiveData<WeatherResponse>()

    val hourlyWeatherLiveData = MutableLiveData<List<Hourly>>()

    fun fetchWeather(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            weatherLiveData.value = WeatherRepository().fetchWeatherDetails(id)

            val lat = weatherLiveData.value!!.coord!!.lat
            val lon = weatherLiveData.value!!.coord!!.lon

            val countOfElements = 24 - LocalTime.now().hour

            val hourlyWeatherRaw = WeatherRepository().getHourlyWeather(lat!!, lon!!).hourly
            val hourlyWeather = mutableListOf<Hourly>()
            for (i in 1..countOfElements) {
                hourlyWeather.add(hourlyWeatherRaw!![i])
            }
            hourlyWeatherLiveData.value = hourlyWeather
            Log.d("checkhourlyweather", hourlyWeatherLiveData.value.toString())

        }
    }
}