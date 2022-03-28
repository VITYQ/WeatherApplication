package com.example.weatherapplication.presentation.detailsweather.adapters

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.model.data.Hourly
import com.example.weatherapplication.model.data.Weather1
import com.example.weatherapplication.presentation.weather.adapters.WeatherAdapter1
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class HourlyWeatherRecyclerAdapter(val context: Context, val list: List<Hourly>): RecyclerView.Adapter<HourlyWeatherRecyclerAdapter.HourlyWeatherItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherItemViewHolder {
        return HourlyWeatherItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly_weather, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HourlyWeatherItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    class HourlyWeatherItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Hourly, position: Int) = with(itemView) {
            val temp = itemView.findViewById<TextView>(R.id.tv_temperature_hourly)
            val hour = itemView.findViewById<TextView>(R.id.tv_hour)
            val icon = itemView.findViewById<ImageView>(R.id.iv_weather_hourly)


            hour.text = SimpleDateFormat("HH:mm").format(item.dt*1000)


//            hour.text = LocalDateTime.now().plusHours(position.toLong())
//                .format(DateTimeFormatter.ofPattern("HH:00"))



            temp.text = "${(item.temp-273).toInt()}°"
            when (item.weather[0].main) {
                "Clear" -> icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sunny))
                "Clouds" -> icon.setImageDrawable(resources.getDrawable(R.drawable.ic_partlycloudy))
                "Snow" -> icon.setImageDrawable(resources.getDrawable(R.drawable.ic_snowy))
                "Rain" -> icon.setImageDrawable(resources.getDrawable(R.drawable.ic_rainy))
                "Drizzle" -> icon.setImageDrawable(resources.getDrawable(R.drawable.ic_rainy))
                "Thunderstorm" -> icon.setImageDrawable(resources.getDrawable(R.drawable.ic_rainthunder))
            }



        }


    }


}