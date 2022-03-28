package com.example.weatherapplication.model.remote

import android.util.Log
import com.example.weatherapplication.WeatherApi
import com.example.weatherapplication.model.data.OnecallApiResponse
import com.example.weatherapplication.model.data.Weather
import com.example.weatherapplication.model.data.Weather1
import com.example.weatherapplication.model.data.WeatherResponse
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
        val id = response.id
        return Weather1(city!!, weather!!, temp!!, id!!)
    }

    suspend fun fetchWeatherDetails(id: Int) : WeatherResponse {
        return api.getWeather(id, "e9fa256df6ebb4101bec1ec7eaa465f2")
    }

    suspend fun getHourlyWeather(lat: Float, lon: Float) : OnecallApiResponse {
        return api.getHourlyWeather(lat, lon, "e9fa256df6ebb4101bec1ec7eaa465f2")
    }

    suspend fun getDailyWeather(lat: Float, lon: Float) : OnecallApiResponse {
        return api.getDailyWeather(lat, lon, "e9fa256df6ebb4101bec1ec7eaa465f2")
    }
}