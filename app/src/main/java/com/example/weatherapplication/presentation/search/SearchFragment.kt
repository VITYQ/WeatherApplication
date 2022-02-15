package com.example.weatherapplication.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentSearchBinding
import com.example.weatherapplication.model.data.City
import com.example.weatherapplication.model.data.Main
import com.example.weatherapplication.presentation.search.adapter.SearchAdapter
import com.example.weatherapplication.presentation.weather.WeatherListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    lateinit var binding : FragmentSearchBinding
    lateinit var viewModel : SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.textField.editText?.doOnTextChanged { text, _, _, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                if (!text.isNullOrEmpty()){
                    val cities = (activity as MainActivity).cities
                    viewModel.foundCities.postValue(cities.filter { it.name.contains(text) })
//                    Log.d("foundcities", viewModel.foundCities.value!!.size.toString())

                }
            }
        }



        viewModel.foundCities.observe(viewLifecycleOwner) {
            val adapter = SearchAdapter(it)
            binding.rvSearch.adapter = adapter
        }













//        CoroutineScope(Dispatchers.IO).launch {
//            if (text?.length!! > 3) {
//                viewModel.list.value = (activity as MainActivity).cities.filter { it.name.contains(text!!) }
//
//            }
//        }
//        val adapter = viewModel.list.value?.let { SearchAdapter(it) }
//        binding.rvSearch.adapter = adapter
//

//
//        viewModel.list.observe(viewLifecycleOwner) {
//            if (it.isNullOrEmpty()){
//                Toast.makeText(requireActivity(), "Not found", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                adapter?.notifyDataSetChanged()
//            }
//        }
    }

}