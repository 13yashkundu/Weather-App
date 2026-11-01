package com.example.weatherz_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherz_app.databinding.FragmentHomePageBinding

class Home_page : Fragment() {

    val vm : Weather_ViewModel by viewModels()
    lateinit var viewBinding : FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomePageBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.searchButton.setOnClickListener {
            searchCity()
        }
        viewBinding.searchBar.setOnEditorActionListener { _, _, _ ->
            searchCity()
            true
        }
    }

    fun searchCity(){
        var city = viewBinding.searchBar.text.toString().trim()
        if (city.isEmpty()){
            viewBinding.searchBar.error = "Enter city name"
            return
        }
        vm.fetchWeather(
            BuildConfig.API_Key
            , city)
    }
}
