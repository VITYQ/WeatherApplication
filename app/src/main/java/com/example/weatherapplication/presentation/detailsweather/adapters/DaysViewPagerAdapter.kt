package com.example.weatherapplication.presentation.detailsweather.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapplication.presentation.detailsweather.dayWeather.DayWeatherFragment
import com.example.weatherapplication.presentation.welcome.WelcomeFragment

class DaysViewPagerAdapter(fragment: Fragment, val cityId: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DayWeatherFragment(cityId, true)
            1 -> return DayWeatherFragment(cityId, false)
            else -> return DayWeatherFragment(cityId, true)
        }
    }
}