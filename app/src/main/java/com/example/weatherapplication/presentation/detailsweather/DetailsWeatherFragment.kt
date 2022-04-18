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
import com.example.weatherapplication.presentation.detailsweather.adapters.DaysViewPagerAdapter
import com.example.weatherapplication.presentation.detailsweather.adapters.HourlyWeatherRecyclerAdapter
import com.example.weatherapplication.presentation.weather.WeatherListViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


class DetailsWeatherFragment() : Fragment() {

    lateinit var binding: FragmentDetailsWeatherBinding
    lateinit var viewModel: DetailsWeatherViewModel
    var cityId = 0

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
        cityId = arguments?.getInt("cityid")!!
        binding.vpDays.adapter = DaysViewPagerAdapter(this, cityId)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.checkIfInFavourites(cityId)
        }
        viewModel.getCityName(cityId)

        TabLayoutMediator(binding.tlDays, binding.vpDays) {tab, position ->
            when (position) {
                0 -> tab.text = "Сегодня"
                1 -> tab.text = "Завтра"
            }
        }.attach()

        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_favourite -> {
                    viewModel.addOrRemoveFromFavourites(cityId)
                    true
                }
                else -> false
            }
        }

        viewModel.cityName.observe(viewLifecycleOwner){
            binding.toolbar.title = it
        }

        viewModel.isFavourite.observe(viewLifecycleOwner){
            Log.d("checkfafaf", it.toString())
            if (it)
                binding.toolbar.menu.findItem(R.id.menu_favourite).icon = resources.getDrawable(R.drawable.ic_baseline_star_24)
            else
                binding.toolbar.menu.findItem(R.id.menu_favourite).icon = resources.getDrawable(R.drawable.ic_baseline_star_outline_24)
        }




    }



}