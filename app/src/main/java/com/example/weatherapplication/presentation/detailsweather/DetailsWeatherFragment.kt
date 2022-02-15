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

        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            binding.tvCity.text = it.name
            binding.tvTemperature.text = "${it.main?.temp?.minus(273)?.toInt()}℃ "
            binding.tvWeather.text = it.weather?.get(0)?.main
        }




    }



}