package com.example.weatherz_app

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherz_app.databinding.FragmentHomePageBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter

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

        temp_chart_setup()

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

        if (state.isLoading) {
            viewBinding.loadingBlur.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blurEffect = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.MIRROR)
                viewBinding.main.setRenderEffect(blurEffect)
            }
        } else {
            viewBinding.loadingBlur.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                viewBinding.main.setRenderEffect(null)
            }
        }


        if (state.error != null){
            Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
        }

        val weather = state.result ?: return

        val localtime = weather.location.localtime.substringAfter(" ")
        val currentTempRounded = weather.current.temp_c.roundToInt()

        viewBinding.cityName.text = "📍 ${weather.location.name}"
        viewBinding.localTime.text = "local time: $localtime"
        viewBinding.tempratureMain.text = "$currentTempRounded °C"
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

        val firstDay = weather.forecast.forecastday.firstOrNull()
        if (firstDay != null) {
            val minTempRounded = firstDay.day.mintemp_c.roundToInt()
            val maxTempRounded = firstDay.day.maxtemp_c.roundToInt()

            viewBinding.minTemp.text = "$minTempRounded °C"
            viewBinding.maxTemp.text = "$maxTempRounded °C"
            viewBinding.textSunrise.text = firstDay.astro.sunrise
            viewBinding.textSunset.text = "Sunset: ${firstDay.astro.sunset}"
        }


        val UV_value = weather.current.uv
        viewBinding.textUvVal.text = UV_value.toString()
        viewBinding.uvProgress.max = 11
        viewBinding.uvProgress.progress = UV_value.toInt().coerceIn(0, 11)
        viewBinding.uvProgress.setOnTouchListener { _, _ -> true }

        viewBinding.textUvDesc.text = when{
            UV_value < 3 -> "Low"
            UV_value <6  -> "Moderate"
            UV_value < 8 -> "High"
            UV_value < 11 -> "Very High"
            else -> "Extreme"
        }


        val humidity = weather.current.humidity

        viewBinding.textHumidityVal.text = "$humidity%"
        viewBinding.humidityProgress.max = 100
        viewBinding.humidityProgress.progress = humidity.coerceIn(0, 100)
        viewBinding.humidityProgress.setOnTouchListener { _, _ -> true }

        viewBinding.textHumidityDesc.text = when{
            humidity < 30 -> "Dry Air"
            humidity <= 60 -> "Comfortable"
            humidity <= 80 -> "Humid"
            else -> "High Humidity"
        }


        viewBinding.textVisVal.text = "${weather.current.vis_km} km"

        viewBinding.textVisDesc.text = when {
            weather.current.vis_km >= 10.0 -> "Clear View"
            weather.current.vis_km >= 5.0 -> "Moderate View"
            else -> "Poor Visibility"
        }

        val forecastItem = weather.forecast.forecastday.map {
            val roundedForecastTemp = it.day.maxtemp_c.roundToInt()
            ForecastItem(
                day = formatDateToDayName(it.date),
                temp = "$roundedForecastTemp °C",
                iconUrl = it.day.condition.icon
            )
        }

        viewBinding.fragmentRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        viewBinding.fragmentRecycler.adapter = ForecastAdapter(forecastItem)

        val currentTime = weather.location.localtime

        val hourlyData = weather.forecast.forecastday
            .flatMap { it.hour }
            .filter { it.time >= currentTime }
            .take(24)

        val entries = hourlyData.mapIndexed { index, hour->
            Entry(index.toFloat(), hour.temp_c.toFloat())
        }

        val dataSet = LineDataSet(entries, "Temperature")

        dataSet.color = android.graphics.Color.WHITE
        dataSet.setCircleColor(android.graphics.Color.WHITE)
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 3f
        dataSet.setDrawValues(false)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.setDrawFilled(true)
        dataSet.fillColor = android.graphics.Color.WHITE
        dataSet.fillAlpha = 35

        val lineData = LineData(dataSet)

        val timeLabels = hourlyData.map { hour ->
            val hourValue = hour.time.substringAfter(" ").substringBefore(":").toInt()

            when {
                hourValue == 0 -> "12 AM"
                hourValue < 12 -> "$hourValue AM"
                hourValue == 12 -> "12 PM"
                else -> "${hourValue - 12} PM"
            }
        }
        viewBinding.temperatureChart.data = lineData
        viewBinding.temperatureChart.invalidate()

        val xAxis = viewBinding.temperatureChart.xAxis

        xAxis.valueFormatter = IndexAxisValueFormatter(timeLabels)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setDrawLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

    }


//    weather card animation

    fun animateWeatherCard() {
        viewBinding.weatherCard.apply {
            alpha = 0f
            translationY = 30f

            animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .start()
        }
    }
//    hourly forecast chart


    fun temp_chart_setup(){
        val chart = viewBinding.temperatureChart

        chart.data
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.axisRight.isEnabled = false

        chart.axisLeft.textColor = android.graphics.Color.WHITE
//        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.setDrawGridLines(false)

        chart.setDrawBorders(false)

        chart.axisLeft.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}°C"
            }
        }

        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.textColor = android.graphics.Color.WHITE

        chart.axisLeft.textColor = android.graphics.Color.WHITE
        chart.setTouchEnabled(true)
        chart.setPinchZoom(false)
        chart.setScaleEnabled(false)
    }


    private fun formatDateToDayName(dateString: String): String {
        return try {
            val inFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outFormat = SimpleDateFormat("EEE", Locale.getDefault())
            val date = inFormat.parse(dateString)
            if (date != null) outFormat.format(date) else dateString
        } catch (e: Exception) {
            dateString
        }
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
