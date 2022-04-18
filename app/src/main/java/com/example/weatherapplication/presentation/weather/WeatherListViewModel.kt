package com.example.weatherapplication.presentation.weather

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.weatherapplication.model.retrofit.RetrofitInstance
import com.example.weatherapplication.model.data.Weather1
import com.example.weatherapplication.WeatherApi
import com.example.weatherapplication.model.remote.WeatherRepository
import com.example.weatherapplication.model.room.AppDatabase
import com.example.weatherapplication.model.room.CityDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherListViewModel(application: Application) : AndroidViewModel(application) {

    var weatherList = MutableLiveData<List<Weather1>>()
    val repository = WeatherRepository()
    val listOfCities = MutableLiveData<List<CityDB>>()
    private val context = getApplication<Application>().applicationContext


    init {
        //fetchWeather()
    }

    fun fetchWeather() {
        weatherList.value = emptyList()

        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "city-db"
            ).build()
            val cityDao = db.cityDao()
            val a = cityDao.getAll()
            val tmpList = mutableListOf<Int>()
            Log.d("checkdao", a.toString())
            a.forEach {
                tmpList.add(it.id)
            }
            weatherList.postValue(repository.fetchWeatherCities(tmpList))


        }


    }

}