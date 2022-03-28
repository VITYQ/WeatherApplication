package com.example.weatherapplication.model.local

import android.content.Context
import com.google.gson.Gson

import android.content.SharedPreferences
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class PreferenceManager {
    fun saveFavourites(context: Context, list: List<Int>) {
        val prefs: SharedPreferences = context.getSharedPreferences("favourites", Context.MODE_PRIVATE)
//        val sharedPref: SharedPreferences = context?.getPreferences(Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("favourites", json)
        editor.apply()
    }

    fun getFavourites (context: Context): List<Int>{
        val prefs: SharedPreferences = context.getSharedPreferences("favourites", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("favourites", null)
        val type: Type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type)
    }
}