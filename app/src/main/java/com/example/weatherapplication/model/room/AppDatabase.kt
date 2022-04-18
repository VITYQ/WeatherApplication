package com.example.weatherapplication.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}