package com.example.weatherapplication.presentation.detailsweather.dayWeather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.model.data.Hourly
import com.example.weatherapplication.model.data.Main
import com.example.weatherapplication.model.data.WeatherResponse
import com.example.weatherapplication.model.remote.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

class DayWeatherViewModel() : ViewModel() {
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    val tomorrowWeatherLiveData = MutableLiveData<WeatherResponse>()

    val hourlyWeatherLiveData = MutableLiveData<List<Hourly>>()
    val tomorrowHourlyWeatherLiveData = MutableLiveData<List<Hourly>>()

    fun fetchWeather(id: Int, today: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            var currentWeather = WeatherRepository().fetchWeatherDetails(id)
            val lat = currentWeather!!.coord!!.lat
            val lon = currentWeather!!.coord!!.lon


            if(today) {
                weatherLiveData.value = currentWeather
                val countOfElements = 24 - LocalTime.now().hour

                val hourlyWeatherRaw = WeatherRepository().getHourlyWeather(lat!!, lon!!).hourly
                val hourlyWeather = mutableListOf<Hourly>()
                for (i in 1..countOfElements) {
                    hourlyWeather.add(hourlyWeatherRaw!![i])
                }
                hourlyWeatherLiveData.value = hourlyWeather
            }
            else {
                val tomorrowWeather = WeatherRepository().getDailyWeather(lat!!, lon!!)

                with (tomorrowWeather.daily!![1]) {
                    val main = Main(temp!!.day, feels_like!!.day, temp!!.min, temp!!.max, 0  , 0)
                    val weather = WeatherResponse(weather = weather, main = main,
                        name = currentWeather.name
                    )
                    tomorrowWeatherLiveData.value = weather
                }

                val countOfElements = 24 - LocalTime.now().hour
                val hourlyWeatherRaw = WeatherRepository().getHourlyWeather(lat!!, lon!!).hourly
                val hourlyWeather = mutableListOf<Hourly>()
                for (i in countOfElements..countOfElements+24) {
                    hourlyWeather.add(hourlyWeatherRaw!![i])
                }
                tomorrowHourlyWeatherLiveData.value = hourlyWeather
            }


        }
    }
}