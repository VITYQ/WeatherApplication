package com.example.weatherapplication.presentation.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.model.data.City

class SearchViewModel : ViewModel() {
    val foundCities = MutableLiveData<List<City>>()
}
