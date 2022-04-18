package com.example.weatherapplication.model.room

import androidx.room.*

@Dao
interface CityDao {
    @Query("SELECT * FROM citydb")
    fun getAll(): List<CityDB>

    @Query("SELECT * FROM citydb WHERE id LIKE :id")
    fun findById(id: Int): CityDB?

    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    fun insertCity(vararg city: CityDB)

    @Delete
    fun delete(city: CityDB)
}