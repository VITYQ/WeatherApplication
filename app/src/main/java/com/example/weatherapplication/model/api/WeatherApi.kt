package com.example.weatherapplication


import com.example.weatherapplication.model.data.OnecallApiResponse
import com.example.weatherapplication.model.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather/?lang=ru")
    suspend fun getWeather(
        @Query("id") zip: Int,
        @Query("appid") key: String): WeatherResponse

    @GET("/data/2.5/onecall?exclude=current,minutely,daily,alerts")
    suspend fun getHourlyWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") key: String): OnecallApiResponse

    @GET("/data/2.5/onecall?exclude=current,minutely,hourly,alerts")
    suspend fun getDailyWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") key: String): OnecallApiResponse

}
