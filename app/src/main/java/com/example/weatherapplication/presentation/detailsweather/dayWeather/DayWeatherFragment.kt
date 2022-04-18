package com.example.weatherapplication.presentation.detailsweather.dayWeather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentDayWeatherBinding
import com.example.weatherapplication.databinding.FragmentDetailsWeatherBinding
import com.example.weatherapplication.model.data.Hourly
import com.example.weatherapplication.model.data.WeatherResponse
import com.example.weatherapplication.presentation.detailsweather.DetailsWeatherViewModel
import com.example.weatherapplication.presentation.detailsweather.adapters.HourlyWeatherRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class DayWeatherFragment(val cityId: Int, val today: Boolean) : Fragment() {

    lateinit var binding: FragmentDayWeatherBinding
    lateinit var viewModel: DayWeatherViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(DayWeatherViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_day_weather, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchWeather(cityId, today)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.checkIfInFavourites(cityId)
        }



        if(today){
            val localdate = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("MMMM dd, HH:mm")
            binding.tvDatetime.text = localdate.format(formatter)

            viewModel.weatherLiveData.observe(viewLifecycleOwner) {
                setView(it)
            }
            viewModel.hourlyWeatherLiveData.observe(viewLifecycleOwner) {
                setRecycler(it)
            }
        }
        else{
            binding.tvDatetime.text = "Завтра"

            viewModel.tomorrowWeatherLiveData.observe(viewLifecycleOwner){
                setView(it)
            }
            viewModel.tomorrowHourlyWeatherLiveData.observe(viewLifecycleOwner) {
                setRecycler(it)
            }
        }

        viewModel.isFavourite.observe(viewLifecycleOwner) {
            if (it)
                binding.ivFavourite.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_24))

            else{
                binding.ivFavourite.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_outline_24))
            }

        }

        binding.ivFavourite.setOnClickListener {
            viewModel.addOrRemoveFromFavourites(cityId)
        }

    }

    fun setRecycler(it: List<Hourly>){
        val adapter = HourlyWeatherRecyclerAdapter(requireContext(), it)
        binding.rvWeatherHourly.adapter = adapter
    }

    fun setView(it: WeatherResponse) {

        when (it.weather?.get(0)?.main) {
            "Clear" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_sunny))
            "Clouds" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_partlycloudy))
            "Snow" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_snowy))
            "Rain" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_rainy))
            "Drizzle" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_rainy))
            "Thunderstorm" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_rainthunder))
        }



        binding.tvTempmaxmin.text = "Макс. ${it.main?.temp_max?.minus(273)?.toInt()}℃ | Мин. ${it.main?.temp_min?.minus(273)?.toInt()}℃"
        binding.tvFeelslike.text = "Ощущается как ${it.main?.feels_like?.minus(273)?.toInt()}℃"
        binding.tvTemperature.text = "${it.main?.temp?.minus(273)?.toInt()}"
        binding.tvWeather.text = it.weather?.get(0)?.description
    }

}