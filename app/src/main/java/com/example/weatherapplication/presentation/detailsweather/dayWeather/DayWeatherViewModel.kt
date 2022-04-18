package com.example.weatherapplication.presentation.detailsweather.dayWeather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.weatherapplication.model.data.Hourly
import com.example.weatherapplication.model.data.Main
import com.example.weatherapplication.model.data.WeatherResponse
import com.example.weatherapplication.model.remote.WeatherRepository
import com.example.weatherapplication.model.room.AppDatabase
import com.example.weatherapplication.model.room.CityDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

class DayWeatherViewModel(application: Application) : AndroidViewModel(application) {
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    val tomorrowWeatherLiveData = MutableLiveData<WeatherResponse>()

    val hourlyWeatherLiveData = MutableLiveData<List<Hourly>>()
    val tomorrowHourlyWeatherLiveData = MutableLiveData<List<Hourly>>()

    val isFavourite = MutableLiveData(false)

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

    fun fetchWeather(id: Int, today: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            var currentWeather = WeatherRepository().fetchWeatherDetails(id)
            val lat = currentWeather!!.coord!!.lat
            val lon = currentWeather!!.coord!!.lon


            if(today) {
                weatherLiveData.value = currentWeather
                val countOfElements = 24 - LocalTime.now().hour

                val hourlyWeatherRaw = WeatherRepository().getHourlyWeather(lat!!, lon!!).hourly
                val hourlyWeather = mutableListOf<Hourly>()
                for (i in 1..countOfElements) {
                    hourlyWeather.add(hourlyWeatherRaw!![i])
                }
                hourlyWeatherLiveData.value = hourlyWeather
            }
            else {
                val tomorrowWeather = WeatherRepository().getDailyWeather(lat!!, lon!!)

                with (tomorrowWeather.daily!![1]) {
                    val main = Main(temp!!.day, feels_like!!.day, temp!!.min, temp!!.max, 0  , 0)
                    val weather = WeatherResponse(weather = weather, main = main,
                        name = currentWeather.name
                    )
                    tomorrowWeatherLiveData.value = weather
                }


                val countOfElements = 24 - LocalTime.now().hour
                val hourlyWeatherRaw = WeatherRepository().getHourlyWeather(lat!!, lon!!).hourly
                val hourlyWeather = mutableListOf<Hourly>()
                for (i in countOfElements..countOfElements+24) {
                    hourlyWeather.add(hourlyWeatherRaw!![i])
                }
                tomorrowHourlyWeatherLiveData.value = hourlyWeather
            }


        }
    }
}