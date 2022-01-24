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
import com.example.weatherapplication.R
import com.example.weatherapplication.model.data.Weather1


class WeatherAdapter1(val context: Context, var list: List<Weather1>): RecyclerView.Adapter<WeatherAdapter1.WeatherItemViewHolder>() {

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemViewHolder {
        return WeatherItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weather1, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WeatherItemViewHolder, position: Int) {
        holder.bind(list[position])
        setAnimation(holder.itemView, position)
    }


    fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    class WeatherItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val expandedView = itemView.findViewById<ConstraintLayout>(R.id.constraint_expanded)
        val field1 = itemView.findViewById<TextView>(R.id.tv_field1)
        val field11 = itemView.findViewById<TextView>(R.id.tv_field11)
        val field2 = itemView.findViewById<TextView>(R.id.tv_field2)
        val list = listOf<View>(field1, field11, field2)

        fun bind(item: Weather1) = with(itemView) {

            val city = itemView.findViewById<TextView>(R.id.textView_city)
            val weather = itemView.findViewById<TextView>(R.id.textView_weather)
            val temp = itemView.findViewById<TextView>(R.id.tv_temperature)
            val card = itemView.findViewById<CardView>(R.id.cardview_weather)
            val expandedView = itemView.findViewById<ConstraintLayout>(R.id.constraint_expanded)

            card.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    if (expandedView.visibility == View.VISIBLE) { hide() }
                    else { expand() }
                }


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
        suspend fun expand() {
            expandedView.visibility = View.VISIBLE
            list.forEach {
                it.alpha = 0f
            }
            list.forEach {
                delay(300)
                val x = it.x
                it.x = x-100
                it.animate()
                    .alpha(1f)
                    .x(x)
                    .setDuration(1000)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .start()
            }
        }

        suspend fun hide() {
            expandedView.visibility = View.GONE
        }

        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }


}