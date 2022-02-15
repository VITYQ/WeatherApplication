package com.example.weatherapplication.model.data

data class City (
    val id: Int,
    val name: String,
    val state: String?,
    val country: String,
    val coord: Coord
)