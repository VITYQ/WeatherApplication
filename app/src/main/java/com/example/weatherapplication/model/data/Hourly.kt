package com.example.weatherapplication.model.data

import android.util.FloatMath

data class Hourly(
    val dt: Long,
    val temp: Float,
    val feels_like: Float,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Float,
    val uvi: Float,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Float,
    val wind_deg: Float,
    val wind_gust: Float,
    val weather: List<Weather>,
    val pop: Float
)
