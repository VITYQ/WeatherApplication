package com.example.weatherapplication.presentation.weather.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.util.DisplayMetrics
import androidx.navigation.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.model.data.Weather1
import android.os.Bundle





class WeatherAdapter1(val context: Context, val list: List<Weather1>): RecyclerView.Adapter<WeatherAdapter1.WeatherItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemViewHolder {
        return WeatherItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weather1, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WeatherItemViewHolder, position: Int) {
        holder.bind(list[position])
    }




    class WeatherItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun bind(item: Weather1) = with(itemView) {

            val city = itemView.findViewById<TextView>(R.id.textView_city)
            val weather = itemView.findViewById<TextView>(R.id.textView_weather)
            val temp = itemView.findViewById<TextView>(R.id.tv_temperature)
            val card = itemView.findViewById<CardView>(R.id.cardview_weather)

            card.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("cityid", item.id)
                findNavController().navigate(R.id.action_nav_weather_to_detailsWeatherFragment, bundle)
            }


            city.text = item.city
            weather.text = item.weather
            temp.text = "${item.temp}C*"

            if (item.temp < -10) {
                card.setCardBackgroundColor(Color.parseColor("#5D54CD"))
            }
            else if (item.temp >= -10 && item.temp <= 10) {
                card.setCardBackgroundColor(Color.parseColor("#234EF4"))
            }
            else if (item.temp > 10 && item.temp < 20) {
                card.setCardBackgroundColor(Color.parseColor("#FFB319"))
            }
            else {
                card.setCardBackgroundColor(Color.parseColor("#F55044"))
            }
        }



    }


}