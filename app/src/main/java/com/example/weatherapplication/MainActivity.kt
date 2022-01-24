package com.example.weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapplication.model.retrofit.RetrofitInstance

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = RetrofitInstance.getClient().create(WeatherApi::class.java)


//        api.getWeather(498817, "e9fa256df6ebb4101bec1ec7eaa465f2").enqueue(object: Callback<WeatherResponse>{
//            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//
//            }
//            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
////                val textView = findViewById<TextView>(R.id.textView_Temperature)
//                val temp = response.body()?.main?.temp?.toInt()?.minus(273)
//                val humidity = response.body()?.main?.humidity
//                val windspeed = response.body()?.wind?.speed
////                textView.text = "Температура $temp градусов, \nВлажность $humidity%, \nСкорость ветра $windspeed м/с"
//            }
//        })

    }
}