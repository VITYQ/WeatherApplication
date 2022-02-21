package com.example.weatherapplication

import com.example.weatherapplication.model.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather/?lang=ru")
    suspend fun getWeather(
        @Query("id") zip: Int,
        @Query("appid") key: String): WeatherResponse
}
