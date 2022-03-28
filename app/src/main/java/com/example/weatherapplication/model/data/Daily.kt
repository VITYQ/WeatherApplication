package com.example.weatherapplication.model.data

data class Daily(
    val dt: Long?,
    val sunrise: Long?,
    val sunset: Long?,
    val moonrise: Long?,
    val moonset: Long?,
    val moon_phase: Float?,
    val temp: Temp?,
    val feels_like: FeelsLike?,
    val pressure: Long?,
    val humidity: Long?,
    val dew_point: Float?,
    val wind_speed: Float?,
    val wind_deg: Long?,
    val weather: List<Weather>?,
    val clouds: Long?,
    val pop: Float?,
    val rain: Float?,
    val uvi: Float?
)
