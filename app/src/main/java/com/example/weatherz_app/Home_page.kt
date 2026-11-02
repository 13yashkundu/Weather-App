package com.example.weatherz_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherz_app.databinding.FragmentHomePageBinding
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            vm.weatherUIState.collect { state ->
                updateWeatherUI(state)
            }
        }
    }

    private fun updateWeatherUI(state: WeatherUIState) {

        val weather = state.result ?: return

        viewBinding.tempratureMain.text = "${weather.current.temp_c} °C"
        viewBinding.localTime.text = "${weather.location.localtime}"
        viewBinding.cityName.text = "${weather.location.name}"
        viewBinding.minTemp.text = "${weather.forecastResponse.forecast.forecastday[0].day.mintemp_c}"
        viewBinding.maxTemp.text = "${weather.forecastResponse.forecast.forecastday[0].day.maxtemp_c}"
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
