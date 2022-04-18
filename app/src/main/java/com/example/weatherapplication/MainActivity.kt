package com.example.weatherapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.room.Room
import com.example.weatherapplication.model.data.City
import com.example.weatherapplication.model.retrofit.RetrofitInstance
import com.example.weatherapplication.model.room.AppDatabase
import com.example.weatherapplication.model.room.CityDB
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var cities : List<City>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCityList()




        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val constraintMain = findViewById<ConstraintLayout>(R.id.cl_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.container_main) as NavHostFragment

        NavigationUI.setupWithNavController(
            bottomNav,
            navHostFragment.navController
        )

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d("CheckDestination", this.resources.getResourceEntryName(destination.id))
            val screenName = this.resources.getResourceEntryName(destination.id)
            if (screenName == "nav_search" || screenName == "nav_settings"
                || screenName == "nav_weather") {
                bottomNav.visibility = View.VISIBLE
                constraintMain.setBackgroundColor(Color.parseColor("#ffffff"))
            }
            else if (screenName == "detailsWeatherFragment") {
                constraintMain.setBackgroundColor(Color.parseColor("#eeeeee"))
                bottomNav.visibility = View.GONE
            }
            else{
                bottomNav.visibility = View.GONE
                constraintMain.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }


    }

    override fun onBackPressed() {
        findNavController(R.id.container_main).popBackStack()
    }


    private fun getCityList() {
        CoroutineScope(Dispatchers.Main).launch {
            val jsonString = this@MainActivity.assets.open("city_list.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            cities = gson.fromJson(jsonString, object : TypeToken<List<City>>() {}.type)
        }
    }

}

