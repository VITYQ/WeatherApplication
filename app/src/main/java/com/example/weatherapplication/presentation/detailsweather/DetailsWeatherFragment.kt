package com.example.weatherapplication.presentation.detailsweather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentDetailsWeatherBinding
import com.example.weatherapplication.presentation.weather.WeatherListViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


class DetailsWeatherFragment : Fragment() {

    lateinit var binding: FragmentDetailsWeatherBinding
    lateinit var viewModel: DetailsWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(DetailsWeatherViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_weather, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchWeather(arguments?.getInt("cityid")!!)
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
//            binding.tvCity.text = it.name
            val localdate = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("MMMM dd, HH:mm")
            binding.tvDatetime.text = localdate.format(formatter)

            val sunrise = LocalDateTime.ofEpochSecond(it.sys?.sunrise?.toLong()!!, 0, ZoneOffset.ofHours(3))
            val sunset = LocalDateTime.ofEpochSecond(it.sys?.sunset?.toLong()!!, 0, ZoneOffset.ofHours(3))

            Log.d("checksunrise", sunset.format(DateTimeFormatter.ISO_LOCAL_TIME))

            when (it.weather?.get(0)?.main) {
                "Clear" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_sunny))
                "Clouds" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_partlycloudy))
                "Snow" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_snowy))
                "Rain" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_rainy))
                "Drizzle" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_rainy))
                "Thunderstorm" -> binding.ivWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_rainthunder))
            }



            binding.toolbar.title = it.name
            binding.tvTempmaxmin.text = "Макс. ${it.main?.temp_max?.minus(273)?.toInt()}℃ | Мин. ${it.main?.temp_min?.minus(273)?.toInt()}℃"
            binding.tvFeelslike.text = "Ощущается как ${it.main?.feels_like?.minus(273)?.toInt()}℃"
            binding.tvTemperature.text = "${it.main?.temp?.minus(273)?.toInt()}"
            binding.tvWeather.text = it.weather?.get(0)?.description
        }




    }



}