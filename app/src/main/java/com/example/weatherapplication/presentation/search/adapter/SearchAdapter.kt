package com.example.weatherapplication.presentation.search.adapter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.model.data.City
import com.example.weatherapplication.model.data.Weather1
import com.example.weatherapplication.presentation.weather.adapters.WeatherAdapter1

class SearchAdapter(val list: List<City>): RecyclerView.Adapter<SearchAdapter.CityItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        return CityItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class CityItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: City) = with(itemView) {
            val city = findViewById<TextView>(R.id.tv_city)
            val country = findViewById<TextView>(R.id.tv_country)

            city.text = item.name
            country.text = item.country

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("cityid", item.id)
                findNavController().navigate(R.id.action_nav_search_to_detailsWeatherFragment, bundle)
            }
        }
    }
}