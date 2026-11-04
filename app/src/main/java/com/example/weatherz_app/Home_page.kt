package com.example.weatherz_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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

        searchCity("New Delhi")

        viewBinding.searchButton.setOnClickListener {
            searchCity(viewBinding.searchBar.text.toString().trim())
        }
        viewBinding.searchBar.setOnEditorActionListener { _, _, _ ->
            searchCity(viewBinding.searchBar.text.toString().trim())
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

        viewBinding.cityName.text = "📍 ${weather.location.name}"
        viewBinding.localTime.text = "local time: ${weather.location.localtime}"
        viewBinding.tempratureMain.text = "${weather.current.temp_c} °C"
        viewBinding.weatherCondition.text = weather.current.condition.text

        // Weather condition icon via Glide
        val iconUrl = weather.current.condition.icon
        if (iconUrl.isNotEmpty()) {
            val fullIconUrl = if (iconUrl.startsWith("//")) "https:$iconUrl" else iconUrl
            Glide.with(this)
                .load(fullIconUrl)
                .placeholder(R.drawable.night_icon)
                .into(viewBinding.iconDay)
        }

        viewBinding.minTemp.text = "${weather.forecast.forecastday[0].day.mintemp_c} °C"
        viewBinding.maxTemp.text = "${weather.forecast.forecastday[0].day.maxtemp_c} °C"

        viewBinding.textSunrise.text = weather.forecast.forecastday[0].astro.sunrise
        viewBinding.textSunset.text = "Sunset: ${weather.forecast.forecastday[0].astro.sunset}"
        viewBinding.textHumidityVal.text = "${weather.current.humidity}%"
        viewBinding.textVisVal.text = "${weather.current.vis_km} km"

        viewBinding.textVisDesc.text = when {
            weather.current.vis_km >= 10.0 -> "Clear View"
            weather.current.vis_km >= 5.0 -> "Moderate View"
            else -> "Poor Visibility"
        }

        val forecastItem = weather.forecast.forecastday.map {
            ForecastItem(
                day = if (it.date.length >= 10) it.date.substring(5) else it.date,
                temp = "${it.day.maxtemp_c} °C",
                iconUrl = it.day.condition.icon
            )
        }

        viewBinding.fragmentRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        viewBinding.fragmentRecycler.adapter = ForecastAdapter(forecastItem)
    }

    fun searchCity(name: String) {
        val city = name
        if (city.isEmpty()) {
            viewBinding.searchBar.error = "Enter city name"
            return
        }
        vm.fetchWeather(
            BuildConfig.API_Key,
            city
        )
    }
}
