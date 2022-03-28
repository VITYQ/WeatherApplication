package com.example.weatherapplication.model.data

data class OnecallApiResponse(
    val lat: Float,
    val lon: Float,
    val timezone: String,
    val timezone_offset: Int,
    val hourly: List<Hourly>?,
    val daily: List<Daily>?
)