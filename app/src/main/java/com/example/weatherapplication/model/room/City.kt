package com.example.weatherapplication.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityDB (
    @PrimaryKey var id : Int,
    @ColumnInfo(name = "city_name") var name: String? = null
)