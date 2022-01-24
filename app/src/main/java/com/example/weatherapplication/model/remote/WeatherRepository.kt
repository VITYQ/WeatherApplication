package com.example.weatherapplication.model.remote

import android.util.Log
import com.example.weatherapplication.WeatherApi
import com.example.weatherapplication.model.data.Weather1
import com.example.weatherapplication.model.retrofit.RetrofitInstance

class WeatherRepository {
    val api = RetrofitInstance.getClient().create(WeatherApi::class.java)

    suspend fun fetchWeatherCities(citiesList: List<Int>) : List<Weather1> {
        val list = mutableListOf<Weather1>()
        citiesList.forEach {
            try {
                list.add(fetchWeatherAtCity(it))
            }
            catch (t: Throwable){
                Log.e("http error", t.message.toString())
            }
        }
        return list
    }

    suspend fun fetchWeatherAtCity(id: Int) : Weather1 {
        val response = api.getWeather(id, "e9fa256df6ebb4101bec1ec7eaa465f2")
        val temp = response.main?.temp?.toInt()?.minus(273)
        val weather = response.weather?.get(0)?.main
        val city = response.name
        return Weather1(city!!, weather!!, temp!!)
    }
}