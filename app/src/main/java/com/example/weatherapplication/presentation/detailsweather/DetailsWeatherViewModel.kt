package com.example.weatherapplication.presentation.detailsweather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.weatherapplication.model.data.Hourly
import com.example.weatherapplication.model.data.WeatherResponse
import com.example.weatherapplication.model.remote.WeatherRepository
import com.example.weatherapplication.model.room.AppDatabase
import com.example.weatherapplication.model.room.CityDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

class DetailsWeatherViewModel(application: Application): AndroidViewModel(application) {

    val isFavourite = MutableLiveData(false)
    val cityName = MutableLiveData<String>()

    val context = getApplication<Application>().applicationContext

    suspend fun checkIfInFavourites(id: Int): Boolean{
        var favourite = false
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "city-db"
        ).build()
        val cityDao = db.cityDao()

        if(cityDao.findById(id) != null) {
            isFavourite.postValue(true)
            favourite = true
        }
        else {
            isFavourite.postValue(false)
        }
        return favourite
    }


    fun addOrRemoveFromFavourites(id: Int){
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "city-db"
        ).build()
        val cityDao = db.cityDao()

        CoroutineScope(Dispatchers.IO).launch {
            if (checkIfInFavourites(id)){
                cityDao.delete(CityDB(id, ""))
            }
            else {
                cityDao.insertCity(CityDB(id, ""))
            }
            checkIfInFavourites(id)
        }


    }

    fun getCityName(cityId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            cityName.postValue(WeatherRepository().fetchWeatherAtCity(cityId).city)
        }

    }


}